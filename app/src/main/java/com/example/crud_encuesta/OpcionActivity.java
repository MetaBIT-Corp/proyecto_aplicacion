package com.example.crud_encuesta;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        Button agregar = (Button)findViewById(R.id.btn_nuevo);
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
                final CheckBox cb_correcta = (CheckBox)v.findViewById(R.id.cb_correcta);
                int check = 0;

                try{
                    if(cb_correcta.isChecked())check = 1;
                }catch (Exception e){}

                final int check_op = check;
                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);

                agregar.setText("Agregar");
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            opcion = new Opcion(id_pregunta, texto_opcion.getText().toString(), check_op);

                            dao.insertar(opcion);
                            adaptador.notifyDataSetChanged();
                            lista_opciones = dao.verTodos();
                            dialog.dismiss();

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
