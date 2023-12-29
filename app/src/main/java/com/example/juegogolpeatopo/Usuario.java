package com.example.juegogolpeatopo;

public class Usuario {
    private String edad, correo, fecha, imagen, nombre, pais, password, uid;
    private int topos;

    public Usuario() {
    }

    public Usuario(String edad, String correo, String fecha, String imagen, String nombre, String pais, String password, String uid, int topos) {
        this.edad = edad;
        this.correo = correo;
        this.fecha = fecha;
        this.imagen = imagen;
        this.nombre = nombre;
        this.pais = pais;
        this.password = password;
        this.uid = uid;
        this.topos = topos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTopos() {
        return topos;
    }

    public void setTopos(int topos) {
        this.topos = topos;
    }
}
