plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.lab8savko"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab8savko"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("org.osmdroid:osmdroid-android:6.1.10")
    implementation("org.osmdroid:osmdroid-android:6.1.10")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.gms:play-services-location:20.0.0")
    implementation("org.json:json:20210307")
implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}