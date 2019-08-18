package com.example.appjurescaym.model;

public class PublicacionModel {
    String id,titulo,cuerpo,imagen;

    public PublicacionModel(){

    }

    public PublicacionModel(String titulo,String cuerpo,String imagen) {

        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
