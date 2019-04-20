package com.example.crud_encuesta.Componentes_R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class CarreraAdapter extends BaseAdapter {
    Context context;
    ArrayList<Carrera> l;
    SQLiteDatabase db;
    Activity activity;
    ArrayList<Escuela> escuelas;
    int id_escuela;
    ArrayList<String> listSpinner;

    public CarreraAdapter(Context c, ArrayList<Carrera> lista, SQLiteDatabase db, Activity a, ArrayList<Escuela> e) {
        this.context = c;
        this.l = lista;
        this.db = db;
        this.activity = a;
        this.escuelas = e;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public Carrera getItem(int position) {
        return l.get(position);
    }

    @Override
    public long getItemId(int position) {
        return l.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_escuela, null);
        }

        Button btneditar = view.findViewById(R.id.btn_editar);
        Button btneliminar = view.findViewById(R.id.btn_eliminar);

        TextView textView = view.findViewById(R.id.txt_escuela_item);
        textView.setText(l.get(position).toString());

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id = (int) getItemId(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.title_borrar);
                alert.setMessage(R.string.lbl_borrar);
                alert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Operaciones_CRUD.eliminar(db, context, EstructuraTablas.CARRERA_TABLA_NAME, EstructuraTablas.COL_0_CARRERA, id);
                        l.clear();
                        setL(Operaciones_CRUD.todosCarrera(db, escuelas));
                    }
                });
                alert.setNegativeButton(R.string.cancelar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = (int) getItemId(position);
                final Carrera c = getItem(position);
                final AlertDialog.Builder d = new AlertDialog.Builder(context);

                View view = inflater.inflate(R.layout.dialogo_carrera, null);
                final EditText nom = view.findViewById(R.id.in_nom_carrera);
                final Spinner s = view.findViewById(R.id.spinner_carrera);
                nom.setText(c.getNombre());

                listSpinner=obtenerLista();
                ArrayAdapter adaptadorSpinner=new ArrayAdapter(context,android.R.layout.simple_list_item_1,listSpinner);
                s.setAdapter(adaptadorSpinner);

                for(int i=0;i<listSpinner.size();i++){
                    if(c.getEscuela().getNombre()==listSpinner.get(i)) s.setSelection(i);
                }
                s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            id_escuela = escuelas.get(position - 1).getId();
                        } else id_escuela = -1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                d.setPositiveButton(R.string.actualizar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nom.getText().toString().isEmpty())
                            Toast.makeText(context, R.string.men_camp_vacios, Toast.LENGTH_SHORT).show();
                        else {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(EstructuraTablas.COL_0_CARRERA, id);
                            contentValues.put(EstructuraTablas.COL_1_CARRERA, id_escuela);
                            contentValues.put(EstructuraTablas.COL_2_CARRERA, nom.getText().toString());
                            Operaciones_CRUD.actualizar(db, contentValues, context, EstructuraTablas.CARRERA_TABLA_NAME, EstructuraTablas.COL_0_CARRERA, id).show();
                            l.clear();
                            setL(Operaciones_CRUD.todosCarrera(db,escuelas));
                        }
                    }
                });

                d.setNegativeButton(R.string.cancelar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                d.setView(view);
                d.show();

            }
        });


        return view;
    }

    public void setL(ArrayList<Carrera> l) {
        this.l = l;
        notifyDataSetChanged();
    }

    public ArrayList<String> obtenerLista(){
        ArrayList<String> listaEs=new ArrayList<>();

        listaEs.add("Seleccione");

        for (int i=0;i<escuelas.size();i++){
            listaEs.add(escuelas.get(i).getNombre());
        }
        return listaEs;
    }
}
