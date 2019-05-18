package com.example.crud_encuesta.Componentes_DC.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Objetos.Opcion;
import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DaoOpcion {

    private SQLiteDatabase cx;
    private ArrayList<Opcion> lista_opciones=new ArrayList<>();
    private Opcion opcion;
    private Context ct;
    private String nombreBD = "proy_aplicacion.db";
    private int id_pregunta;
    private int es_respuesta_corta;
    private DatabaseAccess dba;

    public DaoOpcion(Context ct, int id_pregunta, int es_verdadero_falso, int es_respuesta_corta){
        this.ct = ct;
        this.id_pregunta = id_pregunta;
        this.dba = DatabaseAccess.getInstance(ct);
        this.es_respuesta_corta = es_respuesta_corta;
        if (es_verdadero_falso==1)insertar_automatico();
    }

    private void insertar_automatico(){
        cx = dba.open();
        Cursor cursor = cx.rawQuery("SELECT*FROM OPCION WHERE ID_PREGUNTA="+id_pregunta,null);
     if(cursor.getCount()==0){
         Opcion opcion_verdadero = new Opcion(id_pregunta, "Verdadero",1);
         Opcion opcion_falso = new Opcion(id_pregunta, "Falso",0);
         insertar(opcion_verdadero);
         insertar(opcion_falso);
        }
    }

    public void cambiar(){
        cx = dba.open();
        Cursor cursor = cx.rawQuery("SELECT*FROM OPCION WHERE ID_PREGUNTA="+id_pregunta+" AND CORRECTA="+0,null);
        if (cursor.getCount()==1){
            cursor.moveToFirst();
            Opcion opcion = new Opcion(cursor.getInt(cursor.getColumnIndex("ID_OPCION")), cursor.getInt(cursor.getColumnIndex("ID_PREGUNTA")),cursor.getString(cursor.getColumnIndex("OPCION")), 1);
            editar(opcion);
        }
    }

    public boolean insertar(Opcion opcion){
        cx = dba.open();
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_PREGUNTA ",opcion.getId_pregunta());
        contenedor.put("OPCION",opcion.getOpcion());

        if (opcion.getCorrecta()==1 && es_respuesta_corta!=1){

                ContentValues contenedor_alterno = new ContentValues();
                contenedor_alterno.put("CORRECTA",0);
                cx.update("OPCION",contenedor_alterno,"ID_PREGUNTA="+id_pregunta, null);

        }

        contenedor.put("CORRECTA",opcion.getCorrecta());
        return (cx.insert("OPCION",null,contenedor)>0);
    }

    public boolean eliminar(int id){
        cx = dba.open();
        return (cx.delete("OPCION","ID_OPCION="+id, null)>0);
    }

    public boolean editar(Opcion opcion){
        cx = dba.open();
        ContentValues contenedor = new ContentValues();
        contenedor.put("OPCION",opcion.getOpcion());

        if (opcion.getCorrecta()==1 && es_respuesta_corta!=1){
            ContentValues contenedor_alterno = new ContentValues();
            contenedor_alterno.put("CORRECTA",0);
            cx.update("OPCION",contenedor_alterno,"ID_PREGUNTA="+id_pregunta, null);
        }
        contenedor.put("CORRECTA",opcion.getCorrecta());
        return (cx.update("OPCION",contenedor,"ID_OPCION="+opcion.getId(), null)>0);
    }

    public ArrayList<Opcion> verTodos(){
        cx = dba.open();
        lista_opciones.clear();

        try{

            Cursor cursor = cx.rawQuery("SELECT * FROM OPCION WHERE ID_PREGUNTA="+id_pregunta, null);
            cursor.moveToFirst();

            do {
                lista_opciones.add(new Opcion(cursor.getInt(cursor.getColumnIndex("ID_OPCION")), cursor.getInt(cursor.getColumnIndex("ID_PREGUNTA")),cursor.getString(cursor.getColumnIndex("OPCION")),cursor.getInt(cursor.getColumnIndex("CORRECTA"))));
            }while (cursor.moveToNext());

        }catch (Exception e){

        }

        return lista_opciones;
    }

    public Opcion verUno(int position){
        cx = dba.open();
        Cursor cursor = cx.rawQuery("SELECT * FROM OPCION", null);
        cursor.moveToPosition(position);
        opcion = new Opcion(cursor.getInt(cursor.getColumnIndex("ID_OPCION")), cursor.getInt(cursor.getColumnIndex("ID_PREGUNTA")),cursor.getString(cursor.getColumnIndex("OPCION")),cursor.getInt(cursor.getColumnIndex("CORRECTA")));
        return opcion;
    }
}
