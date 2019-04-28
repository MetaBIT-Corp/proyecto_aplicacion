package com.example.crud_encuesta.Componentes_MT;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class IntentoActivity extends AppCompatActivity {
    private ListView listView;
    Tamanio tamanio;
    private int id_clave = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intento);

        listView = (ListView)findViewById(R.id.lsPreguntas);
        listView.setAdapter(new IntentoAdapter(getPreguntas(), this, tamanio));
    }

    public List<Pregunta> getPreguntas(){
        tamanio = new Tamanio();
        List<Pregunta> preguntas = new ArrayList<>();
        List<PreguntaP> preguntaPList = new ArrayList<>();
        String descripcion="";
        String pregunta;
        float ponderacion;
        int id;
        int id_gpo;
        int respuesta=0;
        int modalidad=0;
        boolean emparejamiento=false;
        int i = 1;

        Log.d("Hey", "Aqui 1");

        String sentencia_pregunta = "SELECT ID_PREGUNTA, ID_GRUPO_EMP, PREGUNTA FROM PREGUNTA WHERE ID_PREGUNTA IN\n" +
                "(SELECT ID_PREGUNTA FROM CLAVE_AREA_PREGUNTA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA WHERE ID_CLAVE ="+id_clave+"))";

        String sentencia_opcion = "SELECT ID_OPCION, OPCION, CORRECTA FROM OPCION WHERE ID_PREGUNTA =";

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        Log.d("Hey", "Aqui 2");

        Cursor cursor_pregunta = db.rawQuery(sentencia_pregunta, null);

        Log.d("Hey", "Aqui 3");

        while (cursor_pregunta.moveToNext()){
            List<String> opciones = new ArrayList<>();
            List<Integer> ides = new ArrayList<>();

            if(emparejamiento){
                i++;
            }else{
                preguntaPList = new ArrayList<>();
            }

            Log.d("Hey", "Aqui 4");

            id = cursor_pregunta.getInt(0);

            Log.d("Hey", "Aqui 5");
            pregunta = cursor_pregunta.getString(2);
            Log.d("Hey", "Aqui 6");
            id_gpo = cursor_pregunta.getInt(1);

            Log.d("Hey", "Aqui 7");
            ponderacion = getPonderacion(id);
            Log.d("Hey", "Aqui 8");
            modalidad = getModalidad(id);
            Log.d("Hey", "Aqui 9");
            Log.d("Hey", "id_GPO: "+id_gpo);
            descripcion = getDescripcion(id_gpo);
            Log.d("Hey", "Aqui 10");

            Cursor cursor_opcion = db.rawQuery(sentencia_opcion+id, null);
            while (cursor_opcion.moveToNext()){
                ides.add(cursor_opcion.getInt(0));
                opciones.add(cursor_opcion.getString(1));

                if(cursor_opcion.getInt(2)==1) respuesta=cursor_opcion.getInt(0);
            }

            if(modalidad==3){
                emparejamiento=true;
                preguntaPList.add(new PreguntaP(pregunta, id, respuesta, ponderacion, ides, opciones));
                if(i==getCatidadPreguntasPorGrupo(id_gpo)){
                    preguntas.add(new Pregunta(descripcion, modalidad, preguntaPList));
                    emparejamiento=false;
                    i = 1;
                }
                tamanio.setEmparejamiento(tamanio.getEmparejamiento()+1);
            }else{
                preguntaPList.add(new PreguntaP(pregunta, id, respuesta, ponderacion, ides, opciones));
                preguntas.add(new Pregunta(descripcion, modalidad, preguntaPList));
                tamanio.setOpcion_multiple(tamanio.getOpcion_multiple()+1);
            }
            cursor_opcion.close();
        }

        databaseAccess.close();
        cursor_pregunta.close();

        Log.d("OP: ", ""+tamanio.getOpcion_multiple());
        Log.d("EM", ""+tamanio.getEmparejamiento());

        return preguntas;
    }

    public String getDescripcion(int id_gpo_emp){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

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

    public int getCatidadPreguntasPorGrupo(int id_gpo_emp){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        String descripcion;
        String sentencia;

        sentencia="SELECT ID_PREGUNTA FROM PREGUNTA WHERE ID_GRUPO_EMP="+id_gpo_emp;

        Cursor cursor = db.rawQuery(sentencia,null);

        return cursor.getCount();
    }

    public float getPonderacion(int id_pregunta){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

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

    public int getModalidad(int id_pregunta){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        int id_modalidad;
        String sentencia;

        sentencia="SELECT ID_TIPO_ITEM FROM AREA WHERE ID_AREA IN\n" +
                "(SELECT ID_AREA FROM CLAVE_AREA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA_PREGUNTA WHERE ID_PREGUNTA="+id_pregunta+"))";

        Cursor cursor = db.rawQuery(sentencia,null);
        cursor.moveToFirst();

        id_modalidad= cursor.getInt(0);
        Log.d("ID modalidad",String.valueOf(id_modalidad));
        return id_modalidad;
    }


    /*
    public List<Pregunta> getPreguntas(){
        List<Pregunta> preguntas = new ArrayList<>();
        String pregunta;
        float ponderacion;
        int id;
        int respuesta=0;
        int modalidad;

        String sentencia_pregunta = "SELECT ID_PREGUNTA, PREGUNTA FROM PREGUNTA WHERE ID_PREGUNTA IN\n" +
                "(SELECT ID_PREGUNTA FROM CLAVE_AREA_PREGUNTA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA WHERE ID_CLAVE ="+id_clave+"))";

        String sentencia_opcion = "SELECT ID_OPCION, OPCION, CORRECTA FROM OPCION WHERE ID_PREGUNTA =";

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        Cursor cursor_pregunta = db.rawQuery(sentencia_pregunta, null);

        while (cursor_pregunta.moveToNext()){
            List<String> opciones = new ArrayList<>();
            List<Integer> ides = new ArrayList<>();

            id = cursor_pregunta.getInt(0);
            pregunta = cursor_pregunta.getString(1);
            ponderacion = getPonderacion(id);
            modalidad = getModalidad(id);

            Cursor cursor_opcion = db.rawQuery(sentencia_opcion+id, null);
            while (cursor_opcion.moveToNext()){
                ides.add(cursor_opcion.getInt(0));
                opciones.add(cursor_opcion.getString(1));

                if(cursor_opcion.getInt(2)==1) respuesta=cursor_opcion.getInt(0);
            }
            preguntas.add(new Pregunta(pregunta, id, respuesta, ponderacion, modalidad, opciones, ides));
            cursor_opcion.close();
        }

        databaseAccess.close();
        cursor_pregunta.close();

        return preguntas;
    }

    public float getPonderacion(int id_pregunta){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

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

    public int getModalidad(int id_pregunta){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        int id_modalidad;
        String sentencia;

        sentencia="SELECT ID_TIPO_ITEM FROM AREA WHERE ID_AREA IN\n" +
                "(SELECT ID_AREA FROM CLAVE_AREA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA_PREGUNTA WHERE ID_PREGUNTA="+id_pregunta+"))";

        Cursor cursor = db.rawQuery(sentencia,null);
        cursor.moveToFirst();

        id_modalidad= cursor.getInt(0);
        Log.d("ID modalidad",String.valueOf(id_modalidad));
        return id_modalidad;
    }
    */
}

