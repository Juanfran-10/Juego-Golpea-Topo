package com.example.juegogolpeatopo;

public class Usuario {
    private String Edad, Correo, Fecha, Imagen, Nombre, Pais, Password, Uid;
    private int Topos;

    public Usuario() {
    }

    public Usuario(String edad, String correo, String fecha, String imagen, String nombre, String pais, String password, String uid, int topos) {
        this.Edad = edad;
        this.Correo = correo;
        this.Fecha = fecha;
        this.Imagen = imagen;
        this.Nombre = nombre;
        this.Pais = pais;
        this.Password = password;
        this.Uid = uid;
        this.Topos = topos;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        this.Edad = edad;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        this.Correo = correo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        this.Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        this.Pais = pais;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    public int getTopos() {
        return Topos;
    }

    public void setTopos(int topos) {
        this.Topos = topos;
    }
}
