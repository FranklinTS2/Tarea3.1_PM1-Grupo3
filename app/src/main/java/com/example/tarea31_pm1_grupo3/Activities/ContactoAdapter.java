package com.example.tarea31_pm1_grupo3.Activities;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea31_pm1_grupo3.R;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private final List<Contacto> listaContactos;
    private final OnItemClickListener listener;

    // Interface para manejar clicks
    public interface OnItemClickListener {
        void onItemClick(Contacto contacto);
    }

    // Constructor
    public ContactoAdapter(List<Contacto> listaContactos, OnItemClickListener listener) {
        this.listaContactos = listaContactos;
        this.listener = listener;
    }

    // ViewHolder
    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivContacto;
        public TextView txtNombre, txtID, txtTelefono;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivContacto = itemView.findViewById(R.id.ivContactoLista);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtID = itemView.findViewById(R.id.txtID);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
        }
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list, parent, false);
        return new ContactoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);

        holder.txtNombre.setText(contacto.getNombre() + " " + contacto.getApellido());
        holder.txtID.setText("ID: " + contacto.getId());
        holder.txtTelefono.setText(contacto.getTelefono());

        // Cargar imagen con Glide (opcional)
        // Glide.with(holder.itemView.getContext())
        //      .load(contacto.getImagenUrl())
        //      .into(holder.ivContacto);

        // Click listener
        holder.itemView.setOnClickListener(v -> listener.onItemClick(contacto));
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }
}