package com.example.crud_encuesta.Componentes_DC.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Adaptadores.Adaptador;
import com.example.crud_encuesta.Componentes_DC.Dao.DaoGrupoEmp;
import com.example.crud_encuesta.Componentes_DC.Objetos.GrupoEmparejamiento;
import com.example.crud_encuesta.Componentes_MT.AreaActivity;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class GpoEmpActivity extends AppCompatActivity {

    private DaoGrupoEmp dao;
    private Adaptador adaptador;
    private ArrayList<GrupoEmparejamiento> lista_gpo_emp;
    private GrupoEmparejamiento gpo_emp;
    private int id_area;
    private int id_tipo_item;
    private int accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grupo_emparejamiento);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id_area = b.getInt("id_area");
        dao = new DaoGrupoEmp(this, id_area);
        lista_gpo_emp = dao.verTodos();
        adaptador = new Adaptador(lista_gpo_emp, this, dao);

        //Inicio
        id_tipo_item = b.getInt("id_tipo_item");
        accion = b.getInt("accion");

        //Fin

        FloatingActionButton agregar = (FloatingActionButton)findViewById(R.id.btn_nuevo);
        ListView list = (ListView)findViewById(R.id.lista);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(GpoEmpActivity.this);
                dialog.setTitle("Nuevo Grupo Emparejamiento");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_gpo_emp);
                dialog.show();

                final EditText descripcion = (EditText)dialog.findViewById(R.id.descripcion);
                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);
                TextView texto_titulo = (TextView)dialog.findViewById(R.id.texto_titulo);
                texto_titulo.setText("Agregar grupo emparejamiento");
                agregar.setText("Agregar");
                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{

                            if(!descripcion.getText().toString().equals("")){

                                gpo_emp = new GrupoEmparejamiento(descripcion.getText().toString());
                                dao.insertar(gpo_emp);
                                lista_gpo_emp = dao.verTodos();
                                dialog.dismiss();

                                //INICIO
                                
                                //FIN

                            }else{
                                Toast.makeText(v.getContext(), "¡Ingrese la descripción del grupo de emparejamiento!", Toast.LENGTH_SHORT).show();
                                descripcion.setFocusable(true);
                            }


                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(v.getContext(), AreaActivity.class);
                        startActivity(in);
                        dialog.dismiss();
                    }
                });
            }
        });

        //INICIO
        if(id_tipo_item==3){

            list.setAdapter(adaptador);
                /*if(accion == 0){

                    if(lista_gpo_emp.isEmpty()){
                        agregar.performClick();
                    }else{

                        Intent in = new Intent(this, PreguntaActivity.class);
                        gpo_emp = lista_gpo_emp.get(0);
                        in.putExtra("id_gpo_emp",gpo_emp.getId());
                        in.putExtra("desc_gpo_emp",gpo_emp.getDescripcion());
                        startActivity(in);
                    }

                }else{
                    Toast.makeText(this, "Tamanio: "+lista_gpo_emp.size(), Toast.LENGTH_SHORT).show();
                    adaptador = new Adaptador(lista_gpo_emp, this, dao);
                    ListView list = (ListView)findViewById(R.id.lista);
                    list.setAdapter(adaptador);
                }*/

        }else{
            Intent in = new Intent(this, PreguntaActivity.class);
            in.putExtra("id_gpo_emp",0);
            in.putExtra("id_tipo_item",id_tipo_item);
            in.putExtra("id_area",id_area);
            startActivity(in);
        }
        //FIN
    }

}
