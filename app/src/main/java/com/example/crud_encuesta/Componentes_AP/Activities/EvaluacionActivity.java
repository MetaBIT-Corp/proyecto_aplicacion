package com.example.crud_encuesta.Componentes_AP.Activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_AP.Adapters.AdapterEvaluacion;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOEvaluacion;
import com.example.crud_encuesta.Componentes_AP.Models.Evaluacion;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class EvaluacionActivity extends AppCompatActivity {

    DAOEvaluacion daoEvaluacion;
    AdapterEvaluacion adapterEvaluacion;
    ArrayList<Evaluacion> evaluaciones;
    Evaluacion evaluacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion);

        daoEvaluacion = new DAOEvaluacion(this);
        evaluaciones = daoEvaluacion.verTodos();
        adapterEvaluacion = new AdapterEvaluacion(evaluaciones,daoEvaluacion,this);

        ImageView agregar = (ImageView) findViewById(R.id.ap_imgv_agregar_evaluacion);
        ImageView buscar = (ImageView) findViewById(R.id.ap_imgv_buscar_evaluacion);
        ImageView all = (ImageView) findViewById(R.id.ap_imgv_all_evaluacion);
        final EditText edt_buscar = (EditText) findViewById(R.id.ap_edt_buscar_evaluacion);

        ListView listView = (ListView) findViewById(R.id.lista_evaluacion);
        listView.setAdapter(adapterEvaluacion);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Intent al siguiente CRUD de turnos
            }
        });
        //final de listener de listview

        //listener de agregar
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dialogo de crear

                final Dialog dialog = new Dialog(EvaluacionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//para quitar el titulo
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_evaluacion);
                dialog.show();

                //enlazamos edittext del dialogo
                final TextView titulo = (TextView) dialog.findViewById(R.id.ap_tv_title_dialogo_eva);
                final EditText nombre = (EditText) dialog.findViewById(R.id.ap_edt_nombre_eva);
                final EditText duracion = (EditText) dialog.findViewById(R.id.ap_edt_duracion_eva);
                final EditText intento = (EditText) dialog.findViewById(R.id.ap_edt_num_intento_eva);
                final EditText desc = (EditText) dialog.findViewById(R.id.ap_edt_desc_eva);
                final CheckBox retroceso = (CheckBox) dialog.findViewById(R.id.ap_cb_retroceder);

                Button btCrear = (Button) dialog.findViewById(R.id.d_agregar_eva);
                Button btCancelar = (Button) dialog.findViewById(R.id.d_cancelar_eva);

                btCrear.setText("Agregar");
                titulo.setText("Nuevo");

                //programamos botones de crear-guardar y cancelar

                btCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int retro=0;
                        if (retroceso.isChecked()){
                            retro=1;
                        }
                        if(!duracion.getText().toString().isEmpty()&&!intento.getText().toString().isEmpty()
                        &&!nombre.getText().toString().isEmpty()) {

                            try {
                                evaluacion = new Evaluacion(
                                        1,
                                        Integer.parseInt(duracion.getText().toString()),
                                        Integer.parseInt(intento.getText().toString()),
                                        nombre.getText().toString(),
                                        desc.getText().toString(),
                                        retro
                                );
                                //editamos registro
                                daoEvaluacion.Insertar(evaluacion);
                                //refrescamos la lista
                                evaluaciones = daoEvaluacion.verTodos();
                                //ejecutamos el metodo
                                adapterEvaluacion.notifyDataSetChanged();
                                //cerramos el dialogo
                                dialog.dismiss();

                            } catch (Exception e) {
                                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(
                                    v.getContext(),
                                    "Debes llenar los campos de nombre, intento y duración",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //final de agregar

                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //final de cancelar

            }
        });
        //final de listener de agregar

        //listener de buscar
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_buscar.getText().toString().isEmpty()){
                    evaluaciones = daoEvaluacion.verUno(edt_buscar.getText().toString());
                    adapterEvaluacion.notifyDataSetChanged();
                }else{
                    Toast.makeText(
                            v.getContext(),
                            "Debes ingresar un nombre de consulta",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        //final de listener de buscar


        //listener de all
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluaciones = daoEvaluacion.verTodos();
                adapterEvaluacion.notifyDataSetChanged();
            }
        });
        //final de listener de all
    }
}
