package com.example.tarea31_pm1_grupo3.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ir directamente a CrearPersonaActivity
        Intent intent = new Intent(this, CrearPersonaActivity.class);
        startActivity(intent);
        finish(); // Para que no se pueda volver con el botón atrás
    }
}
