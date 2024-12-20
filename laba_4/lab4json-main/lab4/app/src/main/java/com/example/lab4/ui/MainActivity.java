package com.example.lab4.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4.R;
import com.example.lab4.adapter.ElementAdapter;
import com.example.lab4.model.Element;
import com.example.lab4.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ElementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loadButton = findViewById(R.id.loadButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        Log.d("MainActivity", "Начало загрузки данных");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Element>> call = apiService.getElements();
        call.enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(Call<List<Element>> call, Response<List<Element>> response) {
                Log.d("MainActivity", "Ответ получен");
                if (response.isSuccessful() && response.body() != null) {
                    List<Element> elements = response.body();
                    adapter = new ElementAdapter(elements);
                    recyclerView.setAdapter(adapter);
                    Log.d("MainActivity", "Данные загружены: " + elements.size());
                } else {
                    Log.e("API_ERROR", "Ошибка: " + response.message());
                    Log.e("API_ERROR", "Код ответа: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Element>> call, Throwable t) {
                Log.e("API_ERROR", "Ошибка загрузки данных: " + t.getMessage());
            }
        });
    }
}