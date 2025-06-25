package com.example.RezerwacjaWizyt1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="doctor")
public class Doctor {
    @Id
    @Column(name="pesel",unique = true,length = 11)
    private String pesel;

    @NotNull
    private String name;
    @NotNull
    private String surname;


    @Email
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(name = "specjalnosc", nullable = false, length = 50)
    private SPECJALIZACJE specjalnosc;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
    private List<Visit> visitList;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SPECJALIZACJE getSpecjalnosc() {
        return specjalnosc;
    }

    public void setSpecjalnosc(SPECJALIZACJE specjalnosc) {
        this.specjalnosc = specjalnosc;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    @Override
    public String toString() {
        if (this.email==null){
            return "Doctor{" +
                    "pesel='" + pesel + '\'' +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", specjalnosc=" + specjalnosc +
                    ", visitList=" + visitList +
                    '}';
        }

        return "Doctor{" +
                "pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", specjalnosc=" + specjalnosc +
                ", visitList=" + visitList +
                '}';
    }
}
