package com.example.crud_encuesta.Componentes_DC.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_DC.Activities.OpcionActivity;
import com.example.crud_encuesta.Componentes_DC.Dao.DaoOpcion;
import com.example.crud_encuesta.Componentes_DC.Objetos.Opcion;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class AdaptadorOpcion extends BaseAdapter {

    private ArrayList<Opcion> lista_opciones = new ArrayList<>();
    private DaoOpcion dao;
    private Opcion opcion;
    private Activity a;
    private int id;
    private int es_verdadero_falso;
    private int es_respuesta_corta;
    private int id_tipo_item;

    public AdaptadorOpcion(ArrayList<Opcion> lista_opciones, Activity a, DaoOpcion dao, int es_verdadero_falso, int es_respuesta_corta, int id_tipo_item) {
        this.lista_opciones = lista_opciones;
        this.dao = dao;
        this.id_tipo_item = id_tipo_item;
        this.a = a;
        this.es_verdadero_falso = es_verdadero_falso;
        this.es_respuesta_corta = es_respuesta_corta;
    }
    @Override
    public int getCount() {
        return lista_opciones.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Opcion getItem(int position) {
        opcion = lista_opciones.get(position);
        return opcion;
    }

    @Override
    public long getItemId(int position) {
        opcion = lista_opciones.get(position);
        return opcion.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){
            LayoutInflater li = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_opcion,null);
        }
        opcion = lista_opciones.get(position);

        TextView texto_opcion = (TextView)v.findViewById(R.id.txt_opcion);
        CheckBox cb_correcta = (CheckBox)v.findViewById(R.id.cb_correcta);
        Button editar = (Button)v.findViewById(R.id.btn_editar);
        Button eliminar = (Button)v.findViewById(R.id.btn_eliminar);

        if(id_tipo_item==4)eliminar.setVisibility(View.GONE);

        texto_opcion.setText(opcion.getOpcion());

        if(opcion.getCorrecta() == 1){
            cb_correcta.setChecked(true);
        }else{
            cb_correcta.setChecked(false);
        }
        if(es_verdadero_falso==1){
            editar.setVisibility(View.GONE);
            eliminar.setVisibility(View.GONE);
        }else{
            editar.setTag(position);
            eliminar.setTag(position);

            final int es_resp_corta_final=es_respuesta_corta;

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int pos = Integer.parseInt(v.getTag().toString());
                    final Dialog dialog = new Dialog(a);
                    dialog.setTitle("Editar Opcion");
                    dialog.setCancelable(true);

                    if(es_resp_corta_final!=1) dialog.setContentView(R.layout.dialogo_opcion);
                    else dialog.setContentView(R.layout.dialogo_opcion_resp_corta);

                    dialog.show();

                    final EditText texto_opcion = (EditText)dialog.findViewById(R.id.editt_opcion);
                    final CheckBox cb_correcta = (CheckBox)dialog.findViewById(R.id.cb_correcta);
                    Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                    Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);
                    TextView texto_titulo = (TextView)dialog.findViewById(R.id.texto_titulo);
                    texto_titulo.setText("Editar opción");
                    agregar.setText("Guardar");
                    opcion = lista_opciones.get(pos);
                    setId(opcion.getId());
                    final int id_pregunta = opcion.getId_pregunta();
                    texto_opcion.setText(opcion.getOpcion());

                    try{
                        if(opcion.getCorrecta() == 1){
                            cb_correcta.setChecked(true);
                        }else{
                            cb_correcta.setChecked(false);
                        }
                    }catch (Exception e){

                    }

                    agregar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{

                                int check = 0;

                                try{
                                    if(cb_correcta.isChecked())check = 1;
                                }catch (Exception e){

                                }
                                if(!texto_opcion.getText().toString().equals("")){

                                    opcion = new Opcion(getId(),id_pregunta,texto_opcion.getText().toString(),check);
                                    dao.editar(opcion);
                                    notifyDataSetChanged();
                                    lista_opciones = dao.verTodos();
                                    dialog.dismiss();

                                }else{
                                    Toast.makeText(v.getContext(), "¡No se permite dejar vacio el texto de opción!", Toast.LENGTH_SHORT).show();
                                    texto_opcion.setFocusable(true);
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
                    opcion = lista_opciones.get(pos);
                    setId(opcion.getId());
                    AlertDialog.Builder del = new AlertDialog.Builder(a);
                    del.setMessage("¿Esta seguro de eliminar la opcion?");
                    del.setCancelable(false);
                    del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.eliminar(getId());
                            lista_opciones = dao.verTodos();
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
        }




        return v;
    }
}
