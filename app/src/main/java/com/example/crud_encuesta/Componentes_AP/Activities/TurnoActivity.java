package com.example.crud_encuesta.Componentes_AP.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_AP.Adapters.AdapterEvaluacion;
import com.example.crud_encuesta.Componentes_AP.Adapters.AdapterTurno;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOEvaluacion;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOTurno;
import com.example.crud_encuesta.Componentes_AP.Models.Evaluacion;
import com.example.crud_encuesta.Componentes_AP.Models.Turno;
import com.example.crud_encuesta.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TurnoActivity extends AppCompatActivity {

    DAOTurno daoTurno;
    AdapterTurno adapterTurno;
    ArrayList<Turno> turnos;
    Turno turno;
    private int anio, mes, dia, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno);

        daoTurno = new DAOTurno(this);
        turnos = daoTurno.verTodos();
        adapterTurno = new AdapterTurno(turnos,daoTurno,this);

        final ImageView agregar = (ImageView) findViewById(R.id.ap_imgv_agregar_turno);
        ImageView buscar = (ImageView) findViewById(R.id.ap_imgv_buscar_turno);
        ImageView all = (ImageView) findViewById(R.id.ap_imgv_all_turno);
        final EditText edt_buscar = (EditText) findViewById(R.id.ap_edt_buscar_turno);

        ListView listView = (ListView) findViewById(R.id.lista_turno);
        listView.setAdapter(adapterTurno);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:intent a claves
            }
        });

        //TODO: Listeners

        //Inicio listener de agregar
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dialogo de crear

                final Dialog dialog = new Dialog(TurnoActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//para quitar el titulo
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_turno);
                dialog.show();

                //enlazamos views del dialogo turno
                final TextView titulo = (TextView) dialog.findViewById(R.id.ap_tv_title_dialogo_turno);
                final EditText dateinicial = (EditText) dialog.findViewById(R.id.ap_edt_dateinicial);
                final EditText datefinal = (EditText) dialog.findViewById(R.id.ap_edt_datefinal);
                final EditText timeinicial = (EditText) dialog.findViewById(R.id.ap_edt_timeinicial);
                final EditText timefinal = (EditText) dialog.findViewById(R.id.ap_edt_timefinal);
                final EditText contrasenia = (EditText) dialog.findViewById(R.id.ap_edt_pass_turno);
                final CheckBox visible =(CheckBox) dialog.findViewById(R.id.ap_cb_visible);

                Button btCrear = (Button) dialog.findViewById(R.id.d_agregar_turno);
                Button btCancelar = (Button) dialog.findViewById(R.id.d_cancelar_turno);
                ImageView imv_dateinicial = (ImageView) dialog.findViewById(R.id.ap_imv_dateinicial);
                ImageView imv_timeinicial = (ImageView) dialog.findViewById(R.id.ap_imv_timeinicial);
                ImageView imv_datefinal = (ImageView) dialog.findViewById(R.id.ap_imv_datefinal);
                ImageView imv_timefinal = (ImageView) dialog.findViewById(R.id.ap_imv_timefinal);

                //fecha y hora actual

                Date date = new Date();
                DateFormat formatoHora = new SimpleDateFormat("HH:mm");
                DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

                dateinicial.setText(""+formatoFecha.format(date));
                datefinal.setText(""+formatoFecha.format(date));
                timeinicial.setText(""+formatoHora.format(date));
                timefinal.setText(""+formatoHora.format(date));

                //programamos metodo crear y cancelar y pickers

                imv_dateinicial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(dateinicial,v.getContext());
                    }
                });

                imv_timeinicial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timepicker(timeinicial,v.getContext());
                    }
                });

                imv_datefinal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(datefinal,v.getContext());
                    }
                });

                imv_timefinal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timepicker(timefinal,v.getContext());
                    }
                });


                //programamos botones de crear-guardar y cancelar
                btCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int ver = 0;
                        if (visible.isChecked()) {
                            ver = 1;
                        }
                        if (!dateinicial.getText().toString().isEmpty() && !datefinal.getText().toString().isEmpty()
                                && !timeinicial.getText().toString().isEmpty() && !timefinal.getText().toString().isEmpty()
                                && !contrasenia.getText().toString().isEmpty()) {
                            try {
                                turno = new Turno(
                                        1,
                                        dateinicial.getText().toString() + " " + timeinicial.getText().toString(),
                                        datefinal.getText().toString() + " " + timefinal.getText().toString(),
                                        ver,
                                        contrasenia.getText().toString()
                                );
                                //creamos registro
                                daoTurno.Insertar(turno);
                                //refrescamos la lista
                                turnos = daoTurno.verTodos();
                                //como ya estamos dentro de la clase adaptador simplemente ejecutamos el metodo
                                adapterTurno.notifyDataSetChanged();
                                //cerramos el dialogo
                                dialog.dismiss();


                            } catch (Exception e) {
                                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(
                                    v.getContext(),
                                    "Debes llenar todos los campos",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        //Final de listener de agregar

        //Inicio de listener de buscar
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_buscar.getText().toString().isEmpty()){
                    turnos = daoTurno.verUno(Integer.parseInt(edt_buscar.getText().toString()));
                    adapterTurno.notifyDataSetChanged();
                }else{
                    Toast.makeText(
                            v.getContext(),
                            "Debes ingresar un Id del turno",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        //Final de listener de buscar

        //Inicio de listener all
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnos = daoTurno.verTodos();
                adapterTurno.notifyDataSetChanged();
            }
        });
        //Final de listener all
    }

    public void datePicker(final EditText editText, Context context){
        anio = 2019;
        mes = 1;
        dia = 0;

        //creamos instancia de clase DatePickerDialog
        DatePickerDialog datePickerDialog =new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                    }
                },
                dia,
                mes,
                anio
        );
        datePickerDialog.show();
    }

    public void timepicker(final EditText editText,Context context){
        hora = 12;
        minuto = 0;
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editText.setText(hourOfDay+":"+minute);
                    }
                },
                hora,
                minuto,
                false
        );
        timePickerDialog.show();

    }
}
