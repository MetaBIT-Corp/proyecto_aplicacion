package com.example.crud_encuesta.Componentes_DC.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Objetos.Pregunta;
import com.example.crud_encuesta.DatabaseAccess;

import java.util.ArrayList;

public class DaoPregunta {

    private SQLiteDatabase cx;
    private ArrayList<Pregunta> lista_preguntas=new ArrayList<>();
    private Pregunta pregunta;
    private Context ct;
    private String nombreBD = "proy_aplicacion.db";
    private int id_grupo_emp=0;
    private int id_area;

    public DaoPregunta(Context ct, int id_grupo_emp, int id_area){
        this.ct = ct;
        this.id_area = id_area;
        this.id_grupo_emp = id_grupo_emp;
        DatabaseAccess dba = DatabaseAccess.getInstance(ct);
        cx = dba.open();

    }

    public boolean insertar(Pregunta pregunta){

        ContentValues contenedor = new ContentValues();

        if(id_grupo_emp!=0){
            contenedor.put("ID_GRUPO_EMP ",pregunta.getId_grupo_emp());
        }else{
            contenedor.put("ID_GRUPO_EMP ",id_grupo_emp());
        }
        contenedor.put("PREGUNTA",pregunta.getPregunta());
        return (cx.insert("PREGUNTA",null,contenedor)>0);
    }

    private int id_grupo_emp(){
        int id_grupo_emp=0;
        ContentValues contenedor = new ContentValues();
        contenedor.put("ID_AREA",id_area);
        cx.insert("GRUPO_EMPAREJAMIENTO", null,contenedor);
        Cursor c = cx.rawQuery("SELECT ID_GRUPO_EMP FROM GRUPO_EMPAREJAMIENTO", null);
        c.moveToLast();
        id_grupo_emp = c.getInt(0);
        return id_grupo_emp;
    }

    public boolean eliminar(int id){

        return (cx.delete("PREGUNTA","ID_PREGUNTA="+id, null)>0);
    }

    public boolean editar(Pregunta pregunta){
        ContentValues contenedor = new ContentValues();
        contenedor.put("PREGUNTA",pregunta.getPregunta());
        return (cx.update("PREGUNTA",contenedor,"ID_PREGUNTA="+pregunta.getId(), null)>0);
    }

    public ArrayList<Pregunta> verTodos(){
        lista_preguntas.clear();

        if(id_grupo_emp != 0){
            try{

                Cursor cursor = cx.rawQuery("SELECT * FROM PREGUNTA WHERE ID_GRUPO_EMP="+id_grupo_emp, null);
                cursor.moveToFirst();

                do {
                    lista_preguntas.add(new Pregunta(cursor.getInt(cursor.getColumnIndex("ID_PREGUNTA")),cursor.getInt(cursor.getColumnIndex("ID_GRUPO_EMP")),cursor.getString(cursor.getColumnIndex("PREGUNTA"))));
                }while (cursor.moveToNext());

            }catch (Exception e){

            }
        }else{

            try{

                Cursor cursor = cx.rawQuery("SELECT ID_GRUPO_EMP FROM GRUPO_EMPAREJAMIENTO WHERE ID_AREA="+id_area, null);
                cursor.moveToFirst();
                do {
                    int id_gpo_emp = cursor.getInt(0);
                    try{

                        Cursor cursor2 = cx.rawQuery("SELECT * FROM PREGUNTA WHERE ID_GRUPO_EMP="+id_gpo_emp, null);
                        cursor2.moveToFirst();

                        do {
                            lista_preguntas.add(new Pregunta(cursor2.getInt(cursor2.getColumnIndex("ID_PREGUNTA")),cursor2.getInt(cursor2.getColumnIndex("ID_GRUPO_EMP")),cursor2.getString(cursor2.getColumnIndex("PREGUNTA"))));
                        }while (cursor2.moveToNext());

                    }catch (Exception e){

                    }
                }while (cursor.moveToNext());

            }catch (Exception e){

            }

        }


        return lista_preguntas;
    }

    public Pregunta verUno(int position){

        Cursor cursor = cx.rawQuery("SELECT * FROM PREGUNTA", null);
        cursor.moveToPosition(position);
        pregunta = new Pregunta(cursor.getInt(cursor.getColumnIndex("ID_PREGUNTA")),cursor.getInt(cursor.getColumnIndex("ID_GRUPO_EMP")),cursor.getString(cursor.getColumnIndex("PREGUNTA")));
        return pregunta;
    }

}
