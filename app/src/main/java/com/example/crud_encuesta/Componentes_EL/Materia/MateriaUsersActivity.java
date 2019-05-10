package com.example.crud_encuesta.Componentes_EL.Materia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

import java.util.ArrayList;

public class MateriaUsersActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseAccess access;
    ContentValues contentValues;
    ListView listView;
    MateriaAdapter adapter;
    ArrayList<Materia> listaMateria = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_users);

        LinearLayout l=findViewById(R.id.linearBusqueda);
        l.setVisibility(View.GONE);
    }
}
