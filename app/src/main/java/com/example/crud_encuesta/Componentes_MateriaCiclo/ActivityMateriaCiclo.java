package com.example.crud_encuesta.Componentes_MateriaCiclo;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_EL.Materia.Materia;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityMateriaCiclo extends AppCompatActivity {

    private SQLiteDatabase db;
    private DatabaseAccess access;
    private ArrayList<MateriaCiclo> lista;
    private ArrayList<Materia> materias = new ArrayList<>();
    private DAOMateriaCiclo dao;
    private AdaptadorMateriaCiclo adapter;
    private int id_materia;
    private int anio_actual = Calendar.getInstance().get(Calendar.YEAR);
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

        materias = DAOMateriaCiclo.materias(db);

        ListView list = (ListView) findViewById(R.id.lista_mat_ci);
        FloatingActionButton agregar = (FloatingActionButton) findViewById(R.id.btn_agregar);

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
                final Dialog dialogo = new Dialog(ActivityMateriaCiclo.this);
                dialogo.setTitle("Registro de Materia Ciclo");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_materia_ciclo);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_mat_cit);
                final Spinner sp_materia = (Spinner) dialogo.findViewById(R.id.sp_materia);
                final RadioButton rb1 = (RadioButton) dialogo.findViewById(R.id.rb_ciclo1);
                final RadioButton rb2 = (RadioButton) dialogo.findViewById(R.id.rb_ciclo2);
                final EditText anio = (EditText) dialogo.findViewById(R.id.editt_anio);

                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar = (Button) dialogo.findViewById(R.id.btn_agregar);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar);

                mensaje.setText("Registrar Materia Ciclo");
                guardar.setText("Registrar");

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
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btn_anio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog d = new Dialog(dialogo.getContext());
                        d.setTitle("Year Picker");
                        d.setContentView(R.layout.year_picker);
                        Button set = (Button) d.findViewById(R.id.button1);
                        Button cancel = (Button) d.findViewById(R.id.button2);
                        TextView year_text=(TextView)d.findViewById(R.id.year_text);
                        year_text.setText(""+anio_actual);
                        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

                        nopicker.setMaxValue(anio_actual+50);
                        nopicker.setMinValue(anio_actual-50);
                        nopicker.setWrapSelectorWheel(false);
                        nopicker.setValue(anio_actual);
                        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                        set.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                anio.setText(String.valueOf(nopicker.getValue()));
                                d.dismiss();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                d.dismiss();
                            }
                        });
                        d.show();
                        d.getWindow().setLayout(((getWidth(d.getContext()) / 100) * 85), ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                });

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
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
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "¡Error!", Toast.LENGTH_SHORT).show();}
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

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
