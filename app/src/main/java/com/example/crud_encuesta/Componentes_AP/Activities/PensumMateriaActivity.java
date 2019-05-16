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
import com.example.crud_encuesta.Componentes_AP.Adapters.AdapterPensumMateria;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOPensum;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOPensumMateria;
import com.example.crud_encuesta.Componentes_AP.Models.Pensum;
import com.example.crud_encuesta.Componentes_AP.Models.PensumMateria;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class PensumMateriaActivity extends AppCompatActivity {

    DAOPensumMateria daoPensumMateria;
    AdapterPensumMateria adapterPensumMateria;
    ArrayList<PensumMateria> pensumMaterias;
    PensumMateria pensumMateria;
    int id_materia = 0;
    int id_pensum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pensum_materia);

        //recuperamos el id de la evaluación que nos mandan
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            id_pensum = bundle.getInt("id_pensum");
        }

        daoPensumMateria = new DAOPensumMateria(this);
        pensumMaterias = daoPensumMateria.verTodos(id_pensum);
        adapterPensumMateria = new AdapterPensumMateria(pensumMaterias,daoPensumMateria,this, this);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.ap_fab_agregar_pensumm);
        ImageView buscar = (ImageView) findViewById(R.id.ap_imgv_buscar_pensumm);
        ImageView all = (ImageView) findViewById(R.id.ap_imgv_all_pensumm);
        final EditText edt_buscar = (EditText) findViewById(R.id.ap_edt_buscar_pensumm);

        ListView listView = (ListView) findViewById(R.id.lista_pensum_materia);
        listView.setAdapter(adapterPensumMateria);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });

        //LISTENERS
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogo de crear

                final Dialog dialog = new Dialog(PensumMateriaActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//para quitar el titulo
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_pensum_materia);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.show();

                //enlazamos
                final TextView titulo = (TextView) dialog.findViewById(R.id.ap_tv_title_dialogo_pensum_materia);
                final Spinner materias = (Spinner) dialog.findViewById(R.id.spinner_materia_pensum);
                Button btCrear = (Button) dialog.findViewById(R.id.d_agregar_pensum_materia);
                Button btCancelar = (Button) dialog.findViewById(R.id.d_cancelar_pensum_materia);

                titulo.setText(getResources().getText(R.string.ap_nuevo));

                //llenamos el spinner
                ArrayAdapter<CharSequence> adaptadorSpinner = new ArrayAdapter(
                        v.getContext(),
                        android.R.layout.simple_spinner_item,
                        daoPensumMateria.SpinnerMaterias(id_pensum));
                materias.setAdapter(adaptadorSpinner);

                //seteamos la opcion del spiner
                materias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position > 0){
                            String[] materia = daoPensumMateria.SpinnerMaterias(id_pensum).get(position).split(" ");
                            id_materia = Integer.parseInt(materia[0]);
                        }else {
                            id_materia=0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id_materia != 0) {
                            try {
                                pensumMateria = new PensumMateria(
                                        id_materia,
                                        id_pensum

                                );
                                //creamos registro
                                daoPensumMateria.Insertar(pensumMateria);
                                //refrescamos la lista
                                pensumMaterias = daoPensumMateria.verTodos(id_pensum);
                                adapterPensumMateria.notifyDataSetChanged();
                                //cerramos el dialogo
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(
                                    v.getContext(),
                                    getResources().getText(R.string.ap_debes_seleccionar),
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

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_buscar.getText().toString().isEmpty()){
                    pensumMaterias = daoPensumMateria.verUno(edt_buscar.getText().toString(),id_pensum);
                    adapterPensumMateria.notifyDataSetChanged();
                }else{
                    Toast.makeText(
                            v.getContext(),
                            getResources().getText(R.string.ap_debes_ingresar),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pensumMaterias = daoPensumMateria.verTodos(id_pensum);
                adapterPensumMateria.notifyDataSetChanged();
            }
        });

    }
}
