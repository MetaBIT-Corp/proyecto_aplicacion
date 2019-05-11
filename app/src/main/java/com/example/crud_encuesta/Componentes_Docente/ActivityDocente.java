package com.example.crud_encuesta.Componentes_Docente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.crud_encuesta.Componentes_AP.Models.Usuario;
import com.example.crud_encuesta.Componentes_EL.Escuela.Escuela;
import com.example.crud_encuesta.Componentes_EL.Operaciones_CRUD;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityDocente extends AppCompatActivity {

    private DAODocente dao;
    private AdaptadorDocente adapter;
    private ArrayList<Docente> lista;
    private Docente docente;
    private Usuario usuario;
    private SQLiteDatabase db;
    private DatabaseAccess access;
    private String tableName = "ESCUELA";
    private ArrayList<Escuela> escuelas = new ArrayList<Escuela>();
    private ArrayList<String> listaEscuelas = new ArrayList<String>();
    private int anio = Calendar.getInstance().get(Calendar.YEAR);
    private int id_escuela;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        dao = new DAODocente(this);
        lista = dao.verTodos();

        adapter = new AdaptadorDocente(this,lista,dao);
        access=DatabaseAccess.getInstance(ActivityDocente.this);
        db=access.open();

        escuelas=Operaciones_CRUD.todosEscuela(tableName,db);
        listaEscuelas = obtenerListaEscuelas();

        ListView list = (ListView) findViewById(R.id.lista_docente);
        Button agregar = (Button) findViewById(R.id.btn_nuevo_docente);

        if((lista != null) && (lista.size() > 0)){
            list.setAdapter(adapter);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialogo =new Dialog(ActivityDocente.this);
                dialogo.setTitle("Registro de Docente");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo_docente);
                dialogo.show();
                dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final Spinner sp_escuela = (Spinner) dialogo.findViewById(R.id.sp_escuela);
                final EditText carnet = (EditText) dialogo.findViewById(R.id.editt_carnet);
                final EditText anio_titulo = (EditText) dialogo.findViewById(R.id.editt_anio_titulo);
                final CheckBox activo = (CheckBox) dialogo.findViewById(R.id.cb_actividad);
                final EditText tipo_jornada = (EditText) dialogo.findViewById(R.id.editt_tipo_jornada);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.editt_descripcion);
                final EditText cargo_actual = (EditText) dialogo.findViewById(R.id.editt_cargo_actual);
                final EditText cargo_secundario = (EditText) dialogo.findViewById(R.id.editt_cargo_secundario);
                final EditText nombre = (EditText) dialogo.findViewById(R.id.editt_nombre);
                Button btn_anio = (Button) dialogo.findViewById(R.id.btn_agregar_anio);
                final TextView mensaje = (TextView) dialogo.findViewById(R.id.toolbar_docente);
                mensaje.setText("Registrar Nuevo Docente");

                ArrayAdapter adapterEs = new ArrayAdapter(ActivityDocente.this, android.R.layout.simple_list_item_1, listaEscuelas);
                sp_escuela.setAdapter(adapterEs);

                sp_escuela.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0){
                            id_escuela = escuelas.get(position).getId();
                        } else {
                            id_escuela = 1;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                btn_anio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog d = new Dialog(ActivityDocente.this);
                        d.setTitle("Year Picker");
                        d.setContentView(R.layout.year_picker);
                        Button set = (Button) d.findViewById(R.id.button1);
                        Button cancel = (Button) d.findViewById(R.id.button2);
                        TextView year_text=(TextView)d.findViewById(R.id.year_text);
                        year_text.setText(""+anio);
                        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

                        nopicker.setMaxValue(anio+50);
                        nopicker.setMinValue(anio-50);
                        nopicker.setWrapSelectorWheel(false);
                        nopicker.setValue(anio);
                        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                        set.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                anio_titulo.setText(String.valueOf(nopicker.getValue()));
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
                        d.getWindow().setLayout(((getWidth(d.getContext()) / 100) * 75), ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                });

                Button guardar =(Button) dialogo.findViewById(R.id.btn_agregar_dcn);
                guardar.setText("Registrar");
                Button cancelar = (Button) dialogo.findViewById(R.id.btn_cancelar_dcn);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            int checki;
                            if(activo.isChecked()){
                                checki = 1;
                            }else{
                                checki = 0;
                            }

                            usuario = new Usuario(
                                    carnet.getText().toString(),
                                    carnet.getText().toString(),
                                    1
                            );

                            docente = new Docente(
                                    id_escuela,
                                    carnet.getText().toString().trim(),
                                    anio_titulo.getText().toString(),
                                    checki,
                                    Integer.parseInt(tipo_jornada.getText().toString()),
                                    descripcion.getText().toString(),
                                    Integer.parseInt(cargo_actual.getText().toString()),
                                    Integer.parseInt(cargo_secundario.getText().toString()),
                                    nombre.getText().toString(),
                                    usuario.getIDUSUARIO());

                            dao.insertar(docente);
                            dao.insertarUsuario(usuario);

                            final AlertDialog.Builder usrAlert= new AlertDialog.Builder(ActivityDocente.this);
                            int clave_tamanio = usuario.getCLAVE().length();
                            String astericos ="";
                            for(int i=0;i<clave_tamanio-2;i++){
                                astericos+="*";
                            }
                            String clave_formateada=(usuario.getCLAVE().substring(0,2)+astericos);
                            usrAlert.setMessage("Usuario de Docente creado:\n\n"+"Usuario: "+usuario.getNOMUSUARIO()+"\nClave: "+clave_formateada);

                            usrAlert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            });

                            usrAlert.show();

                            adapter.notifyDataSetChanged();
                            lista = dao.verTodos();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "¡Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });
    }

    public ArrayList<String> obtenerListaEscuelas() {
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