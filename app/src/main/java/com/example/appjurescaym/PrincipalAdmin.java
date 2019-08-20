package com.example.appjurescaym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrincipalAdmin extends AppCompatActivity {

    // Este es el link para utilizar los servicios de autenticación.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);
        // Inicializa la comunicación el servicio de autenticación
        mAuth = FirebaseAuth.getInstance();
    }



    public void cerrar(View view) {
        mAuth.signOut();
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
