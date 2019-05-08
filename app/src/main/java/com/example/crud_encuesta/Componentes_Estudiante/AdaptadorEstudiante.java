package com.example.crud_encuesta.Componentes_Estudiante;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;
import java.util.ArrayList;
import java.util.Calendar;

public class AdaptadorEstudiante extends BaseAdapter {

    private Activity a;
    private ArrayList<Estudiante> lista;
    private DAOEstudiante dao;
    private Estudiante estudiante;
    private int id = 0;
    private SQLiteDatabase db;
    private DatabaseAccess access;
    private int anio = Calendar.getInstance().get(Calendar.YEAR);

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

        estudiante = lista.get(position);

        nombre.setText(estudiante.getNombre());
        carnet.setText(estudiante.getCarnet());

        ver.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        access = DatabaseAccess.getInstance(v.getContext());
        db = access.open();

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());

                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Ver Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.vista_estudiante);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_std);
                final TextView carnet = (TextView) dialogo.findViewById(R.id.tv_carnet_estd);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad_estd);
                final TextView nombre = (TextView) dialogo.findViewById(R.id.tv_nombre_estd);
                final TextView anio_ingreso = (TextView) dialogo.findViewById(R.id.tv_anio_ingreso_estd);

                Button salir = (Button) dialogo.findViewById(R.id.btn_salir);

                estudiante = lista.get(pos);

                mensaje.setText("Estudiante: "+estudiante.getCarnet());

                carnet.setText(estudiante.getCarnet());

                if(estudiante.getActivo()==1){
                    activo.setChecked(true);
                }else{
                    activo.setChecked(false);
                }

                nombre.setText(estudiante.getNombre());
                anio_ingreso.setText(estudiante.getAnio_ingreso());

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
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Edición de Estudiante");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_estudiante);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final TextView carnet = (TextView) dialogo.findViewById(R.id.editt_carnet_estd);
                final TextView nombre = (TextView) dialogo.findViewById(R.id.editt_nombre_estd);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad_estd);
                final TextView anio_ingreso = (TextView) dialogo.findViewById(R.id.editt_anio_ingreso_estd);
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_std);

                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar =(Button) dialogo.findViewById(R.id.btn_agregar_estd);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_estd);

                estudiante = lista.get(pos);

                mensaje.setText("Editar Estudiante: "+estudiante.getCarnet().toUpperCase());
                guardar.setText("Guardar");

                setId(estudiante.getId());
                carnet.setText(estudiante.getCarnet());

                if(estudiante.getActivo()==1){
                    activo.setChecked(true);
                }else{
                    activo.setChecked(false);
                }

                nombre.setText(estudiante.getNombre());
                anio_ingreso.setText(estudiante.getAnio_ingreso());

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
                                    getId(),
                                    carnet.getText().toString(),
                                    nombre.getText().toString(),
                                    check,
                                    anio_ingreso.getText().toString());
                            dao.editar(estudiante);
                            notifyDataSetChanged();
                            lista = dao.verTodos();
                            dialogo.dismiss();

                        }catch (Exception e){Toast.makeText(a, "¡Error!", Toast.LENGTH_SHORT).show();}
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

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());
                estudiante = lista.get(pos);
                setId(estudiante.getId());

                final AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Quierés elimar el Estudiante "+estudiante.getCarnet()+"?");
                del.setCancelable(true);

                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });

                del.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                del.show();
            }
        });

        return v;
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
