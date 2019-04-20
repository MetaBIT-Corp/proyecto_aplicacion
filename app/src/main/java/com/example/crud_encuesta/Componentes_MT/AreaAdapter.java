package com.example.crud_encuesta.Componentes_MT;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    Context context;
    List<String> areas = new ArrayList<>();
    List<String> ides = new ArrayList<>();
    int[] iconos;

    public AreaAdapter(Context context, List<String> areas, List<String> ides, int[] iconos){
        this.context = context;
        this.areas = areas;
        this.ides = ides;
        this.iconos = iconos;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View mView = inflater.inflate(R.layout.elemento_list_area, null);
        TextView tituloArea = (TextView)mView.findViewById(R.id.elemento_area);
        ImageView imgEdit = (ImageView)mView.findViewById(R.id.img_edit);
        ImageView imgDelete = (ImageView)mView.findViewById(R.id.img_delete);

        tituloArea.setText(areas.get(position));
        imgEdit.setImageResource(iconos[0]);
        imgDelete.setImageResource(iconos[1]);

        imgEdit.setTag(position);
        imgDelete.setTag(position);

        imgEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final int i = Integer.parseInt(v.getTag().toString());
                final View mView = inflater.inflate(R.layout.modal_area, null);
                EditText edt = (EditText)mView.findViewById(R.id.etArea);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

                TextView txt = mView.findViewById(R.id.msj);
                txt.setText("Editar");
                edt.setText(areas.get(i));
                mBuilder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editar_area(i, mView);
                        Intent i = new Intent(context, AreaActivity.class);
                        context.startActivity(i);
                    }
                });

                mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                final int i = Integer.parseInt(v.getTag().toString());


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                final View mView = inflater.inflate(R.layout.modal_area, null);
                TextView msj = (TextView)mView.findViewById(R.id.msj);
                EditText edt = (EditText)mView.findViewById(R.id.etArea);
                msj.setText("¿Desea eliminar este objeto?");
                edt.setVisibility(View.INVISIBLE);

                mBuilder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar_area(i);
                        Toast.makeText(context, "Se ha eliminado con éxito", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, AreaActivity.class);
                        context.startActivity(i);

                    }
                });

                mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();



            }
        });

        return mView;
    }

    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void eliminar_area(int id){
        DatabaseAccess database = DatabaseAccess.getInstance(context);
        SQLiteDatabase db = database.open();

        int ide = Integer.parseInt(ides.get(id));
        db.delete("area","id_area="+ide, null);


        database.close();
    }

    public void editar_area(int id, View v){
        EditText edt = (EditText)v.findViewById(R.id.etArea);
        int ide = Integer.parseInt(ides.get(id));

        DatabaseAccess database = DatabaseAccess.getInstance(context);
        SQLiteDatabase db = database.open();

        ContentValues registro = new ContentValues();
        String titulo = edt.getText().toString();

        if(!titulo.isEmpty()){
            registro.put("titulo", titulo);
            db.update("area", registro, "id_area="+ide, null);
        }else{
            Toast.makeText(context, "El area debe tener un titulo", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }

}
