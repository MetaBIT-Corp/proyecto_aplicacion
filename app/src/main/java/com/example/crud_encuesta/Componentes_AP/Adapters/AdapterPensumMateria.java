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

import com.example.crud_encuesta.Componentes_AP.DAO.DAOPensum;
import com.example.crud_encuesta.Componentes_AP.DAO.DAOPensumMateria;
import com.example.crud_encuesta.Componentes_AP.Models.Pensum;
import com.example.crud_encuesta.Componentes_AP.Models.PensumMateria;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class AdapterPensumMateria extends BaseAdapter {
    ArrayList<PensumMateria> pensumMaterias = new ArrayList<>();
    PensumMateria pensumMateria;
    DAOPensumMateria daoPensumMateria;
    Activity activity; //la actividad donde va a mostrarse el listview
    Context context;

    //variables que nos ayudara a manejar los id de pensum y carrera
    int idPensumMateria = 0;
    int idPensum = 0;
    int id_materia = 0;
    int id_materia_aux = 0;



    public AdapterPensumMateria(ArrayList<PensumMateria> pensumMaterias, DAOPensumMateria daoPensumMateria,
                                Activity activity, Context context) {
        this.pensumMaterias = pensumMaterias;
        this.daoPensumMateria = daoPensumMateria;
        this.activity = activity;
        this.context = context;
    }

    public int getId_materia_aux() {
        return id_materia_aux;
    }

    public void setId_materia_aux(int id_materia_aux) {
        this.id_materia_aux = id_materia_aux;
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    public int getIdPensumMateria() {
        return idPensumMateria;
    }

    public void setIdPensumMateria(int idPensumMateria) {
        this.idPensumMateria = idPensumMateria;
    }
    public int getIdPensum() {
        return idPensum;
    }

    public void setIdPensum(int idPensum) {
        this.idPensum = idPensum;
    }



    @Override
    public int getCount() {
        return pensumMaterias.size();
    }

    @Override
    public PensumMateria getItem(int position) {
        return pensumMaterias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pensumMaterias.get(position).getIdPensumMateria();
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
        pensumMateria = pensumMaterias.get(position);


        TextView tv_item = (TextView) view.findViewById(R.id.ap_tv_item);
        ImageView editar = (ImageView) view.findViewById(R.id.ap_editar_item);
        ImageView eliminar = (ImageView) view.findViewById(R.id.ap_eliminar_item);
        ImageView info = (ImageView) view.findViewById(R.id.ap_info_item);
        ImageView addMateriaItem = (ImageView) view.findViewById(R.id.ap_turno_item);

        tv_item.setText(daoPensumMateria.getNombreMateria(pensumMateria));

        //utilizamos setTag para que al presionar eliminar, android sepa cuál registro queremos afectar
        eliminar.setTag(position);
        editar.setTag(position);

        //ocultamos
        info.setVisibility(view.INVISIBLE);
        addMateriaItem.setVisibility(view.INVISIBLE);

        //LISTENERS

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperamos la posicion del registro
                int pos = Integer.parseInt(v.getTag().toString());
                pensumMateria = pensumMaterias.get(pos);
                setIdPensumMateria(pensumMateria.getIdPensumMateria());
                setIdPensum(pensumMateria.getIdPensum());
                setId_materia(pensumMateria.getIdMateria());
                setId_materia_aux(pensumMateria.getIdMateria());
                //dialogo de crear

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//para quitar el titulo
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo_pensum_materia);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.show();

                final TextView titulo = (TextView) dialog.findViewById(R.id.ap_tv_title_dialogo_pensum_materia);
                final Spinner materias = (Spinner) dialog.findViewById(R.id.spinner_materia_pensum);
                Button btCrear = (Button) dialog.findViewById(R.id.d_agregar_pensum_materia);
                Button btCancelar = (Button) dialog.findViewById(R.id.d_cancelar_pensum_materia);

                titulo.setText(v.getResources().getString(R.string.mt_editar));

                //llenamos el spinner
                ArrayAdapter<CharSequence> adaptadorSpinner = new ArrayAdapter(
                        v.getContext(),
                        android.R.layout.simple_spinner_item,
                        daoPensumMateria.SpinnerMateriasEditar(getIdPensum(), getId_materia()));
                materias.setAdapter(adaptadorSpinner);

                //seteamos el spinner
                for (int i = 1; i < daoPensumMateria.SpinnerMateriasEditar(getIdPensum(),getId_materia()).size(); i++) {
                    if (getId_materia() == Integer.parseInt(daoPensumMateria.SpinnerMateriasEditar(getIdPensum(),getId_materia()).get(i).split(" ")[0])){
                        materias.setSelection(i);
                    }
                }

                //seteamos la opcion seleccionado del spiner
                materias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position > 0){
                            String[] materia = daoPensumMateria.SpinnerMateriasEditar(getIdPensum(),getId_materia()).get(position).split(" ");
                            setId_materia_aux(Integer.parseInt(materia[0]));
                        }else{
                            setId_materia_aux(0);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btCrear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getId_materia_aux() != 0) {
                            try {
                                pensumMateria = new PensumMateria(
                                        getIdPensumMateria(),
                                        getId_materia_aux(),
                                        getIdPensum()
                                );
                                //creamos registro
                                /*
                                if (daoPensumMateria.Editar(pensumMateria)){
                                    Toast.makeText(v.getContext(),"Se guardó con éxito",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(v.getContext(),"Fallo al guardar, intente de nuevo",Toast.LENGTH_SHORT).show();
                                }*/
                                daoPensumMateria.Editar(pensumMateria);
                                //refrescamos la lista
                                pensumMaterias = daoPensumMateria.verTodos(getIdPensum());
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

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperamos la posicion del registro
                int pos = Integer.parseInt(v.getTag().toString());

                //recuperamos el contacto de la lista y seteamos id
                pensumMateria = pensumMaterias.get(pos);
                setIdPensumMateria(pensumMateria.getIdPensumMateria());
                setIdPensum(pensumMateria.getIdPensum());

                //creamos Alertdialogo

                AlertDialog.Builder delete_emergente = new AlertDialog.Builder(activity);
                delete_emergente.setMessage(v.getResources().getString(R.string.ap_desea01));
                delete_emergente.setCancelable(true);

                //Caso positivo

                delete_emergente.setPositiveButton(v.getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //elimina el registro, actualiza la lista y notifica el cambio al adaptador
                        daoPensumMateria.Eliminar(getIdPensumMateria());
                        pensumMaterias = daoPensumMateria.verTodos(getIdPensum());
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

        return view;
    }
}
