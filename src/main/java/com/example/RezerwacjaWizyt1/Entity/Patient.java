package com.example.RezerwacjaWizyt1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="patient")
public class Patient {
    @Id
    @Column(name="pesel",unique = true,length = 11)
    private String pesel;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private Long age;

    @NotNull
    private boolean czyPosiadaKarte;

    @OneToMany(mappedBy = "patient")
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public boolean isCzyPosiadaKarte() {
        return czyPosiadaKarte;
    }

    public void setCzyPosiadaKarte(boolean czyPosiadaKarte) {
        this.czyPosiadaKarte = czyPosiadaKarte;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", czyPosiadaKarte=" + czyPosiadaKarte +
                ", visitList=" + visitList +
                '}';
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }
}

