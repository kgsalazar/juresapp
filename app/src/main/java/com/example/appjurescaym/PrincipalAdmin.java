package com.example.appjurescaym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PrincipalAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);
    }
    public void irPagina(View view){
        Intent intent = new Intent(this,NavegacionAdmin.class);
        intent.putExtra("DefaultFragment","Publicacion");
        startActivity(intent);
    }

    public void irActividades(View view){
        Intent intent = new Intent(this,NavegacionAdmin.class);
        intent.putExtra("DefaultFragment","Actividad");
        startActivity(intent);
    }

    public void irInscripciones(View view){
        Intent intent = new Intent(this,NavegacionAdmin.class);
        intent.putExtra("DefaultFragment","Inscripcion");
        startActivity(intent);
    }
}
