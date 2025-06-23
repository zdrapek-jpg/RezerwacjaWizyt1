package com.example.RezerwacjaWizyt1.Entity;


public enum SPECJALIZACJE {
    NEUROLOG("Neurologia"),
    GASTROLOG("Gastroenterologia"),
    RADIOLOG("Radiologia"),
    ANESTEZJOLOG("Anestezjologia"),
    HIRURG("Chirurgia"),
    DIABETOLOG("Diabetologia"),
    ENDOKRYNOLOG("Endokrynologia"),
    GINEKOLOG("Ginekologia"),
    KARDIOLOG("Kardiologia");

    public final  String description;
    SPECJALIZACJE(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
