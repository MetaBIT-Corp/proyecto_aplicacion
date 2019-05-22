package com.example.crud_encuesta.Componentes_AP.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.Componentes_AP.Activities.PensumActivity;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOPensum;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOTurno;
import com.example.crud_encuesta.Componentes_AP.Models.Pensum;
import com.example.crud_encuesta.Componentes_AP.Models.Turno;
import com.example.crud_encuesta.Componentes_MR.Funciones;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class AdapterPensum  extends BaseAdapter {
    ArrayList<Pensum> pensums = new ArrayList<>();
    Pensum pensum;
    DAOPensum daoPensum;
    Activity activity; //la actividad donde va a mostrarse el listview
    Context context;

    //variables que nos ayudara a manejar los id de pensum y carrera
    int idPensum = 0;
    int idCarrera = 0;

    public AdapterPensum(ArrayList<Pensum> pensums, DAOPensum daoPensum,
                         Activity activity, Context context) {
        this.pensums = pensums;
        this.daoPensum = daoPensum;
        this.activity = activity;
        this.context = context;
    }

    public int getIdPensum() {
        return idPensum;
    }

    public void setIdPensum(int idPensum) {
        this.idPensum = idPensum;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }


    //sobreescritura de métodos
    @Override
    public int getCount() {
        return pensums.size();
    }

    @Override
    public Pensum getItem(int position) {
        return pensums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pensums.get(position).getIdPensum();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //retorna el view de una fila x del listview
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_formato, null); //inflamos la fila con el item
        }

        //vinculamos el arraylist de contactos con el listview
        pensum = pensums.get(position);


        TextView tv_item = (TextView) view.findViewById(R.id.ap_tv_item);
        ImageView editar = (ImageView) view.findViewById(R.id.ap_editar_item);
        ImageView eliminar = (ImageView) view.findViewById(R.id.ap_eliminar_item);
        ImageView info = (ImageView) view.findViewById(R.id.ap_info_item);
        ImageView addMateriaItem = (ImageView) view.findViewById(R.id.ap_turno_item);

        tv_item.setText(daoPensum.getNombreCarrera(pensum) + " - " +pensum.getAnio());

        //utilizamos setTag para que al presionar editar o eliminar, android sepa cuál registro queremos afectar
        editar.setTag(position);
        eliminar.setTag(position);

        //ocultamos
        info.setVisibility(view.INVISIBLE);
        addMateriaItem.setVisibility(view.INVISIBLE);

        //LISTENERS

        //INICIO de listeners Editar
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recuperamos la posicion del registro
                int pos = Integer.parseInt(v.getTag().toString());
                pensum = pensums.get(pos);
                setIdCarrera(pensum.getIdCarrera());
                setIdPensum(pensum.getIdPensum());
                //dialogo de crear

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//para quitar el titulo
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_pensum);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.show();

                //enlazamos
                final TextView titulo = (TextView) dialog.findViewById(R.id.ap_tv_title_dialogo_pensum);
                final EditText anio = (EditText) dialog.findViewById(R.id.ap_edt_anio_pensum);
                final Spinner carreras = (Spinner) dialog.findViewById(R.id.spinner_carrera_pensum);
                Button btnAnio = (Button) dialog.findViewById(R.id.btn_agregar_anio);
                Button btCrear = (Button) dialog.findViewById(R.id.d_agregar_pensum);
                Button btCancelar = (Button) dialog.findViewById(R.id.d_cancelar_pensum);

                titulo.setText(v.getResources().getString(R.string.mt_editar));
                anio.setText(""+pensum.getAnio());

                Funciones.setBtnAnio(dialog,btnAnio,anio); /*Seteando valor a vista de Año Título.*/

                //llenamos el spinner
                ArrayAdapter<CharSequence> adaptadorSpinner = new ArrayAdapter(
                        v.getContext(),
                        android.R.layout.simple_spinner_item,
                        daoPensum.SpinnerCarreras());
                carreras.setAdapter(adaptadorSpinner);

                //seteamos el spinner
                for (int i = 1; i < daoPensum.SpinnerCarreras().size(); i++) {
                    if (getIdCarrera() == Integer.parseInt(daoPensum.SpinnerCarreras().get(i).split(" ")[0])){
                        carreras.setSelection(i);
                    }
                }

                //seteamos la opcion seleccionado del spiner
                carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position > 0){
                            String[] carrera = daoPensum.SpinnerCarreras().get(position).split(" ");
                            setIdCarrera(Integer.parseInt(carrera[0]));
                        }else {
                            setIdCarrera(0);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!anio.getText().toString().isEmpty() && getIdCarrera() != 0) {
                            try {
                                pensum = new Pensum(
                                        getIdPensum(),
                                        getIdCarrera(),
                                        Integer.parseInt(anio.getText().toString())

                                );
                                //creamos registro
                                /*
                                if (daoPensum.Editar(pensum)){
                                    Toast.makeText(v.getContext(),"Se guardó con éxito",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(v.getContext(),"Fallo al guardar, intente de nuevo",Toast.LENGTH_SHORT).show();
                                }*/
                                daoPensum.Editar(pensum);
                                //refrescamos la lista
                                pensums = daoPensum.verTodos();
                                notifyDataSetChanged();
                                //cerramos el dialogo
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(
                                    v.getContext(),
                                    v.getResources().getString(R.string.ap_llena_todos_los_campos),
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
        //FINAL de listeners Editar

        //INICIO de listeners eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogo de eliminar

                //recuperamos la posicion del registro
                int pos = Integer.parseInt(v.getTag().toString());

                //recuperamos el contacto de la lista y seteamos id
                pensum = pensums.get(pos);
                setIdPensum(pensum.getIdPensum());

                //creamos Alertdialogo

                AlertDialog.Builder delete_emergente = new AlertDialog.Builder(activity);
                delete_emergente.setMessage(v.getResources().getString(R.string.ap_delete_pensum));
                delete_emergente.setCancelable(true);

                //Caso positivo

                delete_emergente.setPositiveButton(v.getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //elimina el registro, actualiza la lista y notifica el cambio al adaptador
                        daoPensum.Eliminar(getIdPensum());
                        pensums = daoPensum.verTodos();
                        notifyDataSetChanged();

                    }
                });

                //Caso negativo

                delete_emergente.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // no esperamos que haga nada al cerrar, solo se cierra
                    }
                });
                delete_emergente.show(); //mostrar alerta

            }
        });
        //FINAL de listener eliminar


        return view;
    }
}
