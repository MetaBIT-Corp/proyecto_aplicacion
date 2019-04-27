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
    RadioGroup a;
    LinearLayout ly;
    private ListView listView;
    private int id_clave = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intento);

        //ly = (LinearLayout)findViewById(R.id.contedor);
        //ly.addView(prueba());
        //prueba2();

        listView = (ListView)findViewById(R.id.lsPreguntas);
        listView.setAdapter(new IntentoAdapter(getPreguntas(), this));

        //InteraccionView evento = new InteraccionView(this);
        //setContentView(evento);
    }

    public List<Pregunta> getPreguntas(){
        List<Pregunta> preguntas = new ArrayList<>();
        String pregunta;
        float ponderacion;
        int id;
        int respuesta=0;

        String sentencia_pregunta = "SELECT ID_PREGUNTA, PREGUNTA FROM PREGUNTA WHERE ID_PREGUNTA IN\n" +
                "(SELECT ID_PREGUNTA FROM CLAVE_AREA_PREGUNTA WHERE ID_CLAVE_AREA IN\n" +
                "(SELECT ID_CLAVE_AREA FROM CLAVE_AREA WHERE ID_CLAVE =1))";

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

            Cursor cursor_opcion = db.rawQuery(sentencia_opcion+id, null);
            while (cursor_opcion.moveToNext()){
                ides.add(cursor_opcion.getInt(0));
                opciones.add(cursor_opcion.getString(1));

                if(cursor_opcion.getInt(2)==1) respuesta=cursor_opcion.getInt(0);
            }
            preguntas.add(new Pregunta(pregunta, id, respuesta, ponderacion, opciones, ides));
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
}
