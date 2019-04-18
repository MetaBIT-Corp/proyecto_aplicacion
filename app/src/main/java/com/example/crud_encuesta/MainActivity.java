package com.example.crud_encuesta;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    private Toolbar myTopToolBar;
    private ImageView loggin;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        myTopToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myTopToolBar);

        loggin = (ImageView)findViewById(R.id.log);
        loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed();
            }
        });
    }

    /*
    * Para acceder a la base de datos
    *
    * DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
    * databaseAccess.open();
    * .
    * .
    * .
    * your code
    * .
    * .
    * .
    * databaseAccess.close();
    */



    public void pressed(){
        Toast.makeText(MainActivity.this, "Has presionado el boton login", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),GpoEmpActivity.class);
        startActivity(i);
    }


}
