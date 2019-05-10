package com.example.crud_encuesta.Componentes_MT.Area;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;
import java.util.List;

public class AreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DAOArea daoArea;
    List<Area> areas = new ArrayList<>();
    AreaAdapter areaAdapter;

    private Spinner spItems;
    private ArrayAdapter<String> comboAdapter;
    private List <String> items;
    private int[] iconos={R.drawable.edit_, R.drawable.delete_};

    FloatingActionButton fabArea;
    private EditText mArea;
    private ListView listView;

    //Datos de otros modelos
    private int seleccion_item=0;
    private int id_cat_mat = 1;
    private int id_pdg_dcn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        daoArea = new DAOArea(this);
        areas = daoArea.getAreas(id_cat_mat);
        areaAdapter = new AreaAdapter(this, areas, daoArea, id_cat_mat, iconos);

        //cargarItems();
        listView = (ListView)findViewById(R.id.list_areas);
        listView.setAdapter(areaAdapter);

        fabArea = (FloatingActionButton)findViewById(R.id.fabArea);
        fabArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar_area();
            }
        });

    }

    public void cargarItems(View v){
        spItems = (Spinner) v.findViewById(R.id.spModalidad);
        spItems.setOnItemSelectedListener(this);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        SQLiteDatabase db = databaseAccess.open();

        items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre_tipo_item FROM tipo_item", null);

        while (cursor.moveToNext()) {
            items.add(cursor.getString(0));
        }
        cursor.close();

        databaseAccess.close();

        comboAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        spItems.setAdapter(comboAdapter);
    }

    public void agregar_area(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AreaActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialogo_area, null);

        cargarItems(mView);

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.mt_agregar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mArea = (EditText)mView.findViewById(R.id.etArea);

                String titulo_area = mArea.getText().toString();

                if(!titulo_area.isEmpty()){
                    Area area = new Area(titulo_area, id_cat_mat, id_pdg_dcn, seleccion_item);
                    daoArea.insertar(area);
                    areas = daoArea.getAreas(id_cat_mat);
                    areaAdapter.notifyDataSetChanged();
                    Toast.makeText(AreaActivity.this, getString(R.string.mt_area_agregada), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AreaActivity.this, getString(R.string.mt_campos_vacios), Toast.LENGTH_SHORT).show();
                }
                refresh();
            }
        });

        mBuilder.setNegativeButton(R.string.mt_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spModalidad:

                seleccion_item = position+1;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void refresh(){
        listView.setAdapter(new AreaAdapter(this, areas, daoArea, id_cat_mat, iconos));
    }



}
