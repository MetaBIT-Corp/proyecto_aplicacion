package com.example.crud_encuesta.Componentes_Docente;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class ActivityDocente extends AppCompatActivity {

    private DAODocente dao;
    private AdaptadorDocente adapter;
    private ArrayList<Docente> lista;
    private Docente dc;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        dao = new DAODocente(this);
        lista = dao.verTodos();
        adapter = new AdaptadorDocente(this,lista,dao);
        ListView list = (ListView) findViewById(R.id.lista_docente);
        Button agregar = (Button) findViewById(R.id.btn_nuevo_docente);

        if((lista != null) && (lista.size() > 0)){
            list.setAdapter(adapter);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo =new Dialog(ActivityDocente.this);
                dialogo.setTitle("Registro de Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_docente);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final EditText id_escuela = (EditText) dialogo.findViewById(R.id.editt_id_escuela);
                final EditText carnet = (EditText) dialogo.findViewById(R.id.editt_carnet);
                final EditText anio_titulo = (EditText) dialogo.findViewById(R.id.editt_anio_titulo);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad);
                final EditText tipo_jornada = (EditText) dialogo.findViewById(R.id.editt_tipo_jornada);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.editt_descripcion);
                final EditText cargo_actual = (EditText) dialogo.findViewById(R.id.editt_cargo_actual);
                final EditText cargo_secundario = (EditText) dialogo.findViewById(R.id.editt_cargo_secundario);
                final EditText nombre = (EditText) dialogo.findViewById(R.id.editt_nombre);

                Button guardar =(Button) dialogo.findViewById(R.id.btn_agregar_dcn);
                guardar.setText("Registrar");
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_dcn);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int checki;
                            if(activo.isChecked()){
                                checki = 1;
                            }else{
                                checki = 0;
                            }
                            dc = new Docente(
                                    Integer.parseInt(id_escuela.getText().toString()),
                                    carnet.getText().toString(),
                                    anio_titulo.getText().toString(),
                                    checki,
                                    Integer.parseInt(tipo_jornada.getText().toString()),
                                    descripcion.getText().toString(),
                                    Integer.parseInt(cargo_actual.getText().toString()),
                                    Integer.parseInt(cargo_secundario.getText().toString()),
                                    nombre.getText().toString());
                            dao.insertar(dc);
                            lista = dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "¡Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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