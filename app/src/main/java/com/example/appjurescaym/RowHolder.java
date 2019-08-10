package com.example.appjurescaym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RowHolder extends RecyclerView.ViewHolder {
    View mView;

    public RowHolder(@NonNull View itemView) {
        super(itemView);
        mView=itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });
        //item largo click
      itemView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              mClickListener.onItemLongClick(v,getAdapterPosition());
              return true;
          }
      });
    }


    public void setDetalles(Context ctx,String titulo, String cuerpo,String imagen ){
        TextView tvTitulo=mView.findViewById(R.id.tvTitulo);
        TextView tvCuerpo=mView.findViewById(R.id.tvCuerpo);
        ImageView imImagen=mView.findViewById(R.id.imPublicacion);

        //Set datos al View
        tvTitulo.setText(titulo);
        tvCuerpo.setText(cuerpo);
        Picasso.get().load(imagen).into(imImagen);

    }
    private RowHolder.ClickListener mClickListener;
    //Interface para enviar callbacks
    public interface ClickListener{
        void onItemClick(View view,int posicion);
        void onItemLongClick(View view,int posicion);
    }
    public void setOnClickListener(RowHolder.ClickListener clickListener){
        mClickListener=clickListener;
    }

}
