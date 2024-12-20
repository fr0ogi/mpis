package com.example.journaldownloader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private Button buttonCheckFile, buttonDownload, buttonOpen, buttonDelete, buttonReset;
    private TextView textViewStatus;
    private File file;
    private static final String BASE_URL = "https://ntv.ifmo.ru/file/journal/";
    private static final String DIRECTORY_PATH = "/storage/emulated/0/Documents/journals/";

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String SHOW_POPUP_KEY = "showPopup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonCheckFile = findViewById(R.id.buttonCheckFile);
        buttonDownload = findViewById(R.id.buttonDownload);
        buttonOpen = findViewById(R.id.buttonOpen);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonReset = findViewById(R.id.buttonReset);
        textViewStatus = findViewById(R.id.textViewStatus);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Проверка, нужно ли показывать всплывающее окно
        boolean showPopup = sharedPreferences.getBoolean(SHOW_POPUP_KEY, true);
        if (showPopup) {
            showPopupWindow();
        }

        buttonCheckFile.setOnClickListener(v -> {
            String pageNum = editTextUrl.getText().toString();
            file = new File(DIRECTORY_PATH, "journal_" + pageNum + ".pdf");

            // Скрыть EditText и кнопку "Проверить наличие журнала"
            editTextUrl.setVisibility(View.GONE);
            buttonCheckFile.setVisibility(View.GONE);
            resetButtons();

            if (file.exists()) {
                textViewStatus.setText("\n" + "The file has already been downloaded. You can open it or delete it.");
                buttonOpen.setVisibility(View.VISIBLE);
                buttonDelete.setVisibility(View.VISIBLE);
            } else {
                String url = BASE_URL + pageNum + ".pdf";
                new CheckFileTask().execute(url);
            }
        });

        buttonDownload.setOnClickListener(v -> {
            String pageNum = editTextUrl.getText().toString();
            String url = BASE_URL + pageNum + ".pdf";
            new DownloadFileTask().execute(url, pageNum);
        });

        buttonOpen.setOnClickListener(v -> openFile());

        buttonDelete.setOnClickListener(v -> deleteFile());

        buttonReset.setOnClickListener(v -> {
            resetInput();
            buttonReset.setVisibility(View.GONE); // Скрываем кнопку "Снова ввести номер журнала"
        });
    }

    private void resetButtons() {
        buttonDownload.setVisibility(View.GONE);
        buttonOpen.setVisibility(View.GONE);
        buttonDelete.setVisibility(View.GONE);
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetInput() {
        editTextUrl.setVisibility(View.VISIBLE);
        buttonCheckFile.setVisibility(View.VISIBLE);
        resetButtons();
        textViewStatus.setText("");
        editTextUrl.setText("");
    }

    private class CheckFileTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isAvailable) {
            if (isAvailable) {
                textViewStatus.setText("\n" + "The file is available for download");
                buttonDownload.setVisibility(View.VISIBLE);
            } else {
                textViewStatus.setText("\n" + "File not found");
                buttonDownload.setVisibility(View.GONE);
            }
        }
    }

    private class DownloadFileTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String fileUrl = params[0];
            String journalNumber = params[1];
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                File directory = new File(DIRECTORY_PATH);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                file = new File(directory, "journal_" + journalNumber + ".pdf");
                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                long total = 0;
                int fileLength = connection.getContentLength();

                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                    total += len;
                    publishProgress((int) (total * 100 / fileLength));
                }

                outputStream.close();
                inputStream.close();
                return "File downloaded: journal_" + journalNumber + ".pdf";
            } catch (Exception e) {
                return "\n" + "Error downloading file";
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textViewStatus.setText("Download: " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String result) {
            textViewStatus.setText(result);
            buttonOpen.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);
        }
    }

    private void openFile() {
        if (file != null && file.exists()) {
            Uri fileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = Uri.parse("content://" + getPackageName() + ".fileprovider/" + file.getAbsolutePath());
            } else {
                fileUri = Uri.fromFile(file);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            textViewStatus.setText("\n" + "File not found");
        }
    }

    private void deleteFile() {
        if (file != null && file.exists()) {
            file.delete();
            textViewStatus.setText("\n" + "File deleted");
            buttonOpen.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        } else {
            textViewStatus.setText("File not found");
        }
    }

    private void showPopupWindow() {
        // Загружаем макет всплывающего окна
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_instruction, null);

        // Инициализируем элементы всплывающего окна
        CheckBox checkBoxDontShowAgain = popupView.findViewById(R.id.checkBoxDontShowAgain);
        Button buttonOk = popupView.findViewById(R.id.buttonOk);

        // Создаем диалоговое окно
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);

        AlertDialog dialog = builder.create();

        // Настраиваем кнопку "ОК"
        buttonOk.setOnClickListener(v -> {
            if (checkBoxDontShowAgain.isChecked()) {
                // Сохраняем настройку, чтобы больше не показывать всплывающее окно
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SHOW_POPUP_KEY, false);
                editor.apply();
            }
            dialog.dismiss(); // Закрываем окно
        });

        // Показываем диалоговое окно
        dialog.show();
    }
}
