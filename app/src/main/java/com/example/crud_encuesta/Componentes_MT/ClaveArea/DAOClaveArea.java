package com.example.crud_encuesta.Componentes_MT.ClaveArea;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class DAOClaveArea {
    Context context;
    DatabaseAccess databaseAccess;
    SQLiteDatabase db;

    public DAOClaveArea(Context context) {
        this.context = context;

        databaseAccess = DatabaseAccess.getInstance(context);
    }

    public List<ClaveArea> getAreasClave(int id_clave, String clave){
        List<ClaveArea> areasClave = new ArrayList<>();
        List<String> areas = new ArrayList<>();
        List<Integer> numero_preguntas = new ArrayList<>();
        List<Integer> aleatorios = new ArrayList<>();
        List<Integer> pesos = new ArrayList<>();
        List<Integer> ids_ca = new ArrayList<>();

        String area;
        int num_p;
        int aleat;
        int peso;
        int id_ca;
        int i =0;

        SQLiteDatabase db = databaseAccess.open();

        areas = areas(db, id_clave);
        Cursor cursor = db.rawQuery("SELECT * FROM clave_area WHERE id_clave="+id_clave,null);

        /*areas = areas(db, id_clave);
        numero_preguntas = numero_preguntas(db, id_clave);
        aleatorios = aleatorio(db, id_clave);
        pesos = peso(db, id_clave);
        ids_ca = id_ca(db, id_clave);*/

        while (cursor.moveToNext()){
            id_ca = cursor.getInt(0);
            num_p = cursor.getInt(3);
            aleat = cursor.getInt(4);
            peso = cursor.getInt(5);
            area = areas.get(i);

            areasClave.add(new ClaveArea(area, clave,id_ca, aleat, num_p, peso));
            i++;
        }

        databaseAccess.close();

        return  areasClave;
    }

    public List<String> areas(SQLiteDatabase db, int ide){
        List<String> areas = new ArrayList<>();
        int id_area;

        Cursor cursor = db.rawQuery("SELECT * FROM clave_area WHERE id_clave="+ide,null);
        while (cursor.moveToNext()){
            id_area = cursor.getInt(1);
            Cursor cursor2 = db.rawQuery("SELECT titulo FROM area WHERE id_area="+id_area,null);
            cursor2.moveToFirst();
            areas.add(cursor2.getString(0));
            cursor2.close();
        }
        cursor.close();

        return areas;
    }
}
