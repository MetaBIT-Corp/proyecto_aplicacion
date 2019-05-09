package com.example.crud_encuesta.Componentes_MT.ClaveArea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = inflater.inflate(R.layout.relacion_clave_area_ver, null);
        String modalidad="Manual";

        if(areasClave.get(position).aleatorio==1) modalidad="Aleatorio";

        TextView txt_clave = mView.findViewById(R.id.txtClave);
        TextView txt_area = mView.findViewById(R.id.txtArea);
        TextView txt_num_preguntas = mView.findViewById(R.id.txtNumeroPreguntas);
        TextView txt_peso = mView.findViewById(R.id.txtPeso);
        TextView txt_modalidad = mView.findViewById(R.id.txtModalidad);

        txt_clave.setText(areasClave.get(position).clave);
        txt_area.setText(areasClave.get(position).area);
        txt_num_preguntas.setText(""+areasClave.get(position).numero_preguntas);
        txt_peso.setText(""+areasClave.get(position).peso);
        txt_modalidad.setText(modalidad);

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

}
