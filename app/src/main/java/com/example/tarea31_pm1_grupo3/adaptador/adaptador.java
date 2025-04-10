package com.example.tarea31_pm1_grupo3.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tarea31_pm1_grupo3.Modelo.PersonaModelo;
import com.example.tarea31_pm1_grupo3.R;
import com.example.tarea31_pm1_grupo3.funciones.imageUtils;

import java.util.List;


public class adaptador extends ArrayAdapter<PersonaModelo> {
    private List<PersonaModelo> myList;
    private Context myContext;
    private int resourceLayout;
    public adaptador(@NonNull Context context, int resource, List<PersonaModelo> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        PersonaModelo modelo = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row,parent,false);
        }

        ImageView imagen = convertView.findViewById(R.id.imageView);
        imagen.setImageBitmap(imageUtils.decodeFromBase64(modelo.getFoto()));

        TextView id = convertView.findViewById(R.id.textId);
        id.setText("ID: "+String.valueOf(modelo.getId()));

        TextView nombre = convertView.findViewById(R.id.textNombre);
        nombre.setText("Nombre: "+modelo.getNombre());

        TextView apellido = convertView.findViewById(R.id.textApellido);
        apellido.setText("Apellido: " + String.valueOf(modelo.getApellido()));


        TextView correo = convertView.findViewById(R.id.textCorreo);
        correo.setText("Correo: " + String.valueOf(modelo.getCorreo()));

        TextView fecha = convertView.findViewById(R.id.textFecha);
        fecha.setText("Fecha: " + String.valueOf(modelo.getFechaNac()));



        return convertView;
    }
}
