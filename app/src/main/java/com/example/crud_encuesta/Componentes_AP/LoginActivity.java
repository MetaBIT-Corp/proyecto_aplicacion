package com.example.crud_encuesta.Componentes_AP;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crud_encuesta.AreaActivity;
import com.example.crud_encuesta.DatabaseAccess;
import com.example.crud_encuesta.R;

public class LoginActivity extends AppCompatActivity {

    SQLiteDatabase baseDeDatos;
    TextInputLayout tvUsuario;
    TextInputLayout tvPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //enlazamos con el layout
        tvUsuario = (TextInputLayout) findViewById(R.id.ap_tiluser);
        tvPass = (TextInputLayout) findViewById(R.id.ap_tilpass);
        Button login = (Button) findViewById(R.id.ap_bt_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUsuario()){
                 //TODO:Intent a menu principal
                }
            }
        });
    }

    public Boolean isUsuario(){
        Boolean isUser = Boolean.FALSE;
        //guardamos en variables los datos que ha ingresado el usuario
        String usuario = tvUsuario.getEditText().getText().toString();
        String pass = tvPass.getEditText().getText().toString();

        if(!usuario.isEmpty()&&!pass.isEmpty()){
            //abrimos la base de datos
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            baseDeDatos = databaseAccess.open();

            //realizamos consulta   "Select * FROM USUARIO WHERE NOMUSUARIO= '" + usuario + "' AND CLAVE = '" +pass+"'"
            Cursor cursor = baseDeDatos.rawQuery(
                    "Select * FROM USUARIO WHERE NOMUSUARIO= '" + usuario + "' AND CLAVE = '" +pass+"'",
                    null
            );
            //validamos
            if(cursor.moveToFirst()){
                isUser = Boolean.TRUE;
                Toast.makeText(this,"Bienvenido " + usuario,Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Usuario y/o contraseña no válidos",Toast.LENGTH_SHORT).show();
            }

            //cerramos la base de datos
            databaseAccess.close();
        }else{
            Toast.makeText(this,"Debe llenar los campos usuario y contraseña",Toast.LENGTH_SHORT).show();
        }
        return isUser;
    }
}
