package com.example.crud_encuesta.Componentes_MT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class VerAreasAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context context;
    private List<String> areas = new ArrayList<>();
    private List<Integer> numero_preguntas = new ArrayList<>();
    private List<Integer> aleatorio = new ArrayList<>();
    private List<Integer> pesos = new ArrayList<>();
    private String clave;

    public VerAreasAdapter(Context context, List<String> areas, List<Integer> numero_preguntas, List<Integer> aleatorio, List<Integer> pesos, String clave) {
        this.context = context;
        this.areas = areas;
        this.numero_preguntas = numero_preguntas;
        this.aleatorio = aleatorio;
        this.pesos = pesos;
        this.clave = clave;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = inflater.inflate(R.layout.relacion_clave_area_ver, null);
        String modalidad="Manual";

        if(aleatorio.get(position)==1) modalidad="Aleatorio";

        TextView txt_clave = mView.findViewById(R.id.txtClave);
        TextView txt_area = mView.findViewById(R.id.txtArea);
        TextView txt_num_preguntas = mView.findViewById(R.id.txtNumeroPreguntas);
        TextView txt_peso = mView.findViewById(R.id.txtPeso);
        TextView txt_modalidad = mView.findViewById(R.id.txtModalidad);

        txt_clave.setText(clave);
        txt_area.setText(areas.get(position));
        txt_num_preguntas.setText(""+numero_preguntas.get(position));
        txt_peso.setText(""+pesos.get(position));
        txt_modalidad.setText(modalidad);

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

}
