package com.example.lab4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4.R;
import com.example.lab4.model.Element;
import java.util.List;

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ElementViewHolder> {
    private List<Element> elements;

    public ElementAdapter(List<Element> elements) {
        this.elements = elements;
    }

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Используем пользовательский макет item_element
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_element, parent, false);
        return new ElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        Element element = elements.get(position);
        // Заполняем все поля
        holder.nameText.setText(element.getName());
        holder.usernameText.setText("Username: " + element.getUsername());
        holder.emailText.setText("Email: " + element.getEmail());
        holder.phoneText.setText("Phone: " + element.getPhone());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public static class ElementViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView usernameText;
        TextView emailText;
        TextView phoneText;

        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            // Инициализируем TextView из макета
            nameText = itemView.findViewById(R.id.nameText);
            usernameText = itemView.findViewById(R.id.usernameText);
            emailText = itemView.findViewById(R.id.emailText);
            phoneText = itemView.findViewById(R.id.phoneText);
        }
    }
}