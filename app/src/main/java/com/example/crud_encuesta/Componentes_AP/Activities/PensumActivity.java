package com.example.crud_encuesta.Componentes_AP.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_AP.Adapters.AdapterPensum;
import com.example.crud_encuesta.Componentes_AP.Adapters.AdapterTurno;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOPensum;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOTurno;
import com.example.crud_encuesta.Componentes_AP.Models.Pensum;
import com.example.crud_encuesta.Componentes_AP.Models.PensumMateria;
import com.example.crud_encuesta.Componentes_AP.Models.Turno;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class PensumActivity extends AppCompatActivity {

    DAOPensum daoPensum;
    AdapterPensum adapterPensum;
    ArrayList<Pensum> pensums;
    Pensum pensum;
    int id_carrera = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pensum);

        daoPensum = new DAOPensum(this);
        pensums = daoPensum.verTodos();
        adapterPensum = new AdapterPensum(pensums,daoPensum,this, this);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.ap_fab_agregar_pensum);
        ImageView buscar = (ImageView) findViewById(R.id.ap_imgv_buscar_pensum);
        ImageView all = (ImageView) findViewById(R.id.ap_imgv_all_pensum);
        final EditText edt_buscar = (EditText) findViewById(R.id.ap_edt_buscar_pensum);

        ListView listView = (ListView) findViewById(R.id.lista_pensum);
        listView.setAdapter(adapterPensum);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), PensumMateriaActivity.class);
                //enviamos parametro
                Bundle bundle = new Bundle();
                bundle.putInt("id_pensum",pensums.get(position).getIdPensum());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //LISTENERS

        //INICIO de listeners ADD

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogo de crear

                final Dialog dialog = new Dialog(PensumActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//para quitar el titulo
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_pensum);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.show();

                //enlazamos
                final TextView titulo = (TextView) dialog.findViewById(R.id.ap_tv_title_dialogo_pensum);
                final EditText anio = (EditText) dialog.findViewById(R.id.ap_edt_anio_pensum);
                final Spinner carreras = (Spinner) dialog.findViewById(R.id.spinner_carrera_pensum);
                Button btCrear = (Button) dialog.findViewById(R.id.d_agregar_pensum);
                Button btCancelar = (Button) dialog.findViewById(R.id.d_cancelar_pensum);

                titulo.setText(getResources().getText(R.string.ap_nuevo));

                //llenamos el spinner
                ArrayAdapter<CharSequence> adaptadorSpinner = new ArrayAdapter(
                        v.getContext(),
                        android.R.layout.simple_spinner_item,
                        daoPensum.SpinnerCarreras());
                carreras.setAdapter(adaptadorSpinner);

                //seteamos la opcion del spiner
                carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position > 0){
                            String[] carrera = daoPensum.SpinnerCarreras().get(position).split(" ");
                            id_carrera = Integer.parseInt(carrera[0]);
                        }else {
                            id_carrera=0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!anio.getText().toString().isEmpty() && id_carrera != 0) {
                            try {
                                pensum = new Pensum(
                                        id_carrera,
                                        Integer.parseInt(anio.getText().toString())

                                );
                                //creamos registro
                                daoPensum.Insertar(pensum);
                                //refrescamos la lista
                                pensums = daoPensum.verTodos();
                                adapterPensum.notifyDataSetChanged();
                                //cerramos el dialogo
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(getApplication(), getResources().getText(R.string.ap_error), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(
                                    v.getContext(),
                                    getResources().getText(R.string.ap_llena_todos_los_campos),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        //FINAL de LIsteners ADD

        //INICIO de listeners buscar
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_buscar.getText().toString().isEmpty()){
                    pensums = daoPensum.verUno(edt_buscar.getText().toString());
                    adapterPensum.notifyDataSetChanged();
                }else{
                    Toast.makeText(
                            v.getContext(),
                            getResources().getText(R.string.ap_debes_ingresar),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        //FINAL de LIsteners buscar

        //INICIO de listeners all
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pensums = daoPensum.verTodos();
                adapterPensum.notifyDataSetChanged();
            }
        });
        //FINAL de LIsteners all

    }
}
