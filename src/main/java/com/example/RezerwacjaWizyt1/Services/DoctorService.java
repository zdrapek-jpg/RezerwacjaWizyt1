package com.example.RezerwacjaWizyt1.Services;

import com.example.RezerwacjaWizyt1.Entity.Doctor;
import com.example.RezerwacjaWizyt1.Entity.SPECJALIZACJE;
import com.example.RezerwacjaWizyt1.Repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository =doctorRepository;
    }
    public Doctor addDoctor(Doctor doctor){
        System.out.println(doctor.toString());
        return doctorRepository.save(doctor);
    }
    public List<String> getAllSpecializationNames() {
        return Arrays.stream(SPECJALIZACJE.values())
                .map(SPECJALIZACJE::name) // Get the enum constant name (e.g., "NEUROLOG")
                .collect(Collectors.toList());
    }
    public List<String> getAllSpecializationDescriptions() {
        return Arrays.stream(SPECJALIZACJE.values())
                .map(SPECJALIZACJE::getDescription) // Get the description (e.g., "Neurologia")
                .collect(Collectors.toList());
    }
    public List<SPECJALIZACJE> getAllSpecializations() {
        return Arrays.asList(SPECJALIZACJE.values());
    }

    public Optional<Doctor> findDoctorByPesel(String pesel){
        return doctorRepository.findDoctorByPesel(pesel);
    }

    public void deleteDoctor(String pesel){
        doctorRepository.deleteById(pesel);
    }
    public List<Doctor>  getAllDoctors(){
        return doctorRepository.findAll();
    }

}

