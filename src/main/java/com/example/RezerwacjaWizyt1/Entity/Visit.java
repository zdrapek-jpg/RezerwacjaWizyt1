package com.example.RezerwacjaWizyt1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name="wizyta")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate visitday;
    @NotNull
    private LocalTime visithour;

    @ManyToOne
    @JoinColumn(name="doctor_pesel",referencedColumnName = "pesel",nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name ="patient_pesel", referencedColumnName = "pesel", nullable = false)
    private Patient patient;
    private String opis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVisitday() {
        return visitday;
    }

    public void setVisitday(LocalDate visitday) {
        this.visitday = visitday;
    }

    public LocalTime getVisithour() {
        return visithour;
    }

    public void setVisithour(LocalTime visithour) {
        this.visithour = visithour;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
