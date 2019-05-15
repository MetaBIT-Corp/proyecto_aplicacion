package com.example.crud_encuesta.Componentes_EL.Escuela;

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

import com.example.crud_encuesta.Componentes_EL.EstructuraTablas;
import com.example.crud_encuesta.Componentes_EL.ModelosAdicionales.Facultad;
import com.example.crud_encuesta.Componentes_EL.Operaciones_CRUD;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class EscuelaAdapter extends BaseAdapter {

    Context context;
    ArrayList<Escuela> l;
    SQLiteDatabase db;
    Activity activity;
    ArrayList<Facultad> facultades;
    ArrayList<String> listafacultades;
    int id_facu;

    public EscuelaAdapter(Context c, ArrayList<Escuela> lista, SQLiteDatabase db, Activity a, ArrayList<Facultad> f) {
        this.context = c;
        this.l = lista;
        this.db = db;
        this.activity = a;
        this.facultades=f;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public Escuela getItem(int position) {
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
        Button btninfo=view.findViewById(R.id.btn_infor);
        TextView textView=view.findViewById(R.id.txt_escuela_item);
        textView.setText(l.get(position).toString());

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id = (int) getItemId(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.title_borrar);
                alert.setMessage(R.string.lbl_borrar_escuela);
                alert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Operaciones_CRUD.eliminar(db, context, EstructuraTablas.ESCUELA_TABLA_NAME, EstructuraTablas.COL_0, id);
                        l.clear();
                        setL(Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME, db));
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
                final Escuela e = getItem(position);
                final AlertDialog.Builder d=new AlertDialog.Builder(context);

                View view=inflater.inflate(R.layout.dialogo_escuela, null);
                final EditText nom=view.findViewById(R.id.in_nom_escuela);
                final EditText cod=view.findViewById(R.id.in_cod_escuela);
                final Spinner facu=view.findViewById(R.id.spinner_lista_facultad);
                TextView title=view.findViewById(R.id.title_escuela);
                title.setText(R.string.title_activity_act);
                listafacultades=obtenerListaFacultad();
                ArrayAdapter adapterFa = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listafacultades);
                facu.setAdapter(adapterFa);

                facu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) id_facu = facultades.get(position - 1).getId();
                        else id_facu= -1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                nom.setText(l.get(position).getNombre());
                cod.setText(l.get(position).getCod());
                for (int i = 0; i < facultades.size(); i++) {
                    if (e.getFacultad() == facultades.get(i).getId()) facu.setSelection(i+1);
                }

                d.setPositiveButton(R.string.actualizar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nom.getText().toString().isEmpty())Toast.makeText(context,R.string.men_camp_vacios,Toast.LENGTH_SHORT).show();
                        else{
                            ContentValues contentValues=new ContentValues();
                            contentValues.put(EstructuraTablas.COL_0,id);
                            contentValues=new ContentValues();
                            contentValues.put(EstructuraTablas.COL_2,nom.getText().toString());
                            contentValues.put(EstructuraTablas.COL_1,id_facu);
                            contentValues.put(EstructuraTablas.COL_3,cod.getText().toString());
                            Operaciones_CRUD.actualizar(db,contentValues,context,EstructuraTablas.ESCUELA_TABLA_NAME,EstructuraTablas.COL_0,id);
                            l.clear();
                            setL(Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME, db));
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
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Escuela e = getItem(position);
                final AlertDialog.Builder d = new AlertDialog.Builder(context);

                View view = inflater.inflate(R.layout.dialogo_escuela, null);

                final EditText nom=view.findViewById(R.id.in_nom_escuela);
                final EditText cod=view.findViewById(R.id.in_cod_escuela);
                final Spinner facu=view.findViewById(R.id.spinner_lista_facultad);
                final TextView title=view.findViewById(R.id.title_escuela);
                title.setText(R.string.title_activity_info);
                listafacultades=obtenerListaFacultad();
                ArrayAdapter adapterFa = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listafacultades);
                facu.setAdapter(adapterFa);

                nom.setEnabled(false);
                cod.setEnabled(false);
                facu.setEnabled(false);

                nom.setText(l.get(position).getNombre());
                cod.setText(l.get(position).getCod());
                for (int i = 0; i < facultades.size(); i++) {
                    if (e.getFacultad() == facultades.get(i).getId()) facu.setSelection(i+1);
                }



                d.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
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
    public void setL(ArrayList<Escuela> l) {
        this.l = l;
        notifyDataSetChanged();
    }
    public ArrayList<String> obtenerListaFacultad() {
        ArrayList<String> listaca = new ArrayList<>();
        listaca.add("Seleccione");
        for (int i = 0; i < facultades.size(); i++) {
            listaca.add(facultades.get(i).toString());
        }
        return listaca;
    }
}
