package com.example.crud_encuesta.Componentes_R;

import com.example.crud_encuesta.Componentes_Docente.Docente;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.crud_encuesta.R;

import java.text.ParseException;
import java.util.ArrayList;

public class Operaciones_CRUD {

    public static final Toast insertar(SQLiteDatabase db, ContentValues c, Context context, String table_name) {
        try {
            db.insert(table_name, null, c);
            return Toast.makeText(context, R.string.men_guardar, Toast.LENGTH_SHORT);

        } catch (Exception e) {
            return Toast.makeText(context, "Error", Toast.LENGTH_SHORT);
        }
    }

    public static void eliminar(final SQLiteDatabase db, final Context context, final String table_name, String column_name,int id) {
        final String condicion = column_name + " = ?";
        final String[] argumentos = {""+id};
        db.delete(table_name, condicion, argumentos);
        Toast.makeText(context, R.string.men_eliminar, Toast.LENGTH_SHORT).show();
    }

    public static final Toast actualizar(SQLiteDatabase db, ContentValues c, Context context, String table_name,String column_name,int id) {
        try {
            final String condicion = column_name + " = ?";
            final String[] argumentos = {""+id};
            db.update(table_name,c,condicion,argumentos);
            return Toast.makeText(context, R.string.men_actualizar, Toast.LENGTH_SHORT);

        } catch (Exception e) {
            return Toast.makeText(context, "Error", Toast.LENGTH_SHORT);
        }
    }



    public static ArrayList<Escuela> todosEscuela(String table_name, SQLiteDatabase db) {
        ArrayList<Escuela> lista = new ArrayList<>();
        Cursor c = db.rawQuery(" SELECT * FROM " + table_name, null);
        if (c.moveToFirst()){
            c.moveToFirst();
            Escuela e;
            do {
                e = new Escuela();
                e.setId(c.getInt(0));
                e.setNombre(c.getString(1));
                lista.add(e);
            }while (c.moveToNext());
        }
        return lista;
    }

    public static ArrayList<Carrera> todosCarrera(SQLiteDatabase db, ArrayList<Escuela> escuelas) {
        ArrayList<Carrera> lista = new ArrayList<>();
        Cursor cu = db.rawQuery(" SELECT * FROM " + EstructuraTablas.CARRERA_TABLA_NAME, null);
        cu.moveToFirst();
        Carrera c;
        do{
            c = new Carrera();
            c.setId(cu.getInt(0));
            for (int i=0;i<escuelas.size();i++){
                if(cu.getInt(1)==escuelas.get(i).getId()) c.setEscuela(escuelas.get(i));
            }
            c.setNombre(cu.getString(2));
            lista.add(c);
        }while (cu.moveToNext());
        return lista;
    }

    public static ArrayList<Pensum> todosPensum(SQLiteDatabase db) {
        ArrayList<Pensum> lista = new ArrayList<>();
        Cursor cu = db.rawQuery(" SELECT * FROM " + EstructuraTablas.PENSUM_TABLA_NAME, null);
        cu.moveToFirst();
        Pensum p;
        do{
            p = new Pensum();
            p.setId(cu.getInt(0));
            p.setAño(cu.getInt(1));
            lista.add(p);
        }while (cu.moveToNext());
        return lista;
    }

    public static ArrayList<Materia> todosMateria(SQLiteDatabase db, ArrayList<Carrera> c,ArrayList<Pensum> p) {
        ArrayList<Materia> lista = new ArrayList<>();
        Cursor cu = db.rawQuery(" SELECT * FROM " + EstructuraTablas.MATERIA_TABLA_NAME, null);
        cu.moveToFirst();
        Materia m;
        while (cu.moveToNext()) {
            m=new Materia();
            m.setId(cu.getInt(0));
            for (int i=0;i<p.size();i++){
                if(cu.getInt(1)==p.get(i).getId()) m.setPensum(p.get(i));
            }
            for (int i=0;i<c.size();i++){
                if(cu.getInt(2)==c.get(i).getId()) m.setCarrera(c.get(i));
            }
            m.setCodigo_materia(cu.getString(3));
            m.setNombre(cu.getString(4));
            m.setElectiva(cu.getInt(5)==1);
            m.setMaximo_preguntas(cu.getInt(6));
            lista.add(m);
        }
        System.out.println(c.get(0).getNombre());
        return lista;
    }

    public static ArrayList<Encuesta> todosEncuesta(SQLiteDatabase db, ArrayList<Docente> d){
        ArrayList<Encuesta> lista = new ArrayList<>();
        Cursor c = db.rawQuery(" SELECT * FROM " + EstructuraTablas.ENCUESTA_TABLA_NAME, null);

        if(c.moveToFirst()){
            c.moveToFirst();
            Encuesta e;
            do {
                e = new Encuesta();
                e.setId(c.getInt(0));
                for (int i=0;i<d.size();i++){
                    if(c.getInt(1)==d.get(i).getId()) e.setId_docente(d.get(i));
                }
                e.setTitulo(c.getString(2));
                e.setDescripcion(c.getString(3));
                e.setFecha_in(c.getString(4));
                e.setFecha_fin(c.getString(5));
                lista.add(e);
            }while (c.moveToNext());
        }
        return lista;
    }

    public static ArrayList<Docente> todosDocente(SQLiteDatabase db){

        ArrayList<Docente> lista = new ArrayList<>();
        Cursor c = db.rawQuery(" SELECT * FROM " + EstructuraTablas.DOCENTE_TABLE_NAME, null);

        if(c.moveToFirst()) {
            c.moveToFirst();
            Docente d;
            do {
                d = new Docente();
                d.setId(c.getInt(0));
                d.setId_escuela(c.getInt(1));
                d.setCarnet(c.getString(2));
                d.setAnio_titulacion(c.getString(3));
                d.setActivo(c.getInt(4));
                d.setTipo_jornada(c.getInt(5));
                d.setDescripcion(c.getString(6));
                d.setCargo_actual(c.getInt(7));
                d.setCargo_secundario(c.getInt(8));
                d.setNombre(c.getString(9));
                lista.add(d);
            } while (c.moveToNext());
        }
        return lista;
    }
}
