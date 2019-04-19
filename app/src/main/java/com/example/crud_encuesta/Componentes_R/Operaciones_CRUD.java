package com.example.crud_encuesta.Componentes_R;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Operaciones_CRUD {

    public static final Toast insertar(SQLiteDatabase db, ContentValues c, Context context, String table_name) {
        try{
            db.insert(table_name,null,c);
            return Toast.makeText(context,"Se guardo correctamente",Toast.LENGTH_SHORT);

        }catch (Exception e){
            return Toast.makeText(context,"Error",Toast.LENGTH_SHORT);
        }
    }
}
