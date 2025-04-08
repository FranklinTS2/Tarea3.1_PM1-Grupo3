package com.example.tarea31_pm1_grupo3.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea31_pm1_grupo3.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista de ejemplo
        List<Contacto> listaContactos = new ArrayList<>();
        listaContactos.add(new Contacto("1", "Juan", "Pérez", "+52 555 1234", ""));
        listaContactos.add(new Contacto("2", "María", "Gómez", "+52 555 5678", ""));

        // Configurar el adaptador
        // Manejar el click en un contacto
        ContactoAdapter adapter = new ContactoAdapter(listaContactos, contacto -> {
            // Manejar el click en un contacto
            System.out.println("Contacto seleccionado: " + contacto.getNombre());
        });

        recyclerView.setAdapter(adapter);
    }
}