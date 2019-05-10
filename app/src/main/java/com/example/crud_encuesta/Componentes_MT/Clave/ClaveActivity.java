package com.example.crud_encuesta.Componentes_MT.Clave;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class ClaveActivity extends AppCompatActivity {
    DAOClave daoClave;
    ClaveAdapter claveAdapter;
    List<Clave> claves = new ArrayList<>();

    private FloatingActionButton fabClave;
    private ListView listView;
    private int[] iconos = {R.drawable.infoazul, R.drawable.addgris, R.drawable.edit_, R.drawable.delete_};

    //Datos de modelos
    private int id_encuesta = 1;
    private int id_turno = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave);

        daoClave = new DAOClave(this);
        claves = daoClave.getClaves();
        claveAdapter = new ClaveAdapter(this, claves, daoClave, iconos);

        listView = (ListView)findViewById(R.id.list_claves);
        listView.setAdapter(claveAdapter);

        fabClave = (FloatingActionButton)findViewById(R.id.fabClave);
        fabClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                claveDialog();
            }
        });
    }

    public void agregar_clave(){
        int clave_nueva;
        int tamanio=claves.size();

        if(tamanio==0){
            clave_nueva = 1;
        }else{
            clave_nueva = Integer.parseInt(claves.get(tamanio-1).numero_clave);
            clave_nueva++;

        }

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();
        ContentValues registro = new ContentValues();

        registro.put("id_encuesta", id_encuesta);
        registro.put("id_turno", id_turno);
        registro.put("numero_clave", clave_nueva);

        db.insert("clave", null, registro);
        databaseAccess.close();

    }

    public void claveDialog(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setCancelable(false);
        dialogo.setIcon(R.drawable.infoazul);
        dialogo.setTitle(R.string.mt_agregar);
        dialogo.setMessage(R.string.mt_agregar_clave);

        dialogo.setPositiveButton(R.string.mt_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agregar_clave();
                claves = daoClave.getClaves();
                claveAdapter.notifyDataSetChanged();
                refresh();
                Toast.makeText(ClaveActivity.this, getString(R.string.mt_clave_agregada), Toast.LENGTH_SHORT).show();
            }
        });

        dialogo.setNegativeButton(R.string.mt_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ventana = dialogo.create();
        ventana.show();

    }

    public void refresh(){
        listView.setAdapter(new ClaveAdapter(this, claves, daoClave, iconos));
    }

/*public List<Clave> getClaves(){
        List<Clave> claves = new ArrayList<>();
        int id;
        String num_clave;

        DatabaseAccess database = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = database.open();

        Cursor cursor = db.rawQuery("SELECT id_clave, numero_clave FROM clave", null);

        while (cursor.moveToNext()){
            id = cursor.getInt(0);
            num_clave = cursor.getString(1);

            claves.add(new Clave(id, num_clave));
        }

        database.close();

        return claves;
    }*/


}
