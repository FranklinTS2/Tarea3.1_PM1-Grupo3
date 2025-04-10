package com.example.tarea31_pm1_grupo3.funciones;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tarea31_pm1_grupo3.Modelo.PersonaModelo;
import com.example.tarea31_pm1_grupo3.adaptador.adaptador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseConf {
    PersonaModelo persona;
    private FirebaseFirestore mfirestore;
    //List<PersonaModelo> lista;

    public FirebaseConf(FirebaseFirestore mfirestore) {
        this.mfirestore = mfirestore;

    }

    public void subirDatos( Context contexto,String nombre, String apellido, String correo,String fechaNac, String foto) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("apellido",apellido);
        map.put("correo",correo);
        map.put("fechaNac",fechaNac);
        map.put("foto",foto);

        mfirestore.collection("PersonaModelo").add(map).addOnSuccessListener(new OnSuccessListener<>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(contexto,"Creado exitosamente",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contexto,"Error al ingresar datos: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }



    public interface FirestoreCallback {
        void onCallback(List<PersonaModelo> personaList);
    }

    public void obtenerDatos(FirestoreCallback firestoreCallback){
        List <PersonaModelo> lista = new ArrayList<>();

        mfirestore.collection("PersonaModelo").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id,nombre,apellido,correo,fecha,foto;
                            id = document.getId();
                            nombre = String.valueOf(document.getData().get("nombre"));
                            apellido = String.valueOf(document.getData().get("apellido"));
                            correo = String.valueOf(document.getData().get("correo"));
                            fecha = String.valueOf(document.getData().get("fechaNac"));
                            foto = String.valueOf(document.getData().get("foto"));
                            lista.add(new PersonaModelo(id,nombre,apellido,correo,fecha,foto));
                            Log.d("NombrePersona", "nombre: "+nombre+" apellido: "+apellido+" correo: "+correo+" fecha: "+fecha+" foto: "+foto);
                        }
                        firestoreCallback.onCallback(lista); // Llamar al callback con la lista llena
                    } else {
                        Log.d("Error getting documents: ", String.valueOf(task.getException()));
                        firestoreCallback.onCallback(new ArrayList<>());
                    }
                });
    }


    public void eliminarPersona(Context contexto, String id){
        mfirestore.collection("PersonaModelo").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(contexto,"Eliminado exitosamente",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contexto,"Error al eliminar registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizarDatos( Context contexto,String id,String nombre, String apellido, String correo,String fechaNac, String foto) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("apellido",apellido);
        map.put("correo",correo);
        map.put("fechaNac",fechaNac);
        map.put("foto",foto);

        mfirestore.collection("PersonaModelo").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(contexto,"Registro actualizado exitosamente",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contexto,"Error al actualizar registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }
}
