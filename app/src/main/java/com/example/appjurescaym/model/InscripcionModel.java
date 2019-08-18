package com.example.appjurescaym.model;

public class InscripcionModel {
    private String id;
    private String nombre;
    private String Apellido1;
    private String Apellido2;
    private String telefono;
    private String actividad;
    private String ministerio;
    private String edad;
    private String telefonoEncargado;
    private String padecimiento;

    public InscripcionModel() {
    }

    public InscripcionModel(String id, String nombre, String apellido1, String apellido2, String telefono, String actividad, String ministerio, String edad, String telefonoEncargado, String padecimiento) {
        this.id = id;
        this.nombre = nombre;
        Apellido1 = apellido1;
        Apellido2 = apellido2;
        this.telefono = telefono;
        this.actividad = actividad;
        this.ministerio = ministerio;
        this.edad = edad;
        this.telefonoEncargado = telefonoEncargado;
        this.padecimiento = padecimiento;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getMinisterio() {
        return ministerio;
    }

    public void setMinisterio(String ministerio) {
        this.ministerio = ministerio;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefonoEncargado() {
        return telefonoEncargado;
    }

    public void setTelefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String apellido1) {
        Apellido1 = apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String apellido2) {
        Apellido2 = apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }




}
