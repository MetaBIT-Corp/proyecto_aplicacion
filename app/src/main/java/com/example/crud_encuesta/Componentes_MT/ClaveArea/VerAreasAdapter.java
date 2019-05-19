package com.example.crud_encuesta.Componentes_MT.ClaveArea;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class VerAreasAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context context;
    List<ClaveArea> areasClave = new ArrayList<>();

    public VerAreasAdapter(Context context, List<ClaveArea> areasClave) {
        this.context = context;
        this.areasClave = areasClave;

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

                final int i = Integer.parseInt(v.getTag().toString());

                System.out.println("-------------1");
                final View vEditar = inflater.inflate(R.layout.relacion_clave_area, null);
                System.out.println("-------------2");
                EditText et_numero_preguntas = (EditText)vEditar.findViewById(R.id.etPreguntas);
                EditText et_peso_area = (EditText)vEditar.findViewById(R.id.etPeso);
                Spinner sp_areas = vEditar.findViewById(R.id.spAreas);
                CheckBox cb_aleatorio = vEditar.findViewById(R.id.cbAleatorio);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

                //TextView txt = vEditar.findViewById(R.id.msj);
                //txt.setText(R.string.mt_editar);
                et_numero_preguntas.setText(areasClave.get(position).numero_preguntas);
                et_peso_area.setText(areasClave.get(position).peso);
                cb_aleatorio.setChecked(true);

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.mt_agregar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //agregar_relacion_clave_area(mView, id_seleccion);
                    }
                });

                mBuilder.setNegativeButton(R.string.mt_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                mBuilder.setView(vEditar);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Presionaste eliminar", Toast.LENGTH_SHORT).show();
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

    public void editar_claveArea(int pos, EditText num_p, EditText peso_a, Spinner sp_areas, CheckBox cb_aleatorio){
        int ide = areasClave.get(pos).id_ca;

        if(!num_p.getText().toString().isEmpty() && !peso_a.getText().toString().isEmpty()){
            DatabaseAccess database = DatabaseAccess.getInstance(context);
            SQLiteDatabase db = database.open();


            database.close();
        }

    }

}
