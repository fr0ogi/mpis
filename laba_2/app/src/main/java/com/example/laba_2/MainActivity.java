package com.example.laba_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private GestureDetectorCompat mDetector;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDetector = new GestureDetectorCompat(this,this);

        mDetector.setOnDoubleTapListener(this);

        textView = findViewById(R.id.textView);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    // Отслеживает одиночное нажатие
    public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.single_tap_confirmed, e.toString()));
        return true;
    }

    @Override
    // Отслеживает двойное нажатие
    public boolean onDoubleTap(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.on_double_tap, e.toString()));
        return true;
    }

    @Override
    // Отслеживает появление события во время выполнения жеста двойного нажатия
    public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.on_double_tap_event, e.toString()));
        return true;
    }

    @Override
    // Отслеживает появление касания, т. е. палец прижат к экрану
    public boolean onDown(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.on_down, e.toString()));
        return true;
    }

    @Override
    // Отслеживает, что произошло, событие касания и больше никаких событий не происходит короткое время
    public void onShowPress(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.on_show_press, e.toString()));
    }

    @Override
    // Отслеживает отпускание пальца
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.on_single_tap_up, e.toString()));
        return true;
    }

    @Override
    // Отслеживает движение пальца (прокрутка)
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        textView.setText(getString(R.string.on_scroll, (e1 == null) ? "" : e1.toString(), e2.toString()));
        return true;
    }

    @Override
    // Отслеживает удержание пальца прижатым к экрану длительное время
    public void onLongPress(@NonNull MotionEvent e) {
        textView.setText(getString(R.string.on_long_press, e.toString()));
    }

    @Override
    // Отслеживает появление жеста смахивания
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        textView.setText(getString(R.string.on_fling, (e1 == null) ? "" : e1.toString(), e2.toString()));
        return true;
    }
}