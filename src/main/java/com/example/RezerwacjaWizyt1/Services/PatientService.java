package com.example.RezerwacjaWizyt1.Services;

import com.example.RezerwacjaWizyt1.Entity.Patient;
import com.example.RezerwacjaWizyt1.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository =patientRepository;
    }
    public Patient save(Patient patient){
        return patientRepository.save(patient);
    }
    public void deletePatient(String pesel){
        patientRepository.deleteById(pesel);
    }
    public Optional<Patient> findById(String pesel){
        return patientRepository.findById(pesel);
    }
    public List<Patient> findByName(String name){
        List<Patient> optlist= patientRepository.findByName(name);
       return   optlist==null ? Collections.emptyList():optlist;
    }
    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

}
