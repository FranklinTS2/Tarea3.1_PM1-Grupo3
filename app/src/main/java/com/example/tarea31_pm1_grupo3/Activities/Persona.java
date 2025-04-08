package com.example.tarea31_pm1_grupo3.Modelo;

public class Persona {
    public String id, nombres, apellidos, correo, fechanac, foto;

    public Persona() {
    }

    public Persona(String id, String nombres, String apellidos, String correo, String fechanac, String foto) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.fechanac = fechanac;
        this.foto = foto;
    }
}

