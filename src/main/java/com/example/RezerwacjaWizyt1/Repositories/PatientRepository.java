package com.example.RezerwacjaWizyt1.Repositories;

import com.example.RezerwacjaWizyt1.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,String> {

    List<Patient> findByName(String name);
}
