package com.example.crud_encuesta.Componentes_MT;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class ClaveAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    List<String> claves= new ArrayList<>();
    List<String> ides= new ArrayList<>();
    Context context;
    int[] incons;

    public ClaveAdapter(List<String> claves, List<String> ides, Context context, int[] incons) {
        this.claves = claves;
        this.ides = ides;
        this.context = context;
        this.incons = incons;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("Hey", "Aqui 1");

        final View mView = inflater.inflate(R.layout.elemento_list_clave, null);
        TextView num_clave = (TextView)mView.findViewById(R.id.msj_clave);
        ImageView informacion = (ImageView)mView.findViewById(R.id.info_clave);
        ImageView agregar = (ImageView)mView.findViewById(R.id.add_clave);
        ImageView editar = (ImageView)mView.findViewById(R.id.edit_clave);
        ImageView eliminar = (ImageView)mView.findViewById(R.id.delete_calve);

        num_clave.setText("Clave "+claves.get(position));
        informacion.setImageResource(incons[0]);
        agregar.setImageResource(incons[1]);
        editar.setImageResource(incons[2]);
        eliminar.setImageResource(incons[3]);

        informacion.setTag(position);
        agregar.setTag(position);
        editar.setTag(position);
        eliminar.setTag(position);

        return mView;
    }

    @Override
    public int getCount() {
        return claves.size();
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
