package com.example.crud_encuesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DaoGrupoEmp {

    private SQLiteDatabase cx;
    private ArrayList<GrupoEmparejamiento> lista_gpo_emp=new ArrayList<>();
    private GrupoEmparejamiento gpo_emp;
    private Context ct;
    private String nombreBD = "proy_aplicacion.db";

    public DaoGrupoEmp(Context ct){
        this.ct = ct;
        DatabaseAccess dba = DatabaseAccess.getInstance(ct);
        cx = dba.open();

    }

    public boolean insertar(GrupoEmparejamiento gpo_emp){

        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_AREA",gpo_emp.getId_area());
        contenedor.put("DESCRIPCION_GRUPO_EMP",gpo_emp.getDescripcion());
        return (cx.insert("GRUPO_EMPAREJAMIENTO",null,contenedor)>0);
    }

    public boolean eliminar(int id){
        return true;
    }

    public boolean editar(GrupoEmparejamiento gpo_emp){
        return true;
    }

    public ArrayList<GrupoEmparejamiento> verTodos(){
        lista_gpo_emp.clear();

        try{
            Log.d("Error","Aqui antes");
            Cursor cursor = cx.rawQuery("SELECT * FROM grupo_emparejamiento", null);
            cursor.moveToFirst();

            do {
                lista_gpo_emp.add(new GrupoEmparejamiento(cursor.getInt(cursor.getColumnIndex("ID_AREA")), cursor.getString(cursor.getColumnIndex("GRUPO_EMPAREJAMIENTO"))));
            }while (cursor.moveToNext());

        }catch (Exception e){
            Log.d("Error","Aqui despues");
        }

        return lista_gpo_emp;
    }

    public GrupoEmparejamiento verUno(int position){

        Cursor cursor = cx.rawQuery("SELECT * FROM GRUPO_EMPAREJAMIENTO", null);

        cursor.moveToPosition(position);
        gpo_emp = new GrupoEmparejamiento(cursor.getInt(cursor.getColumnIndex("ID_AREA")), cursor.getString(cursor.getColumnIndex("GRUPO_EMPAREJAMIENTO")));
        return gpo_emp;
    }
}
