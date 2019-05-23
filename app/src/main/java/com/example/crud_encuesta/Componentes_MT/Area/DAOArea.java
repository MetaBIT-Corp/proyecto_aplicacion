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


    }

    public List<Area> getAreas(int id_materia, int id_usuario){
        List<Area> areas = new ArrayList<>();
        int id;
        String titulo;
        int id_tipo_item;

        int id_docente = getDocente(id_usuario);

        db = databaseAccess.open();

        Cursor cursor_area = db.rawQuery("SELECT id_area, titulo, id_tipo_item FROM area WHERE id_cat_mat="+id_materia+" AND id_pdg_dcn="+id_docente, null);

        while(cursor_area.moveToNext()){
            id = cursor_area.getInt(0);
            titulo = cursor_area.getString(1);
            id_tipo_item= cursor_area.getInt(2);

            areas.add(new Area(id, titulo, id_tipo_item));
        }

        cursor_area.close();

        return areas;
    }

    public int getDocente(int id_usuario){
        db = databaseAccess.open();
        int id_docente=0;

        Cursor cursor = db.rawQuery("SELECT ID_PDG_DCN FROM PDG_DCN_DOCENTE WHERE IDUSUARIO="+id_usuario, null);

        if(cursor.moveToFirst()) id_docente = cursor.getInt(0);

        return id_docente;
    }

    //retorna la lista pero solo con el elemento buscado
    public List<Area> buscarArea(int id_materia, String titulo){
        List<Area> areas = new ArrayList<>();
        int id;
        String title;

        db = databaseAccess.open();
        Cursor cursor  = db.rawQuery("SELECT id_area, titulo FROM area WHERE id_cat_mat="+id_materia+" AND TITULO LIKE '%" + titulo + "%'"  ,null);

        while (cursor.moveToNext()){
            id = cursor.getInt(0);
            title = cursor.getString(1);

            areas.add(new Area(id, title));

        }

        return areas;
    }

}
