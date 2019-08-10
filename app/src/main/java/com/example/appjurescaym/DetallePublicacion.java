package com.example.appjurescaym;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallePublicacion extends AppCompatActivity {

    TextView tvTituloDetalle,tvCuerpoDetalle;
    ImageView imPublicacionDetalle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_publicacion);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //Inicializa la vista
        tvTituloDetalle=findViewById(R.id.tvTituloDetalle);
        tvCuerpoDetalle=findViewById(R.id.tvCuerpoDetalle);
        imPublicacionDetalle=findViewById(R.id.imPublicacionDetalle);

        //Obtiene los datos del intent anterior
        byte[]bytes=getIntent().getByteArrayExtra("imagen");
        String titulo=getIntent().getStringExtra("titulo");
        String cuerpo=getIntent().getStringExtra("cuerpo");
        //Convierte el arreglo de bytes en una imagen
        Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        //Setiamos los valores a las vistas
        tvTituloDetalle.setText(titulo);
        tvCuerpoDetalle.setText(cuerpo);
        imPublicacionDetalle.setImageBitmap(bmp);

    }

    //Permite devolverse al activty anterior

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
