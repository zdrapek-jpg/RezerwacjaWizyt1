package com.example.RezerwacjaWizyt1.Repositories;

import com.example.RezerwacjaWizyt1.Entity.Doctor;
import com.example.RezerwacjaWizyt1.Entity.SPECJALIZACJE;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Optional<Doctor> findDoctorByPesel(String pesel);

    List<Doctor> findBySpecjalnosc(SPECJALIZACJE specjalnosc);

}
