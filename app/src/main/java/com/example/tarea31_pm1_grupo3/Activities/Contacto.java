package com.example.tarea31_pm1_grupo3.Activities;

public class Contacto {
    private final String id;
    private final String nombre;
    private final String apellido;
    private final String telefono;
    private final String imagenUrl;

    // Constructor
    public Contacto(String id, String nombre, String apellido, String telefono, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.imagenUrl = imagenUrl;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getImagenUrl() { return imagenUrl; }
}