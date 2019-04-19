package com.example.crud_encuesta.Componentes_R;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

public class EscuelaActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseAccess access;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuela);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d=new Dialog(EscuelaActivity.this);
                d.setContentView(R.layout.fragment_escuela);
                Button agregar=d.findViewById(R.id.btn_agregar_escuela);
                Button cancel=d.findViewById(R.id.btn_cancelar_escuela);
                final EditText nom=d.findViewById(R.id.in_nom_escuela);


                agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nom.getText().toString().isEmpty())Toast.makeText(EscuelaActivity.this,"Campos Vacios.",Toast.LENGTH_SHORT).show();
                        else{
                            access=DatabaseAccess.getInstance(EscuelaActivity.this);
                            db=access.open();
                            contentValues=new ContentValues();
                            contentValues.put(Estructura_Escuela.COL_1,nom.getText().toString());
                            Operaciones_CRUD.insertar(db,contentValues,EscuelaActivity.this,Estructura_Escuela.ESCUELA_TABLA_NAME).show();
                            d.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.show();
            }
        });
    }

}
