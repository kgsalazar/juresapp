package com.example.appjurescaym;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class DetallePublicacion extends AppCompatActivity {

    TextView tvTituloDetalle,tvCuerpoDetalle;
    ImageView imPublicacionDetalle;
    Button guardarBtn,compratirBtn,wallBtn;
    Bitmap bitmap;

    private static final int WRITE_EXTERNAL_STORAGE_CODE=1;
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
        guardarBtn=findViewById(R.id.guardarBtn);
        compratirBtn=findViewById(R.id.compartirBtn);
        wallBtn=findViewById(R.id.wallBtn);

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

        //Se obtiene la imagen desde el imageview como un bitmap
        bitmap=((BitmapDrawable)imPublicacionDetalle.getDrawable()).getBitmap();

        //donde se la da click en guardar
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si el sistema operativo es mayor o igual al marshmallow, se necesitan permisos para guardar la imagen
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                     String[] permisos={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                     //Muestra un popup solicitando los permisos
                        requestPermissions(permisos, WRITE_EXTERNAL_STORAGE_CODE);
                    }
                    else {
                        guardarImagen();
                    }
                }
                else{
                    //Sistema operativo es menor, se guarda la imagen sin necesidad de solicitar permisos en tiempo de ejecución
                    guardarImagen();
                }
            }
        });
        //donde se la da click en compartir
        compratirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compratirImagen();
            }
        });
        //donde se la da click en wall
        wallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImgWallpaper();
            }
        });

    }

    private void setImgWallpaper() {
        WallpaperManager myWallManager=WallpaperManager.getInstance(getApplicationContext());
        try{
            myWallManager.setBitmap(bitmap);
            Toast.makeText(this,"Wallpaper cambiando...",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this,"Hemos tenido problemas para cambiar el wallpaper",Toast.LENGTH_SHORT).show();
        }
    }

    private void compratirImagen() {
        try{
            //Se obtiene el titulo de la publicación y se guarda en una string
            String s=tvTituloDetalle.getText().toString()+"\n"+tvCuerpoDetalle.getText().toString();
            File file= new File(getExternalCacheDir(),"sample.png");
            FileOutputStream fsalida=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fsalida);
            fsalida.flush();
            fsalida.close();
            file.setReadable(true,false);
            //Intebt para compartir imagen y texto
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM,s);//Se manda el texto
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("imagen/png");
            startActivity(Intent.createChooser(intent,"Compartir vía"));



        }
        catch (Exception e){
            Toast.makeText(this,"Hemos tenido problemas al compartir la imagen",Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarImagen() {
        //Se obtiene el tiempo para el nombre de la imagen
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        //Ruta de imagen externa
        File path= Environment.getExternalStorageDirectory();
        //se crea carpeta llamada "Firebase"
        File dir=new File("/Firebase/");
        dir.mkdirs();
        //nombre de la imagen
        String nombreImagen=timeStamp+".PNG";
        File file= new File(dir,nombreImagen);
        OutputStream salida;
        try{
            salida=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,salida);
            salida.flush();
            salida.close();

            Toast.makeText(this,nombreImagen+" guardada en: "+dir,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Permite devolverse al activty anterior

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:
                //si el arreglo de la respuesta viene en blanco es que la petición fue cancelada o denegada
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //si el permiso es granted, se guarda la imagen
                    guardarImagen();
                }
                else {
                    //permisos denegados
                    Toast.makeText(this,"Permisos para guardar imagen han sido denegados",Toast.LENGTH_LONG).show();
                }
        }

    }
}
