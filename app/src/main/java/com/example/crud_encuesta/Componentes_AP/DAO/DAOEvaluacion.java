package com.example.crud_encuesta.Componentes_AP.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_encuesta.Componentes_AP.Models.Evaluacion;
import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DAOEvaluacion {

    SQLiteDatabase baseDeDatos;
    ArrayList<Evaluacion> evaluaciones = new ArrayList<>();
    Evaluacion evaluacion;
    Context contexto;
    String nombreBD= "proy_aplicacion";

    //constructor
    public DAOEvaluacion(Context context){
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


    public Boolean Insertar(Evaluacion evaluacion){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_CARG_ACA", evaluacion.getIdCargaAcad() );
        contentValues.put("DURACION",evaluacion.getDuracion());
        contentValues.put("INTENTO",evaluacion.getCantIntento());
        contentValues.put("NOMBRE_EVALUACION",evaluacion.getNombre());
        contentValues.put("DESCRIPCION_EVALUACION",evaluacion.getDescripcion());
        contentValues.put("RETROCEDER",evaluacion.getRetroceder());


        return (baseDeDatos.insert("EVALUACION",null,contentValues)>0);
    }

    public Boolean Eliminar(Integer id){
        return (baseDeDatos.delete("EVALUACION","ID_EVALUACION="+id,null)>0);
    }

    public Boolean Editar(Evaluacion evaluacion){
        ContentValues contentValues = new ContentValues();

        contentValues.put("DURACION",evaluacion.getDuracion());
        contentValues.put("INTENTO",evaluacion.getCantIntento());
        contentValues.put("NOMBRE_EVALUACION",evaluacion.getNombre());
        contentValues.put("DESCRIPCION_EVALUACION",evaluacion.getDescripcion());
        contentValues.put("RETROCEDER",evaluacion.getRetroceder());


        return (baseDeDatos.update("EVALUACION",contentValues,"ID_EVALUACION =" + evaluacion.getId(),null)>0);
    }

    public ArrayList<Evaluacion> verTodos(){
        evaluaciones.clear(); //limpiamos lista del adapter
        Cursor cursor  = baseDeDatos.rawQuery("Select * FROM EVALUACION",null);
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                evaluaciones.add(new Evaluacion(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                ));

            }while (cursor.moveToNext());
        }
        return evaluaciones;
    }

    //retorna la lista pero solo con el elemento buscado
    public ArrayList<Evaluacion> verUno(String nombre){
        evaluaciones.clear();
        Cursor cursor  = baseDeDatos.rawQuery("Select * FROM EVALUACION WHERE NOMBRE_EVALUACION = '" + nombre + "'"  ,null);
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do {
                evaluaciones.add(new Evaluacion(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                ));
            }while (cursor.moveToNext());
        }
        return evaluaciones;
    }


}
