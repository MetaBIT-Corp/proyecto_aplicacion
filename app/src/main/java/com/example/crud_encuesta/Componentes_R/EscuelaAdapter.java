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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class EscuelaAdapter extends BaseAdapter {

    Context context;
    ArrayList<Escuela> l;
    SQLiteDatabase db;
    Activity activity;

    public EscuelaAdapter(Context c, ArrayList<Escuela> lista, SQLiteDatabase db, Activity a) {
        this.context = c;
        this.l = lista;
        this.db = db;
        this.activity = a;
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
                        Operaciones_CRUD.eliminar(db, context, EstructuraTablas.ESCUELA_TABLA_NAME, EstructuraTablas.COL_0, id);
                        l.clear();
                        setL(Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME, db, context));
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
                nom.setText(e.getNombre());

                d.setPositiveButton(R.string.actualizar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nom.getText().toString().isEmpty())Toast.makeText(context,R.string.men_camp_vacios,Toast.LENGTH_SHORT).show();
                        else{
                            ContentValues contentValues=new ContentValues();
                            contentValues.put(EstructuraTablas.COL_0,id);
                            contentValues.put(EstructuraTablas.COL_1,nom.getText().toString());
                            Operaciones_CRUD.actualizar(db,contentValues,context,EstructuraTablas.ESCUELA_TABLA_NAME,EstructuraTablas.COL_0,id);
                            l.clear();
                            setL(Operaciones_CRUD.todosEscuela(EstructuraTablas.ESCUELA_TABLA_NAME, db, context));
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
    public void setL(ArrayList<Escuela> l) {
        this.l = l;
        notifyDataSetChanged();
    }
}
