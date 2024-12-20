package com.example.lab3;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RouteActivity extends AppCompatActivity {

    private EditText routeEditText1, routeEditText2, routeEditText3, routeEditText4, routeEditText5, routeEditText6;
    private Button okButton,lastNameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        routeEditText1 = findViewById(R.id.routeEditText1);
        routeEditText2 = findViewById(R.id.routeEditText2);
        routeEditText3 = findViewById(R.id.routeEditText3);
        routeEditText4 = findViewById(R.id.routeEditText4);
        routeEditText5 = findViewById(R.id.routeEditText5);
        routeEditText6 = findViewById(R.id.routeEditText6);
        okButton = findViewById(R.id.okButton);
        lastNameButton = findViewById(R.id.lastNameButton);

        lastNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RouteActivity.this, "Задание выполнил Кульбеда Кирилл Александрович", Toast.LENGTH_SHORT).show();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String route = routeEditText1.getText().toString() + ", " +
                        routeEditText2.getText().toString() + ", " +
                        routeEditText3.getText().toString() + ", " +
                        routeEditText4.getText().toString() + ", " +
                        routeEditText5.getText().toString() + ", " +
                        routeEditText6.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("route", route);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}