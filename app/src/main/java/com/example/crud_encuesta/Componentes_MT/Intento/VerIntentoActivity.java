package com.example.crud_encuesta.Componentes_MT.Intento;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class VerIntentoActivity extends AppCompatActivity {
    ListView listView;
    TextView txtTitle;
    int id_usuario;
    double nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_intento);

        listView = (ListView)findViewById(R.id.lsVerPreguntas);
        txtTitle = findViewById(R.id.txtTitle);

        id_usuario = getIntent().getIntExtra("id_estudiante", 0);
        nota = getIntent().getDoubleExtra("nota", 0);

        txtTitle.setText("Nota: "+nota);
        listView.setAdapter(new VerIntentoAdapter(getPreguntas(), this, this));
    }

    public List<PreguntaRevision> getPreguntas(){
        List<PreguntaRevision> preguntas = new ArrayList<>();
        String descripcion="";
        String pregunta;
        String texto_eleccion;
        int id;
        int id_gpo;
        int respuesta=0;
        int modalidad=0;
        int ultimo_intento;
        int id_opcion_eleccion;
        int id_pregunta;

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        ultimo_intento = IntentoConsultasDB.id_ultimo_intento(id_usuario, db);

        String sentencia_pregunta = "SELECT * FROM PREGUNTA WHERE ID_PREGUNTA =";

        String sentencia_opcion = "SELECT ID_OPCION, OPCION, CORRECTA FROM OPCION WHERE ID_PREGUNTA =";

        Cursor cursor_eleccion = db.rawQuery("SELECT ID_OPCION, ID_PREGUNTA, TEXTO_RESPUESTA FROM RESPUESTA WHERE ID_INTENTO ="+ultimo_intento, null);

        while (cursor_eleccion.moveToNext()){
            List<String> opciones = new ArrayList<>();
            List<Integer> ides = new ArrayList<>();

            id_opcion_eleccion = cursor_eleccion.getInt(0);
            id_pregunta = cursor_eleccion.getInt(1);
            texto_eleccion = cursor_eleccion.getString(2);

            Cursor cursor_pregunta = db.rawQuery(sentencia_pregunta+id_pregunta, null);
            cursor_pregunta.moveToFirst();

            id = cursor_pregunta.getInt(0);
            id_gpo = cursor_pregunta.getInt(1);
            pregunta = cursor_pregunta.getString(2);

            modalidad = getModalidad(db, id);
            descripcion = IntentoConsultasDB.getDescripcion(id_gpo, db);

            Cursor cursor_opcion = db.rawQuery(sentencia_opcion+id, null);
            while (cursor_opcion.moveToNext()){
                ides.add(cursor_opcion.getInt(0));
                opciones.add(cursor_opcion.getString(1));
                if(cursor_opcion.getInt(2)==1) respuesta=cursor_opcion.getInt(0);
            }

            preguntas.add(new PreguntaRevision(pregunta, descripcion, texto_eleccion, modalidad, id_opcion_eleccion, respuesta, opciones, ides));

            cursor_opcion.close();
        }

        cursor_eleccion.close();

        return preguntas;
    }

    public int getModalidad(SQLiteDatabase db, int id_pregunta){

        Cursor cursor = db.rawQuery("SELECT ID_TIPO_ITEM FROM AREA WHERE ID_AREA =\n" +
                "(SELECT ID_AREA FROM GRUPO_EMPAREJAMIENTO WHERE ID_GRUPO_EMP =\n" +
                "(SELECT ID_GRUPO_EMP FROM PREGUNTA WHERE ID_PREGUNTA =  "+id_pregunta+"))", null);

        cursor.moveToFirst();
        return cursor.getInt(0);
    }



}
