# JournalDownloader

JournalDownloader is an Android application designed to download and manage journal files. It allows users to check the availability of journal files, download them, open the downloaded files, and delete them if needed.

## Features
- **Check Journal Availability**: Users can verify if a specific journal file is available for download.
- **Download Journal Files**: Download journal files from a specified URL.
- **Open Downloaded Files**: Open downloaded journal files using a compatible application.
- **Delete Files**: Remove downloaded files from the device.

### Requirements
- Android Studio
- An Android device or emulator

## Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/alexsandro007/AndroidFileSystem.git
   ```
2. Open the JournalDownloader folder in Android Studio.
3. Build and run the project on an Android emulator or a physical device.

### Usage
- **Check Journal Availability**: Enter the journal ID in the input field and click the "Проверить наличие журнала" button to check if the file is available.
- **Download Journal File**: If the file is available, click the "Скачать файл" button to download it.
- **Open File**: Click the "Открыть файл" button to view the downloaded journal.
- **Delete File**: Click the "Удалить файл" button to remove the downloaded journal from the device.
- **Reset Input**: Click the "Снова ввести номер журнала" button to enter a new journal ID.

### Permissions
The application requires the following permissions:
- `INTERNET`: To make network requests.
- `ACCESS_NETWORK_STATE`: To check the network connection status.
- `WRITE_EXTERNAL_STORAGE`: To save downloaded files.
- `READ_EXTERNAL_STORAGE`: To read downloaded files.
- `MANAGE_EXTERNAL_STORAGE`: To manage files in external storage.

## Technologies Used
- **Java**: Core programming language for the application.
- **Android Studio**: Development environment used to build the app.
- **Android SDK**: Software development kit for building Android applications.
- **AsyncTask**: Used for performing background operations and updating the UI thread.
- **HTTPURLConnection**: Class used for making HTTP requests.
- **File I/O**: For handling file downloads and storage.