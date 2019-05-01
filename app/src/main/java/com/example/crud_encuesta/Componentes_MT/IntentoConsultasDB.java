package com.example.crud_encuesta.Componentes_MT;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class IntentoConsultasDB {

    public static String getDescripcion(int id_gpo_emp, SQLiteDatabase db){

        String descripcion;
        String sentencia;

        sentencia="SELECT DESCRIPCION_GRUPO_EMP FROM GRUPO_EMPAREJAMIENTO WHERE ID_GRUPO_EMP="+id_gpo_emp;

        Cursor cursor = db.rawQuery(sentencia,null);
        cursor.moveToFirst();

        if(cursor != null){
            descripcion = cursor.getString(0);
        }else{
            descripcion="";
        }
        return descripcion;
    }

    public static int getCatidadPreguntasPorGrupo(int id_gpo_emp, SQLiteDatabase db){
        String descripcion;
        String sentencia;

        sentencia="SELECT ID_PREGUNTA FROM PREGUNTA WHERE ID_GRUPO_EMP="+id_gpo_emp;

        Cursor cursor = db.rawQuery(sentencia,null);

        return cursor.getCount();
    }

    public static float getPonderacion(int id_pregunta, SQLiteDatabase db){

        float valor_pregunta;
        String sentencia;
        int peso;
        int cantidad;

        sentencia="SELECT NUMERO_PREGUNTAS, PESO FROM CLAVE_AREA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA_PREGUNTA WHERE ID_PREGUNTA="+id_pregunta+")";

        Cursor cursor = db.rawQuery(sentencia,null);
        cursor.moveToFirst();

        peso = cursor.getInt(1);
        cantidad = cursor.getInt(0);

        float peso_f = (float)peso;
        float cantidad_f = (float)cantidad;

        valor_pregunta = (peso_f/cantidad_f)/10;
        return valor_pregunta;
    }

    public static int getModalidad(int id_pregunta, SQLiteDatabase db){
        int id_modalidad;
        String sentencia;

        sentencia="SELECT ID_TIPO_ITEM FROM AREA WHERE ID_AREA IN\n" +
                "(SELECT ID_AREA FROM CLAVE_AREA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA_PREGUNTA WHERE ID_PREGUNTA="+id_pregunta+"))";

        Cursor cursor = db.rawQuery(sentencia,null);
        cursor.moveToFirst();

        id_modalidad= cursor.getInt(0);
        return id_modalidad;
    }

    public static int ultimo_intento(int id_usuario, SQLiteDatabase db) {
        int numero_intento = 0;

        Cursor cursor = db.rawQuery("SELECT NUMERO_INTENTO FROM INTENTO WHERE ID_GEN_EST="+id_usuario+" ORDER BY ID_INTENTO DESC LIMIT 1", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            numero_intento = cursor.getInt(0);
        }

        return numero_intento;
    }

    public static int getClaveUltimoIntento(int ultimo_intento, SQLiteDatabase db){

        Cursor cursor = db.rawQuery("SELECT ID_CLAVE FROM INTENTO WHERE NUMERO_INTENTO ="+ultimo_intento, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public static int id_ultimo_intento(int id_usuario, SQLiteDatabase db) {
        int id_numero_intento = 0;

        Cursor cursor = db.rawQuery("SELECT ID_INTENTO FROM INTENTO WHERE ID_GEN_EST="+id_usuario+" ORDER BY ID_INTENTO DESC LIMIT 1", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            id_numero_intento = cursor.getInt(0);
        }

        return id_numero_intento;
    }

}
