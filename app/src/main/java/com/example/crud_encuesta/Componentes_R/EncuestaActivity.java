package com.example.crud_encuesta.Componentes_R;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.Calendar;

public class EncuestaActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseAccess access;
    ListView listView;
    int dia,mes,año,ho,min;
    boolean seg;

    int di,df,mi,mf,ai,af;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        access=DatabaseAccess.getInstance(EncuestaActivity.this);
        db=access.open();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder d=new AlertDialog.Builder(EncuestaActivity.this);

                View v=getLayoutInflater().inflate(R.layout.dialogo_encuesta, null);

                Button btnfi=v.findViewById(R.id.btn_fecha_inicio);
                Button btnff=v.findViewById(R.id.btn_fecha_final);
                final EditText infi=v.findViewById(R.id.in_fecha_inicial);
                final EditText inff=v.findViewById(R.id.in_fecha_final);
                final EditText nom=v.findViewById(R.id.in_nom_encuesta);
                final EditText desc=findViewById(R.id.in_descrip_encuesta);

                btnfi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c=Calendar.getInstance();
                        dia=c.get(Calendar.DAY_OF_MONTH);
                        mes=c.get(Calendar.MONTH);
                        año=c.get(Calendar.YEAR);

                        ho=c.get(Calendar.HOUR_OF_DAY);
                        min=c.get(Calendar.MINUTE);
                        seg=false;

                        DatePickerDialog calendar=new DatePickerDialog(EncuestaActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                ai=year;
                                mi=month;
                                di=dayOfMonth;
                                infi.setText(dayOfMonth+"/"+month+"/"+year);
                            }
                        },año,mes,dia);
                        calendar.show();
                    }
                });

                btnff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c=Calendar.getInstance();
                        dia=c.get(Calendar.DAY_OF_MONTH);
                        mes=c.get(Calendar.MONTH);
                        año=c.get(Calendar.YEAR);

                        DatePickerDialog calendar=new DatePickerDialog(EncuestaActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                af=year;
                                mf=month;
                                df=dayOfMonth;
                                final String cadena=dayOfMonth+"/"+month+"/"+year;

                                TimePickerDialog hora=new TimePickerDialog(EncuestaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        infi.setText(cadena+hourOfDay+":"+minute);
                                    }
                                },ho,min,seg);
                                hora.show();
                            }
                        },año,mes,dia);
                        calendar.show();
                    }
                });

                if ((di>df&&mi>=mf)||mi>mf||ai>af){

                }
                d.setPositiveButton(R.string.agregar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(nom.getText().toString().isEmpty()|| desc.getText().toString().isEmpty()|| infi.getText().toString().equals("dd/mm/aa") || inff.getText().toString().equals("dd/mm/aa"))
                            Toast.makeText(EncuestaActivity.this,R.string.men_camp_vacios,Toast.LENGTH_SHORT).show();
                        else{
                            ContentValues contentValues=new ContentValues();

                            /*OOOOOJOOOOO CAMBIAR CUANDO MANDEN EL ID DEL DOCENTE LOGUEADO*/
                            contentValues.put(EstructuraTablas.COL_1_ENCUESTA,1);

                            contentValues.put(EstructuraTablas.COL_2_ENCUESTA,nom.getText().toString());
                            contentValues.put(EstructuraTablas.COL_3_ENCUESTA,desc.getText().toString());
                            contentValues.put(EstructuraTablas.COL_4_ENCUESTA,infi.getText().toString());
                            contentValues.put(EstructuraTablas.COL_5_ENCUESTA,inff.getText().toString());
                            Operaciones_CRUD.insertar(db,contentValues,EncuestaActivity.this,EstructuraTablas.ENCUESTA_TABLA_NAME).show();
                            /*listaCarreras=Operaciones_CRUD.todosCarrera(db,listaEscuelas);
                            adapter.setL(listaCarreras);*/
                        }
                    }
                });

                d.setNegativeButton(R.string.cancelar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                d.setView(v);
                d.show();
            }
        });
    }

}
