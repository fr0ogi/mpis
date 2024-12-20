package com.example.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

public class FragmentUpdate extends Fragment {
    private EditText editTextId, editTextDescription;
    private Button buttonUpdate;
    private MyDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        editTextId = view.findViewById(R.id.editTextId);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        dbHelper = new MyDatabaseHelper(getContext());

        buttonUpdate.setOnClickListener(v -> {
            int id = Integer.parseInt(editTextId.getText().toString());
            String description = editTextDescription.getText().toString();
            dbHelper.updateNote(id, description);
            editTextId.setText("");
            editTextDescription.setText("");
        });
        return view;
    }
}