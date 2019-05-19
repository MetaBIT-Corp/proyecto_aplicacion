package com.example.crud_encuesta.Componentes_MT.ClaveArea;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class VerAreasAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener{
    private LayoutInflater inflater = null;
    private Context context;
    List<ClaveArea> areasClave = new ArrayList<>();
    DAOClaveArea daoClaveArea;
    int id_clave;
    String clave;

    private int pos_area;
    private List<Integer> id_areas = new ArrayList<>();

    public VerAreasAdapter(Context context, List<ClaveArea> areasClave, DAOClaveArea daoClaveArea, int id_clave, String clave) {
        this.context = context;
        this.areasClave = areasClave;
        this.daoClaveArea = daoClaveArea;
        this. id_clave = id_clave;
        this.clave = clave;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View mView = inflater.inflate(R.layout.relacion_clave_area_ver, null);
        String modalidad="Manual";

        if(areasClave.get(position).aleatorio==1) modalidad="Aleatorio";

        TextView txt_clave = mView.findViewById(R.id.txtClave);
        TextView txt_area = mView.findViewById(R.id.txtArea);
        TextView txt_num_preguntas = mView.findViewById(R.id.txtNumeroPreguntas);
        TextView txt_peso = mView.findViewById(R.id.txtPeso);
        TextView txt_modalidad = mView.findViewById(R.id.txtModalidad);
        ImageView editar = mView.findViewById(R.id.imgEditCA);
        ImageView eliminar = mView.findViewById(R.id.imgDeleteCA);

        txt_clave.setText(areasClave.get(position).clave);
        txt_area.setText(areasClave.get(position).area);
        txt_num_preguntas.setText(""+areasClave.get(position).numero_preguntas);
        txt_peso.setText(""+areasClave.get(position).peso);
        txt_modalidad.setText(modalidad);

        editar.setTag(position);
        eliminar.setTag(position);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id_seleccion = Integer.parseInt(v.getTag().toString());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                final View mView = inflater.inflate(R.layout.relacion_clave_area, null);
                cargarAreas(mView);

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.mt_actualizar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editar_relacion_clave_area(id_seleccion, mView);
                        areasClave = daoClaveArea.getAreasClave(id_clave, clave);
                        notifyDataSetChanged();
                    }
                });

                mBuilder.setNegativeButton(R.string.mt_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int i = Integer.parseInt(v.getTag().toString());

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

                mBuilder.setMessage(R.string.mt_eliminar_clave);
                mBuilder.setIcon(R.drawable.ic_delete);
                mBuilder.setTitle(R.string.mt_eliminar);

                mBuilder.setPositiveButton(R.string.mt_eliminar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar_clave_area(i);
                        Toast.makeText(context, R.string.mt_eliminado_msj, Toast.LENGTH_SHORT).show();
                        areasClave = daoClaveArea.getAreasClave(id_clave, clave);
                        notifyDataSetChanged();

                    }
                });

                mBuilder.setNegativeButton(R.string.mt_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        return mView;
    }

    @Override
    public int getCount() {
        return areasClave.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void editar_relacion_clave_area(int pos, View v){
        int ide = areasClave.get(pos).id_ca;

        EditText cant_preguntas = v.findViewById(R.id.etPreguntas);
        EditText peso_area = v.findViewById(R.id.etPeso);
        CheckBox aleatorio_cb = v.findViewById(R.id.cbAleatorio);

        int aleatorio=0;
        int peso = 0;
        int cantidad = 0;

        if(!cant_preguntas.getText().toString().isEmpty() && !peso_area.getText().toString().isEmpty()) {
            cantidad = Integer.parseInt(cant_preguntas.getText().toString());
            peso = Integer.parseInt(peso_area.getText().toString());
            int id_area = id_areas.get(pos_area);
            if (aleatorio_cb.isChecked()) aleatorio = 1;

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
            SQLiteDatabase db = databaseAccess.open();

            ContentValues registro = new ContentValues();

            registro.put("id_area", id_area);
            registro.put("numero_preguntas", cantidad);
            registro.put("aleatorio", aleatorio);
            registro.put("peso", peso);

            Cursor cantidad_preguntas = db.rawQuery("SELECT ID_GRUPO_EMP FROM GRUPO_EMPAREJAMIENTO WHERE ID_AREA="+id_area, null);

            if(cantidad_preguntas.getCount()>=cantidad){
                if (cantidad > 0 && peso > 0) {

                    db.update("clave_area", registro, "id_clave_area="+ide, null);
                    Toast.makeText(context, R.string.mt_registro_actualizado, Toast.LENGTH_SHORT).show();
                    databaseAccess.close();

                } else {
                    Toast.makeText(context, R.string.mt_cantidad_positiva, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, R.string.mt_cant_preguntas_ingresadas, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, R.string.mt_campos_vacios, Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar_clave_area(int id){
        int ide = areasClave.get(id).id_ca;
        DatabaseAccess database = DatabaseAccess.getInstance(context);
        SQLiteDatabase db = database.open();

        db.delete("CLAVE_AREA","ID_CLAVE_AREA="+ide, null);
        database.close();
    }

    public void cargarAreas(View va){
        ArrayAdapter<String> comboAdapter;
        Spinner spAreas = (Spinner)va.findViewById(R.id.spAreas);
        spAreas.setOnItemSelectedListener(this);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        SQLiteDatabase db = databaseAccess.open();

        List<String> clave_area = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id_area, titulo FROM area", null);

        while (cursor.moveToNext()) {
            id_areas.add(cursor.getInt(0));
            clave_area.add(cursor.getString(1));
        }
        cursor.close();

        databaseAccess.close();

        comboAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, clave_area);
        spAreas.setAdapter(comboAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pos_area=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
