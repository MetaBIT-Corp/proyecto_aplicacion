package com.example.crud_encuesta.Componentes_Docente;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.Componentes_R.Escuela;
import com.example.crud_encuesta.Componentes_R.Operaciones_CRUD;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;
import java.util.ArrayList;
import java.util.Calendar;

public class AdaptadorDocente extends BaseAdapter {

    private DAODocente dao;
    private Docente dc;
    private Activity a;
    private int id = 0;
    private int id_escuela = 0;
    private SQLiteDatabase db;
    private DatabaseAccess access;
    private int anio = Calendar.getInstance().get(Calendar.YEAR);
    private ArrayList<Docente> lista;
    private ArrayList<Escuela> escuelas = new ArrayList<>();
    private ArrayList<String> listaEscuelas = new ArrayList<>();


    public AdaptadorDocente(Activity a, ArrayList<Docente> lista, DAODocente dao){
        this.lista = lista;
        this.a = a;
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
        dc = lista.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        dc = lista.get(position);
        return dc.getId();
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

        dc = lista.get(position);
        nombre.setText(dc.getNombre());
        carnet.setText(dc.getCarnet());
        ver.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        access = DatabaseAccess.getInstance(v.getContext());
        db=access.open();

        escuelas= Operaciones_CRUD.todosEscuela("ESCUELA",db);
        listaEscuelas = obtenerListaEscuelas();

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());
                String e = "";
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Edición de Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.vista_docente);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_docente);
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

                dc = lista.get(pos);
                mensaje.setText("Docente: "+dc.getCarnet());

                for(int i=0;i<escuelas.size();i++){
                    if(dc.getId_escuela()==escuelas.get(i).getId()){
                        e = escuelas.get(i).getNombre();
                    }
                }

                escuela.setText(""+e);
                carnet.setText(dc.getCarnet());
                anio_titulo.setText(dc.getAnio_titulo());
                if(dc.getActivo()==1){
                    activo.setChecked(true);
                }
                else{
                    activo.setChecked(false);
                }
                tipo_jornada.setText(""+dc.getTipo_jornada());
                descripcion.setText(""+dc.getDescripcion());
                cargo_actual.setText(""+dc.getCargo_actual());
                cargo_secundario.setText(""+dc.getCargo_secundario());
                nombre.setText(dc.getNombre());

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
                dialogo.setTitle("Edición de Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_docente);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_docente);
                final Spinner sp_escuela = (Spinner) dialogo.findViewById(R.id.sp_escuela);
                final EditText carnet = (EditText) dialogo.findViewById(R.id.editt_carnet);
                final EditText anio_titulo = (EditText) dialogo.findViewById(R.id.editt_anio_titulo);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad);
                final EditText tipo_jornada = (EditText) dialogo.findViewById(R.id.editt_tipo_jornada);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.editt_descripcion);
                final EditText cargo_actual = (EditText) dialogo.findViewById(R.id.editt_cargo_actual);
                final EditText cargo_secundario = (EditText) dialogo.findViewById(R.id.editt_cargo_secundario);
                final EditText nombre = (EditText) dialogo.findViewById(R.id.editt_nombre);

                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                Button guardar =(Button) dialogo.findViewById(R.id.btn_agregar_dcn);
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_dcn);

                mensaje.setText("Editar Docente");
                guardar.setText("Guardar");


                dc = lista.get(pos);

                setId(dc.getId());  /*Set de ID*/

                /*Seteando Escuela desde Spinner*/

                ArrayAdapter escuelaAdapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1, listaEscuelas);
                sp_escuela.setAdapter(escuelaAdapter);

                for (int i = 0; i < listaEscuelas.size(); i++) {
                    if (dc.getId_escuela() == escuelas.get(i).getId()){
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

                carnet.setText(dc.getCarnet()); /*Set de Carnet*/

                /*Seteando de Año de Titulación desde YearPicker*/

                anio_titulo.setText(""+dc.getAnio_titulo());
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
                                anio_titulo.setText(String.valueOf(nopicker.getValue()));
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

                if(dc.getActivo()==1){
                    activo.setChecked(true);
                }else{
                    activo.setChecked(false);
                }

                tipo_jornada.setText(""+dc.getTipo_jornada());
                descripcion.setText(dc.getDescripcion());
                cargo_actual.setText(""+dc.getCargo_actual());
                cargo_secundario.setText(""+dc.getCargo_secundario());
                nombre.setText(dc.getNombre());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            int check;
                            if(activo.isChecked()){
                                check = 1;
                            }else{
                                check = 0;
                            }
                            dc = new Docente(
                                    getId(),
                                    id_escuela,
                                    carnet.getText().toString(),
                                    anio_titulo.getText().toString(),
                                    check,
                                    Integer.parseInt(tipo_jornada.getText().toString()),
                                    descripcion.getText().toString(),
                                    Integer.parseInt(cargo_actual.getText().toString()),
                                    Integer.parseInt(cargo_secundario.getText().toString()),
                                    nombre.getText().toString());
                            dao.editar(dc);
                            notifyDataSetChanged();
                            lista = dao.verTodos();
                            dialogo.dismiss();

                        } catch (Exception e){
                            Toast.makeText(a, "¡Error!", Toast.LENGTH_SHORT).show();
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

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = Integer.parseInt(v.getTag().toString());
                dc = lista.get(pos);
                setId(dc.getId());
                final AlertDialog.Builder del = new AlertDialog.Builder(a);

                del.setMessage("¿Quieres eliminar el Docente?");
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

    public ArrayList<String> obtenerListaEscuelas() {
        ArrayList<String> escuelasList = new ArrayList<>();
        for (int i =0 ; i < escuelas.size(); i++) {
            escuelasList.add(escuelas.get(i).getNombre());
        }
        return escuelasList;
    }
    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
