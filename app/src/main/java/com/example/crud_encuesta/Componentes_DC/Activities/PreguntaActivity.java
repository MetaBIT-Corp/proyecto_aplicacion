package com.example.crud_encuesta.Componentes_DC.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Adaptadores.AdaptadorPregunta;
import com.example.crud_encuesta.Componentes_DC.Dao.DaoPregunta;
import com.example.crud_encuesta.Componentes_DC.Objetos.Pregunta;
import com.example.crud_encuesta.Componentes_MT.AreaActivity;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class PreguntaActivity extends AppCompatActivity {

    private DaoPregunta dao;
    private AdaptadorPregunta adaptador;
    private ArrayList<Pregunta> lista_preguntas;
    private Pregunta pregunta;

    private int id_gpo_emp;
    private String desc_gpo_emp;
    private  int id_area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        id_gpo_emp = b.getInt("id_gpo_emp");
        desc_gpo_emp = b.getString("desc_gpo_emp");
        id_area = b.getInt("id_area");

        dao = new DaoPregunta(this, id_gpo_emp);

        lista_preguntas = dao.verTodos();
        adaptador = new AdaptadorPregunta(lista_preguntas,this,dao);
        ListView list = (ListView)findViewById(R.id.lista);
        FloatingActionButton agregar = findViewById(R.id.btn_nuevo);
        list.setAdapter(adaptador);
        TextView texto_desc_emp = (TextView)findViewById(R.id.txt_desc_emp);
        texto_desc_emp.setText(desc_gpo_emp);
       /* Button editar_gpo_emp = (Button)findViewById(R.id.btn_editar_desc_emp);

        editar_gpo_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PreguntaActivity.this, GpoEmpActivity.class);
                in.putExtra("id_gpo_emp",id_gpo_emp);
                in.putExtra("id_tipo_item",3);
                in.putExtra("accion",1);
                in.putExtra("id_area",id_area);
                startActivity(in);
            }
        });*/

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
                TextView texto_titulo = (TextView)dialog.findViewById(R.id.texto_titulo);
                texto_titulo.setText("Agregar pregunta");
                agregar.setText("Agregar");
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if(!texto_pregunta.getText().toString().equals("")){

                                pregunta = new Pregunta(id_gpo_emp, texto_pregunta.getText().toString());
                                dao.insertar(pregunta);
                                adaptador.notifyDataSetChanged();
                                lista_preguntas = dao.verTodos();
                                dialog.dismiss();

                            }else{
                                Toast.makeText(v.getContext(), "¡Ingrese el texto de la pregunta!", Toast.LENGTH_SHORT).show();
                                texto_pregunta.setFocusable(true);
                            }

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

    public void onBackPressed()
    {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(new Intent(PreguntaActivity.this, AreaActivity.class));

        return;
    }
}
