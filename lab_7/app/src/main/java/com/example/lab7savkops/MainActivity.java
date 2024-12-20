package com.example.lab7savkops;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView imageView;
    private Button playButton;
    private Button stopButton;
    private Button nextImageButton;
    private Button nextVideoButton;

    private int[] imageResources = {R.drawable.img, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5}; // Добавьте ваши изображения
    private int[] videoResources = {R.raw.lab7_1, R.raw.lab7_2}; // Добавьте ваши видео
    private int currentImageIndex = 0;
    private int currentVideoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        imageView = findViewById(R.id.imageView);
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        nextImageButton = findViewById(R.id.nextImageButton);
        nextVideoButton = findViewById(R.id.nextVideoButton);

        // Установка первого видео
        setVideo(currentVideoIndex);
        // Установка первого изображения
        loadImage(currentImageIndex);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause(); // Останавливает видео
            }
        });

        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageIndex = (currentImageIndex + 1) % imageResources.length;
                loadImage(currentImageIndex);
            }
        });

        nextVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentVideoIndex = (currentVideoIndex + 1) % videoResources.length;
                setVideo(currentVideoIndex);
            }
        });
    }

    private void loadImage(int index) {
        Glide.with(this).load(imageResources[index]).into(imageView);
    }

    private void setVideo(int index) {
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResources[index]);
        videoView.setVideoURI(videoUri);
    }
}