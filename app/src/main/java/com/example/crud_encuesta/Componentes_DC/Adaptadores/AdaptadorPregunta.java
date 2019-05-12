package com.example.crud_encuesta.Componentes_DC.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Dao.DaoPregunta;
import com.example.crud_encuesta.Componentes_DC.Activities.OpcionActivity;
import com.example.crud_encuesta.Componentes_DC.Objetos.Pregunta;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class AdaptadorPregunta extends BaseAdapter {

    private ArrayList<Pregunta> lista_preguntas = new ArrayList<>();
    private DaoPregunta dao;
    private Pregunta pregunta;
    private Activity a;
    private int id;
    private int id_tipo_item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorPregunta(ArrayList<Pregunta> lista_preguntas, Activity a, DaoPregunta dao,int id_tipo_item) {
        this.lista_preguntas = lista_preguntas;
        this.dao = dao;
        this.a = a;
        this.id_tipo_item = id_tipo_item;
    }


    @Override
    public int getCount() {
        return lista_preguntas.size();
    }

    @Override
    public Pregunta getItem(int position) {
        pregunta = lista_preguntas.get(position);
        return pregunta;
    }

    @Override
    public long getItemId(int position) {
        pregunta = lista_preguntas.get(position);
        return pregunta.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){
            LayoutInflater li = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_pregunta,null);
        }

        pregunta = lista_preguntas.get(position);

        TextView texto_pregunta = (TextView)v.findViewById(R.id.txt_pregunta);
        Button editar = (Button)v.findViewById(R.id.btn_editar);
        Button eliminar = (Button)v.findViewById(R.id.btn_eliminar);
        Button opcion = (Button)v.findViewById(R.id.btn_ag_opcion);

        texto_pregunta.setText(pregunta.getPregunta());
        editar.setTag(position);
        eliminar.setTag(position);
        opcion.setTag(position);

        opcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = Integer.parseInt(v.getTag().toString());
                pregunta = lista_preguntas.get(pos);
                Bundle b = new Bundle();
                b.putInt("id_pregunta",pregunta.getId());;
                b.putInt("id_tipo_item",id_tipo_item);
                Intent i = new Intent(a, OpcionActivity.class);
                i.putExtras(b);
                a.startActivity(i);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int pos = Integer.parseInt(v.getTag().toString());
                final Dialog dialog = new Dialog(a);
                dialog.setTitle("Editar Pregunta");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_pregunta);
                dialog.show();

                final EditText texto_pregunta = (EditText)dialog.findViewById(R.id.editt_pregunta);
                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);
                TextView texto_titulo = (TextView)dialog.findViewById(R.id.texto_titulo);
                texto_titulo.setText("Editar pregunta");
                agregar.setText("Guardar");
                pregunta = lista_preguntas.get(pos);
                setId(pregunta.getId());
                final int id_grupo_emp = pregunta.getId_grupo_emp();
                texto_pregunta.setText(pregunta.getPregunta());

                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{

                            if(!texto_pregunta.getText().toString().equals("")){

                                pregunta = new Pregunta(getId(),id_grupo_emp, texto_pregunta.getText().toString());
                                dao.editar(pregunta);
                                notifyDataSetChanged();
                                lista_preguntas = dao.verTodos();
                                dialog.dismiss();

                            }else{
                                Toast.makeText(v.getContext(), "¡No se permite dejar vacio el texto de pregunta!", Toast.LENGTH_SHORT).show();
                                texto_pregunta.setFocusable(true);
                            }

                        }catch (Exception e){
                            Toast.makeText(a, "Error", Toast.LENGTH_SHORT);
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

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = Integer.parseInt(v.getTag().toString());
                pregunta = lista_preguntas.get(pos);
                setId(pregunta.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("Esta seguro de eliminar la pregunta?");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista_preguntas = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });

                del.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();
            }
        });


        return v;
    }

    public void dataChange(ArrayList<Pregunta> lista_preguntas){
        this.lista_preguntas = lista_preguntas;
        notifyDataSetChanged();
    }
}
