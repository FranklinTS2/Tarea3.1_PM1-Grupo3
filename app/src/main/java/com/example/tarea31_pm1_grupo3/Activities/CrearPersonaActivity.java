package com.example.tarea31_pm1_grupo3.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
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

import com.example.tarea31_pm1_grupo3.Modelo.PersonaModelo;
import com.example.tarea31_pm1_grupo3.R;
import com.example.tarea31_pm1_grupo3.funciones.FirebaseConf;
import com.example.tarea31_pm1_grupo3.funciones.imageUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CrearPersonaActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private ImageView imgPersona;
    private EditText etNombres, etApellidos, etCorreo, etFecha;
    Button btnGuardar, btnListado;
    private FirebaseFirestore mfirestore;
    PersonaModelo personaModelo;
    FirebaseConf firebaseConf;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_persona);

        mfirestore = FirebaseFirestore.getInstance();
        firebaseConf = new FirebaseConf(mfirestore);
        personaModelo = new PersonaModelo();


        imgPersona = findViewById(R.id.imgPersona);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etCorreo = findViewById(R.id.etCorreo);

        calendar = Calendar.getInstance();
        etFecha = findViewById(R.id.etFecha);
        limpiarDatos();

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateField();
        };

        etFecha.setOnClickListener(v -> new DatePickerDialog(CrearPersonaActivity.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());

        // Initial update of the date field (optional, shows current date initially)

        btnGuardar = findViewById(R.id.btnGuardar);
        btnListado = findViewById(R.id.btnListado);



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


            if (etNombres.getText().toString().trim().isEmpty() || etApellidos.getText().toString().trim().isEmpty() || etCorreo.getText().toString().trim().isEmpty() || etFecha.getText().toString().trim().isEmpty() || personaModelo.getFoto() == null) {
                Toast.makeText(this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }else{
                personaModelo.setNombre(String.valueOf(etNombres.getText()));
                personaModelo.setApellido(String.valueOf(etApellidos.getText()));
                personaModelo.setCorreo(String.valueOf(etCorreo.getText()));
                personaModelo.setFechaNac(String.valueOf(etFecha.getText()));

                firebaseConf.subirDatos(this,personaModelo.getNombre(),personaModelo.getApellido(), personaModelo.getCorreo(),personaModelo.getFechaNac(),personaModelo.getFoto());
                limpiarDatos();

            }


        });

        btnListado.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),Listado.class);
            startActivity(intent);
        });
    }
    private void updateDateField() {
        String myFormat = "dd/MM/yyyy"; // Desired date format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        etFecha.setText(sdf.format(calendar.getTime()));
    }

    private void limpiarDatos(){
        etNombres.setText(null);
        etApellidos.setText(null);
        etCorreo.setText(null);
        etFecha.setText(null);
        imgPersona.setImageResource(R.drawable.imagen);

        personaModelo.setNombre(null);
        personaModelo.setApellido(null);
        personaModelo.setCorreo(null);
        personaModelo.setFechaNac(null);
        personaModelo.setFoto(null);

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
            personaModelo.setFoto(String.valueOf(imageUtils.encodeToBase64(imageBitmap)));

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



