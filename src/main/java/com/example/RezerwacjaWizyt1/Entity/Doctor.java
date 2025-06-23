package com.example.RezerwacjaWizyt1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="doctor")
public class Doctor {
    @Id
    @Column(name="pesel",unique = true,length = 11)
    private String pesel;

    @Override
    public String toString() {
        return "Doctor{" +
                "pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specjalnosc=" + specjalnosc +
                ", visitList=" + visitList +
                '}';
    }

    @NotNull
    private String name;
    @NotNull
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "specjalnosc", nullable = false, length = 50)
    private SPECJALIZACJE specjalnosc;
    @OneToMany(mappedBy = "doctor")
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
}
