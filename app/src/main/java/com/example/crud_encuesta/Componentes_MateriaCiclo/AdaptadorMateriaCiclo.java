package com.example.crud_encuesta.Componentes_MateriaCiclo;

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
import android.widget.EditText;
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

public class AdaptadorMateriaCiclo extends BaseAdapter {

    private Activity a;
    private ArrayList<MateriaCiclo> lista;
    private DAOMateriaCiclo dao;
    private MateriaCiclo materiaCiclo;
    private int id = 0;
    private int id_materia = 0;
    private SQLiteDatabase db;
    private DatabaseAccess access;
    private int anio_actual = Calendar.getInstance().get(Calendar.YEAR);
    private ArrayList<Materia> materias = new ArrayList<>();
    String nombre_materia;

    public AdaptadorMateriaCiclo(Activity a, ArrayList<MateriaCiclo> lista, DAOMateriaCiclo dao) {
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
    public MateriaCiclo getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_materia_ciclo,null);
        }

        TextView tmateria = (TextView) v.findViewById(R.id.txt_materia);
        TextView tciclo = (TextView) v.findViewById(R.id.txt_ciclo);
        final TextView tanio = (TextView) v.findViewById(R.id.txt_anio);

        Button ver = (Button) v.findViewById(R.id.btn_ver);
        Button editar = (Button) v.findViewById(R.id.btn_editar);
        Button eliminar = (Button) v.findViewById(R.id.btn_eliminar);

        materiaCiclo = lista.get(position);

        for(int i =0;i<materias.size();i++){
            if(materiaCiclo.getId_materia()==materias.get(i).getId()){
                nombre_materia = materias.get(i).getCodigo_materia();
            }
        }

        tmateria.setText(""+nombre_materia);
        tciclo.setText(""+materiaCiclo.getCiclo());
        tanio.setText(materiaCiclo.getAnio());

        ver.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        access = DatabaseAccess.getInstance(v.getContext());
        db=access.open();

        materias = DAOMateriaCiclo.materias(db);

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.parseInt(v.getTag().toString());
                String mate = "";

                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Vista de Materia Ciclo");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.vista_materia_ciclo);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_mat_cit);
                final TextView materia = (TextView) dialogo.findViewById(R.id.tv_materia);
                final RadioButton rd1 = (RadioButton) dialogo.findViewById(R.id.rb_ciclo1);
                final RadioButton rd2 = (RadioButton) dialogo.findViewById(R.id.rb_ciclo2);
                final TextView anio = (TextView) dialogo.findViewById(R.id.tv_anio);

                Button salir = (Button) dialogo.findViewById(R.id.btn_salir);

                materiaCiclo = lista.get(pos);

                mensaje.setText("Materia Ciclo");

                for (int i=0; i<materias.size(); i++){
                    if(materiaCiclo.getId_materia()==materias.get(i).getId()){
                        mate = materias.get(i).getCodigo_materia();
                    }
                }

                materia.setText(""+mate);

                if(materiaCiclo.getCiclo()==1){
                    rd1.setChecked(true);
                }else{
                    rd2.setChecked(true);
                }

                anio.setText(materiaCiclo.getAnio());

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
                dialogo.setTitle("Edición de Materia Ciclo");
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

                materiaCiclo = lista.get(pos);

                mensaje.setText("Editar Materia Ciclo");
                guardar.setText("Guardar");

                setId(materiaCiclo.getId());

                ArrayAdapter materiaAdapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1,DAOMateriaCiclo.listaMaterias(materias));
                sp_materia.setAdapter(materiaAdapter);

                for (int i=0; i<materias.size();i++){
                    if(materiaCiclo.getId_materia() == materias.get(i).getId()){
                        sp_materia.setSelection(i);
                    }
                }

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

                if(materiaCiclo.getCiclo()==1){
                    rb1.setChecked(true);
                }else{
                    rb2.setChecked(true);
                }

                anio.setText(materiaCiclo.getAnio());

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
                                ciclo_seleccionado=1;
                            }else{
                                ciclo_seleccionado = 2;
                            }

                            materiaCiclo = new MateriaCiclo(
                                    getId(),
                                    id_materia,
                                    ciclo_seleccionado,
                                    anio.getText().toString());
                            dao.editar(materiaCiclo);
                            lista = dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(a,"¡Error!",Toast.LENGTH_SHORT).show();
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
                materiaCiclo = lista.get(pos);
                setId(materiaCiclo.getId());

                final AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Quieres eliminar la Materia Ciclo?");
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
                    public void onClick(DialogInterface dialog, int which) {}
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
