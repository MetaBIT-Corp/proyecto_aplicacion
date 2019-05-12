package com.example.crud_encuesta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crud_encuesta.Componentes_AP.Activities.EvaluacionActivity;
import com.example.crud_encuesta.Componentes_AP.Models.Evaluacion;
import com.example.crud_encuesta.Componentes_MT.Area.AreaActivity;

public class SubMenuMateriaActivity extends AppCompatActivity {

    int iduser;
    int rol;
    int idMat;
    String nomMat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu_materia);

        CardView cardView=findViewById(R.id.cardArea);

        ImageView btnArea=findViewById(R.id.btnArea);
        ImageView btnEva=findViewById(R.id.btnEva);
        TextView tx=findViewById(R.id.el_lbl_Mat);

        //Recuperacion de valores
        nomMat=getIntent().getExtras().getString("nom_materia");
        idMat=getIntent().getExtras().getInt("id_materia");
        rol=getIntent().getExtras().getInt("rol_user");
        iduser=getIntent().getExtras().getInt("id_user");

        tx.setText(nomMat);

        if (rol==2){
            cardView.setVisibility(View.GONE);
        }

        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SubMenuMateriaActivity.this, AreaActivity.class);
                i.putExtra("id_materia",idMat);
                startActivity(i);
                finish();
            }
        });

        btnEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SubMenuMateriaActivity.this, EvaluacionActivity.class);
                i.putExtra("id_materia",idMat);
                startActivity(i);
                finish();
            }
        });

    }
}
