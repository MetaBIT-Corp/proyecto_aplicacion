package com.example.crud_encuesta.Componentes_DC.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Adaptadores.AdaptadorOpcion;
import com.example.crud_encuesta.Componentes_DC.Dao.DaoOpcion;
import com.example.crud_encuesta.Componentes_DC.Objetos.Opcion;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class OpcionActivity extends AppCompatActivity {

    private DaoOpcion dao;
    private AdaptadorOpcion adaptador;
    private ArrayList<Opcion> lista_opciones;
    private Opcion opcion;

    private int id_pregunta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id_pregunta = b.getInt("id_pregunta");

        dao = new DaoOpcion(this, id_pregunta);
        lista_opciones = dao.verTodos();
        adaptador = new AdaptadorOpcion(lista_opciones,this,dao);
        ListView list = (ListView)findViewById(R.id.lista);
        FloatingActionButton agregar = findViewById(R.id.btn_nuevo);
        list.setAdapter(adaptador);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(OpcionActivity.this);
                dialog.setTitle("Nueva Opcion");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_opcion);
                dialog.show();

                final EditText texto_opcion = (EditText)dialog.findViewById(R.id.editt_opcion);
                final CheckBox cb_correcta = (CheckBox)dialog.findViewById(R.id.cb_correcta);


                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);
                TextView texto_titulo = (TextView)dialog.findViewById(R.id.texto_titulo);
                texto_titulo.setText("Agregar opción");
                agregar.setText("Agregar");
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int check = 0;

                        if(cb_correcta.isChecked())check = 1;

                        try{

                            if(!texto_opcion.getText().toString().equals("")){

                                opcion = new Opcion(id_pregunta, texto_opcion.getText().toString(), check);

                                dao.insertar(opcion);
                                adaptador.notifyDataSetChanged();
                                lista_opciones = dao.verTodos();
                                dialog.dismiss();

                            }else{
                                Toast.makeText(v.getContext(), "¡Ingrese el texto de la opción!", Toast.LENGTH_SHORT).show();
                                texto_opcion.setFocusable(true);
                            }

                        }catch (Exception e){
                            Log.d("BUSQUEDA", "CATCH");
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
