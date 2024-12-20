package com.example.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private EditText phoneEditText, firstNameEditText, lastNameEditText;
    private Button registrationButton, lastNameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.d(TAG, "onCreate");

        phoneEditText = findViewById(R.id.phoneEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        registrationButton = findViewById(R.id.registrationButton);
        lastNameButton = findViewById(R.id.lastNameButton);

        loadSavedData();

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, UserDetailsActivity.class);
                intent.putExtra("phone", phoneEditText.getText().toString());
                intent.putExtra("firstName", firstNameEditText.getText().toString());
                intent.putExtra("lastName", lastNameEditText.getText().toString());
                startActivity(intent);
            }
        });

        lastNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegistrationActivity.this, "Задание выполнил Кульбеда Кирилл Александрович", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSavedData() {
        SharedPreferences preferences = getSharedPreferences("TaxiApp", MODE_PRIVATE);
        phoneEditText.setText(preferences.getString("phone", ""));
        firstNameEditText.setText(preferences.getString("firstName", ""));
        lastNameEditText.setText(preferences.getString("lastName", ""));
        registrationButton.setText(preferences.contains("phone") ? "Log in" : "Registration");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        saveData();
    }

    private void saveData() {
        SharedPreferences preferences = getSharedPreferences("TaxiApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone", phoneEditText.getText().toString());
        editor.putString("firstName", firstNameEditText.getText().toString());
        editor.putString("lastName", lastNameEditText.getText().toString());
        editor.apply();
    }
}