package com.example.appjurescaym;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appjurescaym.model.PublicacionModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class Publicar extends Fragment {

    EditText tvAgregarTitulo,tvAgregarCuerpo;
    ImageView ivPublicacion;
    Button btnPublicar;
    //Carpeta en el Firebase Storage
    String mStoragePath="ImagenesCargadas/";
    //Rama en la base de Datos
    String mDatabasePath="Publicacion";
    //Creacion de la Uri
    Uri mFliePathUri;
    //Declara las referencias del Storage y realtime
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    //Un progress dialog
    ProgressDialog mProgressDialog;

    //codigo de respuesta al escoger la imagen.
    int IMAGE_REQUEST_CODE=5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_publicar, container, false);
        tvAgregarTitulo=view.findViewById(R.id.tvAgregaTitulo);
        tvAgregarCuerpo=view.findViewById(R.id.tvAgregaCuerpo);
        ivPublicacion=view.findViewById(R.id.ivPublicacion);
        btnPublicar=view.findViewById(R.id.btnPublicar);

        //Se le asigna evento click a la imagen para escoger otra.
        ivPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"),IMAGE_REQUEST_CODE);
            }
        });
        //Cuando se le da click al boton de publicar (Se procede a subir la info a la BD)
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se llama el metodo para subir info a la DB
                subirDatosFirebase();
            }
        });
        //Se asigna la instancia de firebase al objeto de storage
        mStorageReference= FirebaseStorage.getInstance().getReference();
        //Se asigna la instancia de firebase al padre de la rama de BD
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(mDatabasePath);
        //Inicializamos el progresDialog
        mProgressDialog=new ProgressDialog(view.getContext());
        return view;


    }

    private void subirDatosFirebase() {
        //Se valida que el filepath esta vacia o no
        if(mFliePathUri!=null){
            mProgressDialog.setTitle("Cargando imagen...");
            mProgressDialog.show();
            StorageReference storageReference2nd =mStorageReference.child(mStoragePath+System.currentTimeMillis()+"."+getExtensionArchivo(mFliePathUri));

            //Agrega un addOnSuccessListener al storage2
            storageReference2nd.putFile(mFliePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String titulo=tvAgregarTitulo.getText().toString().trim();
                    String cuerpo=tvAgregarCuerpo.getText().toString().trim();
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(),"Imagen cargada con éxito",Toast.LENGTH_SHORT).show();
                    String prueba=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    PublicacionModel publicacion=new PublicacionModel(titulo,cuerpo,taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    //Se genera el id de la publicacion

                    String id=mDatabaseReference.push().getKey();
                    //Se asigna el id de la rama hija en la BD
                    mDatabaseReference.child(id).setValue(publicacion);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.setTitle("Cargando Imagen....");
                }
            });
        }
        else {
            Toast.makeText(getContext(),"Por favor, seleccionar una imagen",Toast.LENGTH_LONG).show();
        }
    }
    //Metodo obtiene la extension de la imagen seleccionada
    private String getExtensionArchivo(Uri uri) {
        ContentResolver contentResolver=getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        //Retorna la extension
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST_CODE && resultCode== RESULT_OK
                &&data!=null && data.getData()!=null){
            mFliePathUri=data.getData();

            try{
                //Se obtiene la imagen seleccionada en un bitmap
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),mFliePathUri);
                //Se le asigna el bitmap al ImageView
                ivPublicacion.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
