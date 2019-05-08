package com.example.crud_encuesta.Componentes_Estudiante;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityEstudiante extends AppCompatActivity {

    private DAOEstudiante dao;
    private AdaptadorEstudiante adapter;
    private ArrayList<Estudiante> lista;
    private Estudiante estudiante;
    private SQLiteDatabase db;
    private DatabaseAccess access;
    private int anio = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        dao = new DAOEstudiante(this);
        lista = dao.verTodos();

        adapter = new AdaptadorEstudiante(this,lista,dao);
        access = DatabaseAccess.getInstance(ActivityEstudiante.this);
        db = access.open();

        ListView list = (ListView) findViewById(R.id.lista_estudiante);
        Button agregar = (Button) findViewById(R.id.btn_nuevo_estudiante);

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

                final Dialog dialogo =new Dialog(ActivityEstudiante.this);
                dialogo.setContentView(R.layout.dialogo_estudiante);
                dialogo.setTitle("Registro de Estudiante");
                dialogo.setCancelable(true);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final EditText carnet = (EditText) dialogo.findViewById(R.id.editt_carnet_estd);
                final EditText nombre = (EditText) dialogo.findViewById(R.id.editt_nombre_estd);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad_estd);
                final EditText anio_ingreso = (EditText) dialogo.findViewById(R.id.editt_anio_ingreso_estd);
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_std);

                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar = (Button) dialogo.findViewById(R.id.btn_agregar_estd);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_estd);

                mensaje.setText("Registrar Nuevo Estudiante");
                guardar.setText("Registrar");
                cancelar.setText("Cancelar");

                btn_anio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog d = new Dialog(dialogo.getContext());
                        d.setTitle("Year Picker");
                        d.setContentView(R.layout.year_picker);
                        Button set = (Button) d.findViewById(R.id.button1);
                        Button cancel = (Button) d.findViewById(R.id.button2);
                        TextView year_text=(TextView)d.findViewById(R.id.year_text);
                        year_text.setText(""+anio);
                        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

                        nopicker.setMaxValue(anio+50);
                        nopicker.setMinValue(anio-50);
                        nopicker.setWrapSelectorWheel(false);
                        nopicker.setValue(anio);
                        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                        set.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                anio_ingreso.setText(String.valueOf(nopicker.getValue()));
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
                        try {

                            int check;
                            if(activo.isChecked()){check = 1;}
                            else{check = 0;}

                            estudiante = new Estudiante(
                                    carnet.getText().toString(),
                                    nombre.getText().toString(),
                                    check,
                                    anio_ingreso.getText().toString());

                            dao.insertar(estudiante);
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
