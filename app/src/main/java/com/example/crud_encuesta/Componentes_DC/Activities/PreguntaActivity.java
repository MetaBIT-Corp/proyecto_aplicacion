package com.example.crud_encuesta.Componentes_DC.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Adaptadores.AdaptadorPregunta;
import com.example.crud_encuesta.Componentes_DC.Dao.DaoPregunta;
import com.example.crud_encuesta.Componentes_DC.Objetos.Pregunta;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class PreguntaActivity extends AppCompatActivity {

    private DaoPregunta dao;
    private AdaptadorPregunta adaptador;
    private ArrayList<Pregunta> lista_preguntas;
    private Pregunta pregunta;

    private int id_gpo_emp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id_gpo_emp = b.getInt("id_gpo_emp");
        dao = new DaoPregunta(this, id_gpo_emp);

        lista_preguntas = dao.verTodos();
        adaptador = new AdaptadorPregunta(lista_preguntas,this,dao);
        ListView list = (ListView)findViewById(R.id.lista);
        Button agregar = (Button)findViewById(R.id.btn_nuevo);
        list.setAdapter(adaptador);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PreguntaActivity.this);
                dialog.setTitle("Nueva Pregunta");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_pregunta);
                dialog.show();

                final EditText texto_pregunta = (EditText)dialog.findViewById(R.id.editt_pregunta);
                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);

                agregar.setText("Agregar");
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{

                            pregunta = new Pregunta(id_gpo_emp, texto_pregunta.getText().toString());
                            dao.insertar(pregunta);
                            adaptador.notifyDataSetChanged();
                            lista_preguntas = dao.verTodos();
                            dialog.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
