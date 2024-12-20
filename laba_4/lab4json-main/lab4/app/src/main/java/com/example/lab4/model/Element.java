package com.example.lab4.model;

import com.google.gson.annotations.SerializedName;

public class Element {
    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username; // Добавлено поле username

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone; // Добавлено поле phone

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username; // Геттер для username
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone; // Геттер для phone
    }
}