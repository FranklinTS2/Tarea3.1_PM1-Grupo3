package com.example.tarea31_pm1_grupo3.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarea31_pm1_grupo3.Modelo.PersonaModelo;
import com.example.tarea31_pm1_grupo3.R;
import com.example.tarea31_pm1_grupo3.adaptador.adaptador;
import com.example.tarea31_pm1_grupo3.funciones.FirebaseConf;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Listado extends AppCompatActivity {

    private ListView myListView;
    adaptador mAdapter;
    private List<PersonaModelo> myLista = new ArrayList<>();
    private FirebaseFirestore mfirestore;
    FirebaseConf firebaseConf;
    PersonaModelo selectedProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listado);

        mfirestore = FirebaseFirestore.getInstance();
        firebaseConf = new FirebaseConf(mfirestore);

        myListView = (ListView) findViewById(R.id.listaPersona);

        setLista();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedProducto = (PersonaModelo) (myListView.getItemAtPosition(i));
                AlertDialog dialog = createDialog("¿Que desea realizar?",selectedProducto);
                dialog.show();


            }



        });

    }

    AlertDialog createDialog(String mensaje, PersonaModelo lista){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent modificar = new Intent(getApplicationContext(), ModificarPersona.class);


                modificar.putExtra("Id",String.valueOf(lista.getId()));
                modificar.putExtra("Nombre",lista.getNombre());
                modificar.putExtra("Apellido",String.valueOf(lista.getApellido()));
                modificar.putExtra("Correo",lista.getCorreo());
                modificar.putExtra("Fecha",lista.getFechaNac());
                modificar.putExtra("Imagen",lista.getFoto());
                startActivity(modificar);

            }


        });
        builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog dialog = createDialogConfirmar("¿Desea continuar con la eliminación?",String.valueOf(lista.getId()));
                dialog.show();

            }


        });
        return builder.create();
    }

    AlertDialog createDialogConfirmar(String mensaje, String idPersona){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseConf.eliminarPersona(getApplicationContext(),String.valueOf(idPersona));
                setLista();
            }


        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"No se elimino el registro",Toast.LENGTH_SHORT).show();
            }


        });
        return builder.create();
    }

    private void setLista() {
        firebaseConf.obtenerDatos(new FirebaseConf.FirestoreCallback() {
            @Override
            public void onCallback(List<PersonaModelo> personaList) {
                mAdapter = new adaptador(getApplicationContext(),0,personaList);
                myListView.setAdapter(mAdapter);
                setLista();
            }
        });
    }


}