package com.example.crud_encuesta.Componentes_EL.Encuesta;

import com.example.crud_encuesta.Componentes_MR.Docente.Docente;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_EL.EstructuraTablas;
import com.example.crud_encuesta.Componentes_EL.Operaciones_CRUD;
import com.example.crud_encuesta.Componentes_MT.Intento.IntentoActivity;
import com.example.crud_encuesta.R;
import com.example.crud_encuesta.SubMenuEncuestaActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class EncuestaAdapter extends BaseAdapter {
    Context context;
    ArrayList<Encuesta> l;
    ArrayList<Docente> listaDocentes;
    SQLiteDatabase db;
    Activity activity;
    int dia,mes,año,ho,min;
    boolean seg;

    int di,df,mi,mf,ai,af,hi,hf;
    String cadenai = null;
    String cadenaf=null;

    int rol;
    int iduser;

    public EncuestaAdapter(Context c, ArrayList<Encuesta> lista, SQLiteDatabase db, Activity a, ArrayList<Docente> e,int id,int rol) {
        this.context = c;
        this.l = lista;
        this.db = db;
        this.activity = a;
        this.listaDocentes = e;
        this.rol=rol;
        this.iduser=id;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public Encuesta getItem(int position) {
        return l.get(position);
    }

    @Override
    public long getItemId(int position) {
        return l.get(position).getId();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_escuela, null);
        }

        final Button btneditar = view.findViewById(R.id.btn_editar);
        Button btneliminar = view.findViewById(R.id.btn_eliminar);
        Button btninfor=view.findViewById(R.id.btn_infor);

        TextView textView = view.findViewById(R.id.txt_escuela_item);
        textView.setText(l.get(position).toString());

        if (rol==0||rol==2){
            btneditar.setVisibility(View.GONE);
            btneliminar.setVisibility(View.GONE);
        }

        if (rol==1){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, SubMenuEncuestaActivity.class);
                    i.putExtra("id_encuesta",(int)getItemId(position));
                    context.startActivity(i);
                }
            });
        }

        if(rol==2){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, IntentoActivity.class);
                    i.putExtra("id_encuesta",(int)getItemId(position));
                    context.startActivity(i);

                }
            });
        }



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
                        Operaciones_CRUD.eliminar(db, context, EstructuraTablas.ENCUESTA_TABLA_NAME, EstructuraTablas.COL_0_ENCUESTA, id);
                        l.clear();
                        if (rol==0||rol==2){
                            setL(Operaciones_CRUD.todosEncuesta(db,listaDocentes));
                        }

                        if (rol==1){
                            setL(Operaciones_CRUD.todosEncuesta(db, listaDocentes,iduser));
                        }

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
                final Encuesta c = getItem(position);
                final AlertDialog.Builder d = new AlertDialog.Builder(context);

                View view = inflater.inflate(R.layout.dialogo_encuesta, null);

                final EditText infi=view.findViewById(R.id.in_fecha_inicial);
                final EditText inff=view.findViewById(R.id.in_fecha_final);
                final EditText nom=view.findViewById(R.id.in_nom_encuesta);
                final EditText desc=view.findViewById(R.id.in_descrip_encuesta);
                Button btnfi=view.findViewById(R.id.btn_fecha_inicio);
                Button btnff=view.findViewById(R.id.btn_fecha_final);

                nom.setText(c.getTitulo());
                desc.setText(c.getDescripcion());
                infi.setText(c.getFecha_in());
                inff.setText(c.getFecha_fin());


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

                        DatePickerDialog calendar=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                ai=year;
                                mi=month;
                                di=dayOfMonth;
                                cadenai=year+"-"+month+"-"+dayOfMonth+" ";

                                TimePickerDialog hora=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        hi=hourOfDay;
                                        infi.setText(cadenai+hourOfDay+":"+minute);
                                    }
                                },ho,min,seg);
                                hora.setTitle(R.string.men_hora_in);
                                hora.show();
                            }
                        },año,mes,dia);
                        calendar.setTitle(R.string.men_fecha_in);
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

                        DatePickerDialog calendar=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                af=year;
                                mf=month;
                                df=dayOfMonth;
                                cadenaf=year+"-"+month+"-"+dayOfMonth+" ";

                                TimePickerDialog hora=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        hf=hourOfDay;
                                        inff.setText(cadenaf+hourOfDay+":"+minute);

                                        if ((di>df&&mi>=mf)||mi>mf||ai>af||(di==df&&hi>=hf)||(hi==12&&hf>=12)){
                                            Toast.makeText(context,R.string.men_fecha_error,Toast.LENGTH_SHORT).show();
                                            infi.setText("aa-mm-dd");
                                            inff.setText("aa-mm-dd");
                                        }
                                    }
                                },ho,min,seg);
                                hora.setTitle(R.string.men_hora_fi);
                                hora.show();

                            }
                        },año,mes,dia);
                        calendar.setTitle(R.string.men_fecha_fi);
                        calendar.show();
                    }
                });



                d.setPositiveButton(R.string.actualizar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(nom.getText().toString().isEmpty()|| desc.getText().toString().isEmpty() || infi.getText().toString().equals("dd/mm/aa") || inff.getText().toString().equals("dd/mm/aa"))
                            Toast.makeText(context,R.string.men_camp_vacios,Toast.LENGTH_SHORT).show();
                        else{
                            ContentValues contentValues=new ContentValues();
                            contentValues.put(EstructuraTablas.COL_1_ENCUESTA,Operaciones_CRUD.docenteEncuesta(db,iduser));
                            contentValues.put(EstructuraTablas.COL_2_ENCUESTA,nom.getText().toString());
                            contentValues.put(EstructuraTablas.COL_3_ENCUESTA,desc.getText().toString());
                            contentValues.put(EstructuraTablas.COL_4_ENCUESTA,infi.getText().toString());
                            contentValues.put(EstructuraTablas.COL_5_ENCUESTA,inff.getText().toString());
                            Operaciones_CRUD.actualizar(db,contentValues,context,EstructuraTablas.ENCUESTA_TABLA_NAME,EstructuraTablas.COL_0_ENCUESTA,id).show();
                            if (rol==0||rol==2){
                                l=Operaciones_CRUD.todosEncuesta(db,listaDocentes);
                            }

                            if (rol==1){
                                l=Operaciones_CRUD.todosEncuesta(db, listaDocentes,iduser);
                            }

                            setL(l);
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

        btninfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = (int) getItemId(position);
                final Encuesta c = getItem(position);
                final AlertDialog.Builder d = new AlertDialog.Builder(context);

                View view = inflater.inflate(R.layout.dialogo_encuesta, null);

                final EditText infi=view.findViewById(R.id.in_fecha_inicial);
                final EditText inff=view.findViewById(R.id.in_fecha_final);
                final EditText nom=view.findViewById(R.id.in_nom_encuesta);
                final EditText desc=view.findViewById(R.id.in_descrip_encuesta);
                Button btnfi=view.findViewById(R.id.btn_fecha_inicio);
                Button btnff=view.findViewById(R.id.btn_fecha_final);

                nom.setText(c.getTitulo());
                desc.setText(c.getDescripcion());
                infi.setText(c.getFecha_in());
                inff.setText(c.getFecha_fin());
                nom.setEnabled(false);
                desc.setEnabled(false);
                inff.setEnabled(false);
                infi.setEnabled(false);

                d.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
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
    public void setL(ArrayList<Encuesta> l) {
        this.l = l;
        notifyDataSetChanged();
    }
}
