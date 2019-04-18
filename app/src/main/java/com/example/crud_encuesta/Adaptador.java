package com.example.crud_encuesta;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        EditText area = (EditText)v.findViewById(R.id.txt_area);
        EditText descripcion = (EditText)v.findViewById(R.id.txt_descripcion);
        Button editar = (Button)v.findViewById(R.id.btn_editar);
        Button eliminar = (Button)v.findViewById(R.id.btn_eliminar);

        area.setText(""+gpo_emp.getId_area());
        descripcion.setText(gpo_emp.getDescripcion());
        editar.setTag(position);
        eliminar.setTag(position);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int pos = Integer.parseInt(v.getTag().toString());
                final Dialog dialog = new Dialog(a);
                dialog.setTitle("Editar Grupo Emparejamiento");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_gpo_emp);
                dialog.show();

                final EditText area = (EditText)dialog.findViewById(R.id.area);
                final EditText descripcion = (EditText)dialog.findViewById(R.id.descripcion);
                Button agregar = (Button)dialog.findViewById(R.id.btn_agregar);
                Button cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);

                agregar.setText("Guardar");
                gpo_emp = lista_gpo_emp.get(pos);
                Log.d("REAL",""+gpo_emp.getId());
                setId(gpo_emp.getId());
                area.setText(""+gpo_emp.getId_area());
                descripcion.setText(gpo_emp.getDescripcion());

                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{

                            gpo_emp = new GrupoEmparejamiento(getId(),Integer.parseInt(area.getText().toString()), descripcion.getText().toString());
                            dao.editar(gpo_emp);
                            notifyDataSetChanged();
                            lista_gpo_emp = dao.verTodos();
                            dialog.dismiss();

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

            }
        });


        return v;
    }
}
