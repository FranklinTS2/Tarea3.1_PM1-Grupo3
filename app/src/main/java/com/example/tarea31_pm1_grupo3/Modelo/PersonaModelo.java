package com.example.tarea31_pm1_grupo3.Modelo;

public class PersonaModelo {
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String fechaNac;
    private String foto;

    public PersonaModelo() {
    }

    public PersonaModelo(String id, String nombre, String apellido, String correo, String fechaNac, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.foto = foto;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String FechaNac) {
        this.fechaNac = FechaNac;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
