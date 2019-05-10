package com.example.crud_encuesta.Componentes_AP.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_encuesta.Componentes_AP.Models.Pensum;
import com.example.crud_encuesta.Componentes_EL.Carrera.Carrera;
import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DAOPensum {
    SQLiteDatabase baseDeDatos;
    ArrayList<Pensum> pensums = new ArrayList<>();
    Pensum pensum;
    Context contexto;
    String nombreBD= "proy_aplicacion";


    //constructor
    public DAOPensum(Context context){
        this.contexto = context;

        DatabaseAccess dba = DatabaseAccess.getInstance(context);
        baseDeDatos = dba.open();

        /*
         *Abrir base de datos
         * DatabaseAccess dba = DatabaseAccess.getInstance(context);
         * baseDeDatos = dba.open();
         *
         * */
    }
    public Boolean Insertar(Pensum pensum){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CARRERA", pensum.getIdCarrera());
        contentValues.put("ANIO_PENSUM",pensum.getAnio());

        return (baseDeDatos.insert("PENSUM",null,contentValues)>0);
    }
    public Boolean Eliminar(Integer id){
        return (baseDeDatos.delete(
                "PENSUM",
                "ID_PENUM="+id,
                null)>0);
    }

    public Boolean Editar(Pensum pensum){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CARRERA", pensum.getIdCarrera());
        contentValues.put("ANIO_PENSUM",pensum.getAnio());

        return (baseDeDatos.update(
                "PENSUM",
                contentValues,
                "ID_PENUM =" + pensum.getIdPensum(),
                null)>0);
    }

    public ArrayList<Pensum> verTodos(){
        pensums.clear(); //limpiamos lista del adapter
        Cursor cursor  = baseDeDatos.rawQuery(
                "Select * FROM PENSUM",
                null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                pensums.add(new Pensum(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2)
                ));

            }while (cursor.moveToNext());
        }
        return pensums;
    }

    public ArrayList<String> SpinnerCarreras(){
        ArrayList<String> carreras = new ArrayList<String>();
        carreras.add("Seleccione");
        Cursor cursor  = baseDeDatos.rawQuery(
                "Select * FROM CARRERA",
                null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                carreras.add(cursor.getInt(0) + " - " + cursor.getString(2));
            }while (cursor.moveToNext());
        }
        return carreras;
    }

    public String getNombreCarrera(Pensum pensum){
        String nombreCarrera = null;
        Cursor cursor  = baseDeDatos.rawQuery(
                "Select * FROM CARRERA WHERE ID_CARRERA = " +pensum.getIdCarrera(),
                null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            nombreCarrera = cursor.getString(2);
        }
        return nombreCarrera;
    }

    //retorna la lista de pensum de una carrera
    public ArrayList<Pensum> verUno(String carrera){
        pensums.clear();
        Cursor cursorCarrrera  = baseDeDatos.rawQuery(
                "Select * FROM CARRERA WHERE NOMBRE_CARRERA LIKE '%" + carrera + "%'",
                null);
        if(cursorCarrrera.moveToFirst()){
            cursorCarrrera.moveToFirst();
            do {
                Cursor cursorPensum = baseDeDatos.rawQuery(
                        "Select * FROM PENSUM WHERE ID_CARRERA = " + cursorCarrrera.getInt(0),
                        null
                );
                if (cursorPensum.moveToFirst()){
                    cursorPensum.moveToFirst();
                    do {
                        pensums.add(new Pensum(
                                cursorPensum.getInt(0),
                                cursorPensum.getInt(1),
                                cursorPensum.getInt(2)
                        ));
                    }while (cursorPensum.moveToNext());
                }
            }while (cursorCarrrera.moveToNext());
        }
        return pensums;
    }
}
