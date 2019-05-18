package com.example.crud_encuesta.Componentes_MR.MateriaCiclo;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.Componentes_EL.Materia.Materia;
import com.example.crud_encuesta.Componentes_MR.Funciones;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;
import java.util.ArrayList;

public class ActivityMateriaCiclo extends AppCompatActivity {

    private SQLiteDatabase db;
    private DatabaseAccess access;
    private ArrayList<MateriaCiclo> lista;
    private ArrayList<Materia> materias = new ArrayList<>();
    private DAOMateriaCiclo dao;
    private AdaptadorMateriaCiclo adapter;
    private int id_materia;
    private MateriaCiclo materiaCiclo;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_ciclo);

        dao = new DAOMateriaCiclo(this);
        lista = dao.verTodos();
        adapter = new AdaptadorMateriaCiclo(this,lista,dao);
        access = DatabaseAccess.getInstance(ActivityMateriaCiclo.this);
        db = access.open();

        materias = DAOMateriaCiclo.materias(db); /*Lista con Objetos Materia desde DB*/

        ListView list = (ListView) findViewById(R.id.lista_mat_ci);
        FloatingActionButton agregar = (FloatingActionButton) findViewById(R.id.btn_agregar);

        if((lista != null) && (lista.size() > 0)){
            list.setAdapter(adapter);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
        });

        /*Botón Agregar nueva MateriaCiclo*/
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Declarando Dialogo para Ingreso de Nueva Materia Ciclo*/
                final Dialog dialogo = new Dialog(ActivityMateriaCiclo.this);
                dialogo.setTitle("Registro de Materia Ciclo");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_materia_ciclo);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                /*Llamando y declarando las Vistas*/
                    /*TextView, EditText, Spinners, etc...*/
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_mat_cit);
                final Spinner sp_materia = (Spinner) dialogo.findViewById(R.id.sp_materia);
                final RadioButton rb1 = (RadioButton) dialogo.findViewById(R.id.rb_ciclo1);
                final RadioButton rb2 = (RadioButton) dialogo.findViewById(R.id.rb_ciclo2);
                final EditText anio = (EditText) dialogo.findViewById(R.id.editt_anio);

                    /*Buttons*/
                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar = (Button) dialogo.findViewById(R.id.btn_agregar);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar);

                mensaje.setText(R.string.mtc_titulo_registrar);
                guardar.setText(R.string.btn_registrar);

                /*Seteando Valores en Vistas*/
                    /*Obteniendo Valores de Spinner de Materias*/
                ArrayAdapter materiaAdapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,DAOMateriaCiclo.listaMaterias(materias));
                sp_materia.setAdapter(materiaAdapter);
                sp_materia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position !=0){
                            id_materia = materias.get(position).getId();
                        }else{
                            id_materia = 1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                    /*Fin de Spinner Materias*/

                Funciones.setBtnAnio(dialogo,btn_anio,anio); /*Seteando valor a vista de Año MateriaCiclo*/

                /*Guardado de Objeto MateriaCiclo(Creación de Objeto y envio a DAO)*/
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String errores="";
                        errores += Funciones.comprobarCampo(anio,"Año",ActivityMateriaCiclo.this);
                        errores += Funciones.comprobarAnio(anio,ActivityMateriaCiclo.this);

                        if (errores.isEmpty()){
                            int ciclo_seleccionado;
                            if(rb1.isChecked()){
                                ciclo_seleccionado =1;
                            }else {
                                ciclo_seleccionado = 2;
                            }
                            materiaCiclo = new MateriaCiclo(
                                    id_materia,
                                    ciclo_seleccionado,
                                    anio.getText().toString());
                            dao.insertar(materiaCiclo);
                            adapter.notifyDataSetChanged();
                            lista = dao.verTodos();
                            dialogo.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(),errores+"\n"+getResources().getString(R.string.rellene_v), Toast.LENGTH_LONG).show();}
                    }
                });

                /*Cancelación de Creación de Nueva MateriaCiclo*/
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });
    }
}