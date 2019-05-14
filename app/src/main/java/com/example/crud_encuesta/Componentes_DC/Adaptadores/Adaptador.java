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

import com.example.crud_encuesta.Componentes_DC.Dao.DaoGrupoEmp;
import com.example.crud_encuesta.Componentes_DC.Objetos.GrupoEmparejamiento;
import com.example.crud_encuesta.Componentes_DC.Activities.PreguntaActivity;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private ArrayList<GrupoEmparejamiento> lista_gpo_emp = new ArrayList<>();
    DaoGrupoEmp dao;
    GrupoEmparejamiento gpo_emp;
    Activity a;

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Adaptador(ArrayList<GrupoEmparejamiento> lista_gpo_emp, Activity a, DaoGrupoEmp dao) {
        this.lista_gpo_emp = lista_gpo_emp;
        this.dao = dao;
        this.a = a;
    }

    @Override
    public int getCount() {
        return lista_gpo_emp.size();
    }

    @Override
    public GrupoEmparejamiento getItem(int position) {
        gpo_emp = lista_gpo_emp.get(position);
        return gpo_emp;
    }

    @Override
    public long getItemId(int position) {
        gpo_emp = lista_gpo_emp.get(position);
        return gpo_emp.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){
            LayoutInflater li = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_gpo_emp,null);
        }

        gpo_emp = lista_gpo_emp.get(position);

        TextView descripcion = (TextView)v.findViewById(R.id.txt_descripcion);
        Button editar = (Button)v.findViewById(R.id.btn_editar);
        Button eliminar = (Button)v.findViewById(R.id.btn_eliminar);
        Button pregunta = (Button)v.findViewById(R.id.btn_ag_preg);

        descripcion.setText(gpo_emp.getDescripcion());
        pregunta.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        pregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int pos = Integer.parseInt(v.getTag().toString());
                gpo_emp = lista_gpo_emp.get(pos);
                Bundle b = new Bundle();
                b.putInt("id_gpo_emp",gpo_emp.getId());

                Intent i = new Intent(a, PreguntaActivity.class);
                i.putExtras(b);
                a.startActivity(i);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int pos = Integer.parseInt(v.getTag().toString());
                final Dialog dialog = new Dialog(a);
                dialog.setTitle("Editar Grupo Emparejamiento");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_gpo_emp);
                dialog.show();

                final EditText descripcion = (EditText)dialog.findViewById(R.id.descripcion);
                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);
                TextView texto_titulo = (TextView)dialog.findViewById(R.id.texto_titulo);
                texto_titulo.setText("Editar grupo emparejamiento");
                agregar.setText("Guardar");
                gpo_emp = lista_gpo_emp.get(pos);
                final int id_area = gpo_emp.getId_area();
                setId(gpo_emp.getId());
                descripcion.setText(gpo_emp.getDescripcion());

                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if(!descripcion.getText().toString().equals("")){

                                gpo_emp = new GrupoEmparejamiento(getId(),id_area, descripcion.getText().toString());
                                dao.editar(gpo_emp);
                                notifyDataSetChanged();
                                lista_gpo_emp = dao.verTodos();
                                dialog.dismiss();

                            }else{
                                Toast.makeText(v.getContext(), "¡No se permite dejar vacia la descripción del grupo de emparejamiento!", Toast.LENGTH_SHORT).show();
                                descripcion.setFocusable(true);
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
                gpo_emp = lista_gpo_emp.get(pos);
                setId(gpo_emp.getId());
                int cant = dao.cantidad_eliminar_pregunta(getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Esta seguro de eliminar el grupo de emparejamiento?, se eliminaran "+cant+" preguntas en caso de aceptar!!");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista_gpo_emp = dao.verTodos();
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
}
