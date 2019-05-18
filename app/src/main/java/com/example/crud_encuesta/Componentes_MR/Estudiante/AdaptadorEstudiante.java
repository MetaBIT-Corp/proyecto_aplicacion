package com.example.crud_encuesta.Componentes_MR.Estudiante;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.Componentes_AP.Models.Usuario;
import com.example.crud_encuesta.Componentes_MR.Funciones;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;
import java.util.ArrayList;

public class AdaptadorEstudiante extends BaseAdapter {

    private Activity a;
    private ArrayList<Estudiante> lista;
    private DAOEstudiante dao;
    private Estudiante estudiante;
    private Usuario usuario;
    private int id = 0;
    private SQLiteDatabase db;
    private DatabaseAccess access;

    public AdaptadorEstudiante(Activity a, ArrayList<Estudiante> lista, DAOEstudiante dao) {
        this.a = a;
        this.lista = lista;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Estudiante getItem(int position) {
        estudiante = lista.get(position);
        return estudiante;
    }

    @Override
    public long getItemId(int position) {
        estudiante = lista.get(position);
        return estudiante.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v==null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_estudiante,null);
        }

        TextView nombre = (TextView) v.findViewById(R.id.txt_nombre_estd);
        TextView carnet = (TextView) v.findViewById(R.id.txt_carnet_estd);

        Button ver = (Button) v.findViewById(R.id.btn_ver_estd);
        Button editar = (Button) v.findViewById(R.id.btn_editar_estd);
        Button eliminar = (Button) v.findViewById(R.id.btn_eliminar_estd);

        estudiante = lista.get(position);/*Obteniendo Objeto de Lista*/

        nombre.setText(estudiante.getNombre());
        carnet.setText(estudiante.getCarnet());

        ver.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        access = DatabaseAccess.getInstance(v.getContext());
        db = access.open();

        /*Botón de Información*/
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());

                /*Declarando Dialogo de Información de Estudiante*/
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Ver Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.vista_estudiante);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                /*Llamando y declarando las Vistas*/
                    /*TextView, EditText, Spinners, etc...*/
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_std);
                final TextView mensaje2 = (TextView) dialogo.findViewById(R.id.toolbar2);
                final TextView carnet = (TextView) dialogo.findViewById(R.id.tv_carnet_estd);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad_estd);
                final TextView nombre = (TextView) dialogo.findViewById(R.id.tv_nombre_estd);
                final TextView anio_ingreso = (TextView) dialogo.findViewById(R.id.tv_anio_ingreso_estd);

                Button salir = (Button) dialogo.findViewById(R.id.btn_salir);

                estudiante = lista.get(pos); /*Obteniendo Objeto de Lista.*/

                mensaje.setText(R.string.est_titulo3);
                mensaje2.setText(estudiante.getCarnet().toUpperCase());

                /*Seteando valores a Vistas a partir de Objeto.*/
                carnet.setText(estudiante.getCarnet());
                    /*CheckBox de Actividad Estudiante.*/
                if(estudiante.getActivo()==1){
                    activo.setChecked(true);
                }else{
                    activo.setChecked(false);
                }
                nombre.setText(estudiante.getNombre());
                anio_ingreso.setText(estudiante.getAnio_ingreso());

                /*Botón Salir, Cierra dialogo de Información.*/
                salir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });

        /*Botón de Edición de Estudiante*/
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());

                /*Declaración de Dialogo para Edición de Estudiante*/
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Edición de Estudiante");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_estudiante);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                /*Llamando y declarando las Vistas.*/
                    /*TextView, EditText, Spinners, etc.*/
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_std);
                final TextView mensaje2 = (TextView) dialogo.findViewById(R.id.toolbar2);
                final EditText carnet = (EditText) dialogo.findViewById(R.id.editt_carnet_estd);
                final EditText nombre = (EditText) dialogo.findViewById(R.id.editt_nombre_estd);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad_estd);
                final EditText anio_ingreso = (EditText) dialogo.findViewById(R.id.editt_anio_ingreso_estd);

                    /*Buttons.*/
                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar =(Button) dialogo.findViewById(R.id.btn_agregar_estd);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_estd);
                guardar.setText(R.string.btn_guardar);

                estudiante = lista.get(pos);/*Obteniendo Objeto desde Lista.*/

                final int id_user=estudiante.getId_usuario();/*Obteniedo Id_user de Estudiante Objeto*/

                mensaje.setText(R.string.est_titulo_editar);
                mensaje2.setText(estudiante.getCarnet().toUpperCase());


                    /*Seteando valores a Vistas a partir de Objeto*/
                setId(estudiante.getId());
                carnet.setText(estudiante.getCarnet());
                nombre.setText(estudiante.getNombre());

                    /*CheckBox de Actividad*/
                if(estudiante.getActivo()==1){
                    activo.setChecked(true);
                }else{
                    activo.setChecked(false);
                }

                    /*Seteando Valor de Año*/
                anio_ingreso.setText(estudiante.getAnio_ingreso());
                Funciones.setBtnAnio(dialogo,btn_anio,anio_ingreso);

                /*Guardado de Objeto Editado (Creacion de Objeto y envio a DAO).*/
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String errores="";

                        errores += Funciones.comprobarCampo(carnet,"Carnet",a);
                        errores += Funciones.comprobarCampo(nombre,"Nombre",a);
                        errores += Funciones.comprobarCampo(anio_ingreso,"Año de Ingreso",a);
                        errores += Funciones.comprobarAnio(anio_ingreso,a);

                        if(errores.isEmpty()){

                            int check;
                            if(activo.isChecked()){
                                check = 1;
                            }else{
                                check = 0;
                            }

                            estudiante = new Estudiante(
                                    getId(),
                                    carnet.getText().toString(),
                                    nombre.getText().toString(),
                                    check,
                                    anio_ingreso.getText().toString(),
                                    id_user);

                            usuario = new Usuario(
                                    id_user,
                                    estudiante.getCarnet(),
                                    estudiante.getCarnet(),
                                    2);

                            dao.editar(estudiante);
                            dao.editarUsuario(usuario);

                            notifyDataSetChanged();
                            lista = dao.verTodos();
                            dialogo.dismiss();
                        }else{
                            Toast.makeText(a, errores+"\n"+a.getResources().getString(R.string.rellene_v), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                /*Botón Cancelar Edición de Estudiante*/
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });

        /*Botón de Eliminación*/
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());

                estudiante = lista.get(pos); /*Obteniendo Objeto de Lista.*/
                setId(estudiante.getId());

                /*Declarando Alert Dialog para Eliminación*/
                final AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage(R.string.est_borrar);
                del.setCancelable(true);

                /*Botón de Si*/
                del.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });

                /*Botón de No*/
                del.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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