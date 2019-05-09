package com.example.crud_encuesta.Componentes_MT.Area;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class DAOArea {
    Context context;
    DatabaseAccess databaseAccess;
    SQLiteDatabase db;

    public DAOArea(Context context) {
        this.context = context;

        databaseAccess = DatabaseAccess.getInstance(context);
    }


    public void insertar(Area area){
        db = databaseAccess.open();
        ContentValues registro = new ContentValues();

        registro.put("titulo", area.titulo);
        registro.put("id_tipo_item", area.id_tipo_itemm);
        registro.put("id_cat_mat", area.id_materia);
        registro.put("id_pdg_dcn", area.id_docente);

        db.insert("area", null, registro);

        databaseAccess.close();
    }

    public List<Area> getAreas(){
        List<Area> areas = new ArrayList<>();
        int id;
        String titulo;

        db = databaseAccess.open();

        Cursor cursor_area = db.rawQuery("SELECT id_area, titulo FROM area", null);

        while(cursor_area.moveToNext()){
            id = cursor_area.getInt(0);
            titulo = cursor_area.getString(1);

            areas.add(new Area(id, titulo));
        }

        cursor_area.close();
        databaseAccess.close();

        return areas;
    }
}
