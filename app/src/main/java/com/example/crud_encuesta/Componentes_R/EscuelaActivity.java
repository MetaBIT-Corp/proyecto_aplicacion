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
                final AlertDialog.Builder d=new AlertDialog.Builder(EscuelaActivity.this);

                View v=getLayoutInflater().inflate(R.layout.fragment_escuela, null);

                final EditText nom=v.findViewById(R.id.in_nom_escuela);

                d.setPositiveButton(R.string.agregar_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nom.getText().toString().isEmpty())Toast.makeText(EscuelaActivity.this,"Campos Vacios.",Toast.LENGTH_SHORT).show();
                        else{
                            access=DatabaseAccess.getInstance(EscuelaActivity.this);
                            db=access.open();
                            contentValues=new ContentValues();
                            contentValues.put(Estructura_Escuela.COL_1,nom.getText().toString());
                            Operaciones_CRUD.insertar(db,contentValues,EscuelaActivity.this,Estructura_Escuela.ESCUELA_TABLA_NAME).show();
                            db.close();
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
