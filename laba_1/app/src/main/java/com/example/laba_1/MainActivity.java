package com.example.laba_1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText editText;
    private ImageView imageView;
    private ImageButton imageButtonLike, imageButtonDislike;

    private String ImageURL;
    private String pageURL;
    private String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        ImageButton buttonForSearch = findViewById(R.id.button_for_searching);
        editText = findViewById(R.id.input_for_searching);
        imageView = findViewById(R.id.imageView);
        imageButtonLike = findViewById(R.id.imageButtonLike);
        imageButtonDislike = findViewById(R.id.imageButtonDislike);

        buttonForSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                if (!query.isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    imageButtonLike.setVisibility(View.INVISIBLE);
                    imageButtonDislike.setVisibility(View.INVISIBLE);

                    String accessKey = BuildConfig.UNSPLASH_ACCESS_KEY;
                    String url = "https://pixabay.com/api/?key=" + accessKey + "&q=" + query.replace(" ", "+") + "&image_type=photo&orientation=vertical&lang=ru&per_page=3";
                    Title = query;

                    getPhoto(url);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.image_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (!ImageURL.isEmpty()) {
                            switch (item.getItemId()) {
                                case R.id.download:
                                    downloadPhoto();
                                    Toast.makeText(MainActivity.this, "Начало загрузки", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.original:
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pageURL));
                                    MainActivity.this.startActivity(intent);
                                default:
                                    return false;
                            }
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    private void setPhoto() {
        Glide.with(MainActivity.this).load(ImageURL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                showError("Ошибка загрузки изображения");
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                imageButtonLike.setVisibility(View.VISIBLE);
                imageButtonDislike.setVisibility(View.VISIBLE);
                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade(500)).into(imageView);
    }

    private void getPhoto(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showError("Ошибка запроса: \n" + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("", "Response: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray hits = jsonObject.getJSONArray("hits");
                        if (hits.length() == 0) {
                            showError("Ничего не найдено!");
                            return;
                        }
                        JSONObject hit = hits.getJSONObject(0);
                        pageURL = hit.getString("pageURL");
                        ImageURL = hit.getString("largeImageURL");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setPhoto();
                            }
                        });
                    } catch (JSONException e) {
                        showError("Ошибка обработки JSON: \n" + e.getMessage());
                    }
                } else {
                    showError("Неизвестная ошибка!");
                }
            }
        });
    }

    private void downloadPhoto() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ImageURL));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(Title);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Title + ".jpg");

        DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    private void showError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(errorMessage)
                        .setPositiveButton("OK", null)
                        .show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    public void estimation(View view) {
        switch (view.getId()) {
            case R.id.imageButtonLike:
                Toast.makeText(this, "Лайк :)", Toast.LENGTH_LONG).show();
                break;
            case R.id.imageButtonDislike:
                Toast.makeText(this, "Дизлайк :(", Toast.LENGTH_LONG).show();
                break;
        }
    }
}