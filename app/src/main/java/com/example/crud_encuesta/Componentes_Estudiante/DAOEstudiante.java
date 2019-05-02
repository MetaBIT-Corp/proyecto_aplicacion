package com.example.crud_encuesta.Componentes_Estudiante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DAOEstudiante {
    private SQLiteDatabase cx;
    private ArrayList<Estudiante> lista = new ArrayList<>();
    private Estudiante estd;
    private Context ct;

    public DAOEstudiante(Context ct){
        this.ct = ct;
        DatabaseAccess dba = DatabaseAccess.getInstance(ct);
        cx = dba.open();
    }

    public Context getCt() {
        return ct;
    }

    public void setCt(Context ct) {
        this.ct = ct;
    }

    public boolean insertar(Estudiante estd){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_EST", estd.getId());
        contenedor.put("CARNET", estd.getCarnet());
        contenedor.put("NOMBRE", estd.getNombre());
        contenedor.put("ACTIVO", estd.getActivo());
        contenedor.put("ANIO_INGRESO", estd.getAnio_ingreso());
        return (cx.insert("ESTUDIANTE", null, contenedor)>0);
    }

    public boolean eliminar(int id){
        return (cx.delete("ESTUDIANTE", "ID_EST="+id, null)>0);
    }

    public boolean editar(Estudiante estd){
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_EST", estd.getId());
        contenedor.put("CARNET", estd.getCarnet());
        contenedor.put("NOMBRE", estd.getNombre());
        contenedor.put("ACTIVO", estd.getActivo());
        contenedor.put("ANIO_INGRESO", estd.getAnio_ingreso());
        return (cx.update("ESTUDIANTE", contenedor, "ID_EST="+estd.getId(), null)>0);
    }

    public ArrayList<Estudiante> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("SELECT * FROM ESTUDIANTE",null);

        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Estudiante(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Estudiante verUno(int id){
        Cursor cursor = cx.rawQuery("SELECT * FROM ESTUDIANTE", null);
        cursor.moveToPosition(id);
        estd = new Estudiante(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4));
        return estd;
    }
}
