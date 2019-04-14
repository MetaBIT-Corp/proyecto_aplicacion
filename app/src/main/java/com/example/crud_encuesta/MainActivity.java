package com.example.crud_encuesta;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar myTopToolBar;
    private ImageView loggin;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



    public void pressed(){
        Toast.makeText(MainActivity.this, "Has presionado el boton login", Toast.LENGTH_SHORT).show();
    }


}
