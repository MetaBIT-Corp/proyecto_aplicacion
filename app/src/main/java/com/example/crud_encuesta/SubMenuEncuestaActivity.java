package com.example.crud_encuesta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.crud_encuesta.Componentes_MT.Area.AreaActivity;
import com.example.crud_encuesta.Componentes_MT.Clave.Clave;
import com.example.crud_encuesta.Componentes_MT.Clave.ClaveActivity;

public class SubMenuEncuestaActivity extends AppCompatActivity {
    int idEncuesta;
    ImageView btnArea,btnClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu_encuesta);
        idEncuesta=getIntent().getExtras().getInt("id_encuesta");

        btnArea=findViewById(R.id.btnArea);
        btnClave=findViewById(R.id.btnClave);

        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SubMenuEncuestaActivity.this, AreaActivity.class);
                i.putExtra("id_encuesta",idEncuesta);
                startActivity(i);
            }
        });
        btnClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SubMenuEncuestaActivity.this, ClaveActivity.class);
                i.putExtra("id_encuesta",idEncuesta);
                startActivity(i);
            }
        });

    }
}
