package com.example.RezerwacjaWizyt1.Services;

import com.example.RezerwacjaWizyt1.Entity.Doctor;
import com.example.RezerwacjaWizyt1.Entity.Patient;
import com.example.RezerwacjaWizyt1.Entity.Visit;
import com.example.RezerwacjaWizyt1.Repositories.DoctorRepository;
import com.example.RezerwacjaWizyt1.Repositories.PatientRepository;
import com.example.RezerwacjaWizyt1.Repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository,
                        DoctorRepository doctorRepository,
                        PatientRepository patientRepository) {
        this.visitRepository = visitRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    public Optional<Visit> findById(Long id) {
        return visitRepository.findById(id);
    }

    public Visit saveVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }

    public Optional<Patient> findByPatientPesel(String pesel) {
        return patientRepository.findById(pesel);
    }
    public Optional<Doctor> findByDoctorPesel(String pesel){
        return doctorRepository.findDoctorByPesel(pesel);
    }

    // Supporting doctor/patient lists for form dropdowns
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Optional: prevent overlapping visits for a doctor at the same date/time

}
