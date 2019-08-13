package com.example.appjurescaym;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appjurescaym.model.PublicacionModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;


public class Publicacion extends Fragment {
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FloatingActionButton fbtnAgregarPubl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_publicacion, container,false);
        //RecyclerView
        mRecyclerView= view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set layout como LinearLayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference("Publicacion");

        fbtnAgregarPubl=view.findViewById(R.id.fbtnAgregarPubl);
       fbtnAgregarPubl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), CreaPublicacion.class);
               startActivity(intent);
           }
       });
        String usuario="kgsalazarc@ccss.sa.cr";
        if(usuario.equals("keivin97salazar@gmail.com")){
            fbtnAgregarPubl.hide();
        }
        return view;

    }
    //Buscar información

    private void buscarFirebase(String buscarTexto){
        Query firebaseSearchQuery=mRef.orderByChild("titulo").startAt(buscarTexto).endAt(buscarTexto+"\uf8ff");
        FirebaseRecyclerAdapter<PublicacionModel,RowHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<PublicacionModel, RowHolder>(
                        PublicacionModel.class,
                        R.layout.row,
                        RowHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(RowHolder viewHolder, PublicacionModel model, int position) {
                        viewHolder.setDetalles(getContext(),model.getTitulo(),model.getCuerpo(),model.getImagen());
                    }

                    @Override
                    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        RowHolder rowHolder = super.onCreateViewHolder(parent, viewType);
                        rowHolder.setOnClickListener(new RowHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int posicion) {
                                //Obtener datos desde la vista donde estan todas las publicaciones
                                TextView tvTitulo = view.findViewById(R.id.tvTitulo);
                                TextView tvCuerpo = view.findViewById(R.id.tvCuerpo);
                                ImageView imPublicacion = view.findViewById(R.id.imPublicacion);
                                //Coloca los datos

                                String mTitulo= tvTitulo.getText().toString();
                                String mCuerpo=tvCuerpo.getText().toString();
                                Drawable mDrawable=imPublicacion.getDrawable();
                                Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();

                                //Pasar esta información al nuevo activity (DetallePublicacion)
                                Intent intent=new Intent(view.getContext(),DetallePublicacion.class);

                                //Se convierte la imagen en un arreglo de bytes para ser enviada.
                                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes=stream.toByteArray();

                                //Se pasan los parametros al otro activity
                                intent.putExtra("imagen",bytes);
                                intent.putExtra("titulo",mTitulo);
                                intent.putExtra("cuerpo",mCuerpo);
                                startActivity(intent);


                            }

                            @Override
                            public void onItemLongClick(View view, int posicion) {
                                // TODO hacer una  implementacion propia para un click largo
                                //Por ahora es la misma del corto
                                //Obtener datos desde la vista donde estan todas las publicaciones
                                TextView tvTitulo = view.findViewById(R.id.tvTitulo);
                                TextView tvCuerpo = view.findViewById(R.id.tvCuerpo);
                                ImageView imPublicacion = view.findViewById(R.id.imPublicacion);
                                //Coloca los datos

                                String mTitulo= tvTitulo.getText().toString();
                                String mCuerpo=tvCuerpo.getText().toString();
                                Drawable mDrawable=imPublicacion.getDrawable();
                                Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();

                                //Pasar esta información al nuevo activity (DetallePublicacion)
                                Intent intent=new Intent(view.getContext(),DetallePublicacion.class);

                                //Se convierte la imagen en un arreglo de bytes para ser enviada.
                                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes=stream.toByteArray();

                                //Se pasan los parametros al otro activity
                                intent.putExtra("imagen",bytes);
                                intent.putExtra("titulo",mTitulo);
                                intent.putExtra("cuerpo",mCuerpo);
                                startActivity(intent);

                            }
                        });
                        return  rowHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

//Cargar información al inciar el fragment


    @Override
    public void onStart() {
        super.onStart();

       // FirebaseRecyclerAdapter<PublicacionModel, RowHolder> firebaseRecyclerAdapter
         //       =new
        FirebaseRecyclerAdapter<PublicacionModel,RowHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PublicacionModel, RowHolder>(
                        PublicacionModel.class,R.layout.row,RowHolder.class,mRef
                ) {
                    @Override
                    protected void populateViewHolder(RowHolder viewHolder, PublicacionModel model, int position) {
                        viewHolder.setDetalles(getContext(),model.getTitulo(),model.getCuerpo(),model.getImagen());


                    }
                    @Override
                    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        RowHolder rowHolder = super.onCreateViewHolder(parent, viewType);
                        rowHolder.setOnClickListener(new RowHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int posicion) {
                                //Obtener datos desde la vista donde estan todas las publicaciones
                                TextView tvTitulo = view.findViewById(R.id.tvTitulo);
                                TextView tvCuerpo = view.findViewById(R.id.tvCuerpo);
                                ImageView imPublicacion = view.findViewById(R.id.imPublicacion);
                                //Coloca los datos

                                String mTitulo= tvTitulo.getText().toString();
                                String mCuerpo=tvCuerpo.getText().toString();
                                Drawable mDrawable=imPublicacion.getDrawable();
                                Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();

                                //Pasar esta información al nuevo activity (DetallePublicacion)
                                Intent intent=new Intent(view.getContext(),DetallePublicacion.class);

                                //Se convierte la imagen en un arreglo de bytes para ser enviada.
                                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes=stream.toByteArray();

                                //Se pasan los parametros al otro activity
                                intent.putExtra("imagen",bytes);
                                intent.putExtra("titulo",mTitulo);
                                intent.putExtra("cuerpo",mCuerpo);
                                startActivity(intent);


                            }

                            @Override
                            public void onItemLongClick(View view, int posicion) {
                                // TODO hacer una  implementacion propia para un click largo
                                //Por ahora es la misma del corto
                                //Obtener datos desde la vista donde estan todas las publicaciones
                                TextView tvTitulo = view.findViewById(R.id.tvTitulo);
                                TextView tvCuerpo = view.findViewById(R.id.tvCuerpo);
                                ImageView imPublicacion = view.findViewById(R.id.imPublicacion);
                                //Coloca los datos

                                String mTitulo= tvTitulo.getText().toString();
                                String mCuerpo=tvCuerpo.getText().toString();
                                Drawable mDrawable=imPublicacion.getDrawable();
                                Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();

                                //Pasar esta información al nuevo activity (DetallePublicacion)
                                Intent intent=new Intent(view.getContext(),DetallePublicacion.class);

                                //Se convierte la imagen en un arreglo de bytes para ser enviada.
                                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes=stream.toByteArray();

                                //Se pasan los parametros al otro activity
                                intent.putExtra("imagen",bytes);
                                intent.putExtra("titulo",mTitulo);
                                intent.putExtra("cuerpo",mCuerpo);
                                startActivity(intent);

                            }
                        });
                        return  rowHolder;
                    }
                };
        //Vamos a setear el adapter al reciclerView
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    //Inflar el menu, esto agrega elementos a la barra de acción si esta presente



}
