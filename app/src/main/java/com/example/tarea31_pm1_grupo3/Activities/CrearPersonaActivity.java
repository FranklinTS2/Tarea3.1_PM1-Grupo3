package com.example.tarea31_pm1_grupo3.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tarea31_pm1_grupo3.Modelo.Persona;
import com.example.tarea31_pm1_grupo3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearPersonaActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private ImageView imgPersona;
    private EditText etNombres, etApellidos, etCorreo, etFecha, etFoto;
    private Button btnGuardar, btnListado;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_persona);

        imgPersona = findViewById(R.id.imgPersona);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etCorreo = findViewById(R.id.etCorreo);
        etFecha = findViewById(R.id.etFecha);
        etFoto = findViewById(R.id.etFoto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnListado = findViewById(R.id.btnListado);

        dbRef = FirebaseDatabase.getInstance().getReference("personas");

        // Abrir cámara al tocar imagen
        imgPersona.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            } else {
                abrirCamara();
            }
        });

        btnGuardar.setOnClickListener(v -> {
            String id = dbRef.push().getKey();
            String nombres = etNombres.getText().toString().trim();
            String apellidos = etApellidos.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String foto = etFoto.getText().toString().trim();

            if (nombres.isEmpty() || apellidos.isEmpty()) {
                Toast.makeText(this, "Nombre y Apellido son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            Persona persona = new Persona(id, nombres, apellidos, correo, fecha, foto);
            dbRef.child(id).setValue(persona)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Persona guardada correctamente", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show());
        });
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Resultado de la cámara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgPersona.setImageBitmap(imageBitmap);
        }
    }

    // Resultado del permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



