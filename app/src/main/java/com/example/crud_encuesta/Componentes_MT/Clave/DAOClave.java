package com.example.crud_encuesta.Componentes_MT.Clave;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class DAOClave {
    Context context;
    DatabaseAccess databaseAccess;
    SQLiteDatabase db;

    public DAOClave(Context context) {
        this.context = context;

        databaseAccess = DatabaseAccess.getInstance(context);
    }


    public List<Clave> getClaves(){
        List<Clave> claves = new ArrayList<>();
        int id;
        String num_clave;

        db = databaseAccess.open();

        Cursor cursor = db.rawQuery("SELECT id_clave, numero_clave FROM clave", null);

        while (cursor.moveToNext()){
            id = cursor.getInt(0);
            num_clave = cursor.getString(1);

            claves.add(new Clave(id, num_clave));
        }

        databaseAccess.close();

        return claves;
    }
}
