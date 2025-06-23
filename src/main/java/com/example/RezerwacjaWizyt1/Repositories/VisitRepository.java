package com.example.RezerwacjaWizyt1.Repositories;

import com.example.RezerwacjaWizyt1.Entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit,Long> {
    List<Visit> findByPatientPesel(String pesel);
    List<Visit> findByDoctorPesel(String pesel);
    List<Visit> findByVisitday(LocalDate date);
}
