package com.example.crud_encuesta.Componentes_MR;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.example.crud_encuesta.Componentes_EL.Escuela.Escuela;
import com.example.crud_encuesta.R;
import java.util.ArrayList;
import java.util.Calendar;

public class Funciones {

    public Activity a;

    public static String comprobarCampo(EditText campo, String nombre, Activity a){
        String cadena = "";
        if(campo.getText().toString().isEmpty()){
            cadena = a.getResources().getString(R.string.campo_v)+" "+nombre+" "+a.getResources().getString(R.string.vacio_v)+"\n";
        }
        return cadena;
    }

    public static String comprobarAnio(EditText campo, Activity a){

        String cadena = "";
        if(campo.getText().toString().isEmpty()){
            cadena="";
        }else{

            int anio_ingresado = Integer.parseInt(campo.getText().toString());
            int anio_actual = Calendar.getInstance().get(Calendar.YEAR);

            if( anio_ingresado>anio_actual+50 || anio_ingresado<anio_actual-50 ){
                cadena = a.getResources().getString(R.string.anio_v)+"\n";
            }
        }
        return cadena;
    }

    public static void setBtnAnio(final Dialog dialogo, Button btn_anio, final EditText anio_edt){

        btn_anio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d = new Dialog(dialogo.getContext());
                int anio_actual = Calendar.getInstance().get(Calendar.YEAR);
                d.setTitle("Year Picker");
                d.setContentView(R.layout.year_picker);

                Button set = (Button) d.findViewById(R.id.button1);
                Button cancel = (Button) d.findViewById(R.id.button2);
                TextView year_text=(TextView)d.findViewById(R.id.year_text);
                final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

                year_text.setText(R.string.set_anio);
                nopicker.setMaxValue(anio_actual+50);
                nopicker.setMinValue(anio_actual-50);
                nopicker.setWrapSelectorWheel(false);
                nopicker.setValue(anio_actual);
                nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                set.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        anio_edt.setText(String.valueOf(nopicker.getValue()));
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
                d.getWindow().setLayout(((Funciones.getWidth(d.getContext()) / 100) * 85), ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public static ArrayList<String> obtenerListaEscuelas(ArrayList<Escuela> escuelas) {
        ArrayList<String> escuelasList = new ArrayList<String>();
        for (int i =0 ; i < escuelas.size(); i++) {
            escuelasList.add(escuelas.get(i).getNombre());
        }
        return escuelasList;
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
