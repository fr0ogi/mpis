plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.lab7savkops"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab7savkops"
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Зависимости для работы с мультимедиа
    implementation("com.google.android.exoplayer:exoplayer:2.19.1") // Замените на последнюю версию
    implementation("com.github.bumptech.glide:glide:4.16.0") // Замените на последнюю версию
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0") // Замените на последнюю версию

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}