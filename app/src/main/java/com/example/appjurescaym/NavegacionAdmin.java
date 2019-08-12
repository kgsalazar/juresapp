package com.example.appjurescaym;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NavegacionAdmin extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;



    {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        volverInicio();
                        return true;
                    case R.id.navigation_actividad:
                         irActividades();
                        return true;
                    case R.id.navigation_inscripcion:

                        return true;
                    case R.id.navigation_noticia:
                        // fragmentManager.beginTransaction().replace(R.id.contenedorAdm,new NoticiasAdm()).commit();
                        return true;
                    case R.id.navigation_joven:
                        return  true;
                }
                return false;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_admin);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        validaFragment();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public void volverInicio(){
        Intent intent=new Intent(this, PrincipalAdmin.class);
        startActivity(intent);
    }
    public void irActividades(){
        Intent intent=new Intent(this, ActividadesActivity.class);
        startActivity(intent);
    }
    public void validaFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(getIntent().getStringExtra("DefaultFragment").equals("Publicacion")){

            fragmentManager.beginTransaction().replace(R.id.contenedorAdm,new Publicacion()).commit();

        }

        if(getIntent().getStringExtra("DefaultFragment").equals("Actividad")){

            irActividades();

        }
    }

}
