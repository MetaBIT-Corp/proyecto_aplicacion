package com.example.crud_encuesta.Componentes_MR.Docente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.Componentes_AP.Models.Usuario;
import com.example.crud_encuesta.Componentes_EL.Escuela.Escuela;
import com.example.crud_encuesta.Componentes_EL.Operaciones_CRUD;
import com.example.crud_encuesta.Componentes_MR.Funciones;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;
import java.util.ArrayList;

public class AdaptadorDocente extends BaseAdapter {

    private Activity a;
    private ArrayList<Docente> lista;
    private DAODocente dao;
    private Docente docente;
    private Usuario usuario;
    private int id = 0;
    private int id_escuela = 0;
    private SQLiteDatabase db;
    private DatabaseAccess access;
    private ArrayList<Escuela> escuelas = new ArrayList<>();
    private ArrayList<String> listaEscuelas = new ArrayList<>();

    public AdaptadorDocente(Activity a, ArrayList<Docente> lista, DAODocente dao){
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
    public Docente getItem(int position) {
        docente = lista.get(position);
        return docente;
    }

    @Override
    public long getItemId(int position) {
        docente = lista.get(position);
        return docente.getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_docente, null);
        }

        TextView nombre = (TextView) v.findViewById(R.id.txt_nombre);
        TextView carnet = (TextView) v.findViewById(R.id.txt_carnet);

        Button ver = (Button) v.findViewById(R.id.btn_ver_dcn);
        Button editar = (Button) v.findViewById(R.id.btn_editar_dcn);
        Button eliminar = (Button) v.findViewById(R.id.btn_eliminar_dcn);

        docente = lista.get(position); /*Obteniendo cada Objeto de Lista.*/

        nombre.setText(docente.getNombre());
        carnet.setText(docente.getCarnet());

        ver.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        access = DatabaseAccess.getInstance(v.getContext());
        db=access.open();

        escuelas= Operaciones_CRUD.todosEscuela("ESCUELA",db); /*Lista con Objetos Escuelas en DB*/
        listaEscuelas = Funciones.obtenerListaEscuelas(escuelas); /*Lista con los Strings de escuelas*/

        /*Botón de Información de Docente*/
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());
                String e = "";

                /*Declaración de Dialogo de Información de Docente.*/
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Vista de Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.vista_docente);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                /*Llamando y declarando las Vistas*/
                    /*TextView, EditText, Spinners, etc...*/
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_docente);
                final TextView mensaje2 = (TextView) dialogo.findViewById(R.id.toolbar2);
                final TextView escuela = (TextView) dialogo.findViewById(R.id.tv_escuela);
                final TextView carnet = (TextView) dialogo.findViewById(R.id.tv_carnet);
                final TextView anio_titulo = (TextView) dialogo.findViewById(R.id.tv_anio_titulo);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad);
                final TextView tipo_jornada = (TextView) dialogo.findViewById(R.id.tv_tipo_jornada);
                final TextView descripcion = (TextView) dialogo.findViewById(R.id.tv_descripcion);
                final TextView cargo_actual = (TextView) dialogo.findViewById(R.id.tv_cargo_actual);
                final TextView cargo_secundario = (TextView) dialogo.findViewById(R.id.tv_cargo_secundario);
                final TextView nombre = (TextView) dialogo.findViewById(R.id.tv_nombre);

                Button salir = (Button) dialogo.findViewById(R.id.btn_salir);

                docente = lista.get(pos);/*Obteniendo Objeto de Lista.*/

                mensaje.setText(R.string.est_titulo3);
                mensaje2.setText(docente.getCarnet().toUpperCase());

                /*Seteando valores a Vistas a partir de Objeto.*/
                    /*Seteo de Escuela a partir de ObjetO*/
                for(int i=0;i<escuelas.size();i++){
                    if(docente.getId_escuela()==escuelas.get(i).getId()){
                        e = escuelas.get(i).getNombre();
                    }
                }
                escuela.setText(""+e);
                    /*Fin de Escuela*/

                /*CheckBox de Actividad de Docente*/
                if(docente.getActivo()==1){
                    activo.setChecked(true);
                }
                else{
                    activo.setChecked(false);
                }
                /*Fin de CheckBox Actividad*/

                carnet.setText(docente.getCarnet());
                anio_titulo.setText(docente.getAnio_titulo());
                tipo_jornada.setText(""+docente.getTipo_jornada());
                descripcion.setText(""+docente.getDescripcion());
                cargo_actual.setText(""+docente.getCargo_actual());
                cargo_secundario.setText(""+docente.getCargo_secundario());
                nombre.setText(docente.getNombre());

                /*Cerrar Dialogo de Información.*/
                salir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());

                /*Declaración de Dialogo Edición de Docente*/
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Edición de Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_docente);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                /*Llamando y declarando las Vistas*/
                    /*TextView, EditText, Spinners, etc...*/
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_docente);
                final TextView mensaje2 = (TextView) dialogo.findViewById(R.id.toolbar2);
                final Spinner sp_escuela = (Spinner) dialogo.findViewById(R.id.sp_escuela);
                final EditText carnet = (EditText) dialogo.findViewById(R.id.editt_carnet);
                final EditText anio_titulo = (EditText) dialogo.findViewById(R.id.editt_anio_titulo);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad);
                final EditText tipo_jornada = (EditText) dialogo.findViewById(R.id.editt_tipo_jornada);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.editt_descripcion);
                final EditText cargo_actual = (EditText) dialogo.findViewById(R.id.editt_cargo_actual);
                final EditText cargo_secundario = (EditText) dialogo.findViewById(R.id.editt_cargo_secundario);
                final EditText nombre = (EditText) dialogo.findViewById(R.id.editt_nombre);

                    /*Botones.*/
                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar =(Button) dialogo.findViewById(R.id.btn_agregar_dcn);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_dcn);
                guardar.setText(R.string.btn_guardar);

                docente = lista.get(pos);/*Obteniendo Objeto desde Lista.*/

                final int id_user = docente.getId_usuario(); /*Obteniedo Id_user de Docente Objeto*/

                mensaje.setText(R.string.dcn_titulo_editar);
                mensaje2.setText(docente.getCarnet().toUpperCase());

                /*Seteando valores a Vistas a partir de Objeto*/

                setId(docente.getId());

                    /*Seteando Escuela desde Spinner*/
                ArrayAdapter escuelaAdapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1, listaEscuelas);
                sp_escuela.setAdapter(escuelaAdapter);
                for (int i = 0; i < listaEscuelas.size(); i++) {
                    if (docente.getId_escuela() == escuelas.get(i).getId()){
                        sp_escuela.setSelection(i);
                    }
                }
                sp_escuela.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0){
                            id_escuela = escuelas.get(position).getId();
                        } else {
                            id_escuela = 1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                carnet.setText(docente.getCarnet());

                    /*Seteando Valor de Año*/
                anio_titulo.setText(""+docente.getAnio_titulo());
                Funciones.setBtnAnio(dialogo,btn_anio,anio_titulo);

                    /*Seteando CheckBox Activo*/
                if(docente.getActivo()==1){
                    activo.setChecked(true);
                }else{
                    activo.setChecked(false);
                }

                tipo_jornada.setText(""+docente.getTipo_jornada());
                descripcion.setText(docente.getDescripcion());
                cargo_actual.setText(""+docente.getCargo_actual());
                cargo_secundario.setText(""+docente.getCargo_secundario());
                nombre.setText(docente.getNombre());

                /*Guardado de Objeto Editado (Creacion de Objeto y envio a DAO).*/
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String errores="";
                        errores += Funciones.comprobarCampo(carnet,"Carnet", a);
                        errores += Funciones.comprobarCampo(anio_titulo,"Año de Título", a);
                        errores += Funciones.comprobarAnio(anio_titulo, a);
                        errores += Funciones.comprobarCampo(tipo_jornada,"Tipo de Jornada", a);
                        errores += Funciones.comprobarCampo(cargo_actual,"Cargo Actual", a);
                        errores += Funciones.comprobarCampo(cargo_secundario,"Cargo Secundario", a);
                        errores += Funciones.comprobarCampo(nombre,"Nombre", a);

                        if(errores.isEmpty()) {

                            int check;
                            if(activo.isChecked()){
                                check = 1;
                            }else{
                                check = 0;
                            }

                            docente = new Docente(
                                    getId(),
                                    id_escuela,
                                    carnet.getText().toString(),
                                    anio_titulo.getText().toString(),
                                    check,
                                    Integer.parseInt(tipo_jornada.getText().toString()),
                                    descripcion.getText().toString(),
                                    Integer.parseInt(cargo_actual.getText().toString()),
                                    Integer.parseInt(cargo_secundario.getText().toString()),
                                    nombre.getText().toString(),
                                    id_user);

                            usuario = new Usuario(
                                    id_user,
                                    docente.getCarnet(),
                                    docente.getCarnet(),
                                    1
                            );

                            dao.editar(docente);
                            dao.editarUsuario(usuario);

                            notifyDataSetChanged();
                            lista = dao.verTodos();
                            dialogo.dismiss();
                        } else {
                            Toast.makeText(a, errores+"\n"+a.getResources().getString(R.string.rellene_v), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                /*Botón Cancelar Edición de Docente*/
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });

            }
        });


        /*Boton de Eliminación*/
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());

                docente = lista.get(pos); /*Obteniendo Objeto de Lista.*/
                setId(docente.getId());

                /*Declarando Alert Dialog para Eliminación*/
                final AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage(R.string.dcn_borrar);
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
