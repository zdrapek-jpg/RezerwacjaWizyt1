package com.example.RezerwacjaWizyt1.Controller;

import com.example.RezerwacjaWizyt1.Entity.Doctor;
import com.example.RezerwacjaWizyt1.Entity.Patient;
import com.example.RezerwacjaWizyt1.Entity.Visit;
import com.example.RezerwacjaWizyt1.Services.EmailSenderService;
import com.example.RezerwacjaWizyt1.Services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public VisitController(VisitService visitService,EmailSenderService emailSenderService) {
        this.visitService = visitService;
        this.emailSenderService=emailSenderService;
    }

    @GetMapping({"", "/add"})
    public String showAddVisitForm(Model model) {
        model.addAttribute("visit", new Visit());
        model.addAttribute("visits", visitService.findAll());
        model.addAttribute("doctors", visitService.getAllDoctors());
        model.addAttribute("patients", visitService.getAllPatients());
        return "visit";
    }

    @PostMapping("/add")
    public String saveVisit(@ModelAttribute Visit visit, RedirectAttributes redirectAttributes) {
        String doctorPesel = visit.getDoctor().getPesel();
        String patientPesel = visit.getPatient().getPesel();

        // Use injected services for finding Doctor and Patient
        Optional<Doctor> doctorOpt = visitService.findByDoctorPesel(doctorPesel);
        Optional<Patient> patientOpt = visitService.findByPatientPesel(patientPesel);

        if (doctorOpt.isPresent() && patientOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            Patient patient = patientOpt.get();

            visit.setDoctor(doctor); // Set the managed Doctor entity
            visit.setPatient(patient); // Set the managed Patient entity

            try {
                // 1. Load the HTML template content
                Resource resource = new ClassPathResource("templates/visitConfirmationEmail.html");
                String htmlTemplate = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

                // 2. Populate placeholders for Doctor's email
                String doctorEmailBody = htmlTemplate
                        .replace("#USER_NAME#", doctor.getName() + " " + doctor.getSurname())
                        .replace("#VISIT_DATE#", visit.getVisitday() != null ? visit.getVisitday().toString() : "N/A") // Format date as needed
                        .replace("#VISIT_TIME#", visit.getVisithour() != null ? visit.getVisithour().toString() : "N/A") // Format time as needed
                        .replace("#DOCTOR_NAME#", doctor.getName())
                        .replace("#DOCTOR_SURNAME#", doctor.getSurname())
                        .replace( "#DOCTOR_SPECIALIZATION#", doctor.getSpecjalnosc() != null ? doctor.getSpecjalnosc().toString() : "N/A")
                        .replace("#DOCTOR_PESEL#", doctor.getPesel())
                        .replace("#PATIENT_NAME#", patient.getName())
                        .replace("#PATIENT_SURNAME#", patient.getSurname())
                        .replace("#PATIENT_PESEL#", patient.getPesel());


                // 3. Populate placeholders for Patient's email (can be similar or slightly different)
                String patientEmailBody = htmlTemplate
                        .replace("#USER_NAME#", patient.getName() + " " + patient.getSurname())
                        .replace("#VISIT_DATE#", visit.getVisitday() != null ? visit.getVisitday().toString() : "N/A")
                        .replace("#VISIT_TIME#", visit.getVisithour() != null ? visit.getVisithour().toString() : "N/A")
                        .replace("#DOCTOR_NAME#", doctor.getName())
                        .replace("#DOCTOR_SURNAME#", doctor.getSurname())
                        .replace("#DOCTOR_SPECIALIZATION#", doctor.getSpecjalnosc()!= null ? doctor.getSpecjalnosc().toString() : "N/A")
                        .replace("#DOCTOR_PESEL#", doctor.getPesel())
                        .replace("#PATIENT_NAME#", patient.getName())
                        .replace("#PATIENT_SURNAME#", patient.getSurname())
                        .replace("#PATIENT_PESEL#", patient.getPesel());


                // Send email to doctor
                if (doctor.getEmail() != null && !doctor.getEmail().isEmpty()) {
                    emailSenderService.SendConfirmationDoctorVisit(
                            doctor.getEmail(),
                            "Potwierdzenie wizyty pacjenta dnia " + visit.getVisitday(),
                            doctorEmailBody
                    );
                } else {
                    redirectAttributes.addFlashAttribute("warning", "Email lekarza nie jest dostępny. Wizyta została zapisana, ale powiadomienie nie zostało wysłane.");
                }

                // Send email to patient
                if (patient.getEmail() != null && !patient.getEmail().isEmpty()) {
                    emailSenderService.SendConfirmationDoctorVisit(
                            patient.getEmail(),
                            "Potwierdzenie wizyty u lekarza dnia " + visit.getVisitday(),
                            patientEmailBody
                    );
                } else {
                    redirectAttributes.addFlashAttribute("warning", "Email pacjenta nie jest dostępny. Wizyta została zapisana, ale powiadomienie nie zostało wysłane.");
                }

                visitService.saveVisit(visit); // Save the visit after email attempts
                redirectAttributes.addFlashAttribute("message", "Wizyta została zapisana. Powiadomienia email wysłane.");

            } catch (MailException | IOException e) {
                // Handle email sending errors
                redirectAttributes.addFlashAttribute("error", "Wizyta została zapisana, ale wystąpił błąd podczas wysyłania powiadomień email: " + e.getMessage());
                visitService.saveVisit(visit); // Save the visit even if email failed
            }

        } else {
            redirectAttributes.addFlashAttribute("error", "Nieprawidłowy PESEL lekarza lub pacjenta. Wizyta nie została zapisana.");
        }

        return "redirect:/visits";
    }



    @PostMapping("/edit/{id}")
    public String updateVisit(@ModelAttribute Visit visit, RedirectAttributes redirectAttributes) {
        visitService.saveVisit(visit); // JPA will update if ID exists
        redirectAttributes.addFlashAttribute("message", "Wizyta została zaktualizowana.");
        return "redirect:/visits";
    }
    @GetMapping("/edit/{id}")
    public String setVisit(@PathVariable Long id,Model model, RedirectAttributes redirectAttributes){
        Optional<Visit> visitOptional = visitService.findById(id);
        if (visitOptional.isPresent()){
            model.addAttribute("doctors", visitService.getAllDoctors());
            model.addAttribute("patients",visitService.getAllPatients());
            model.addAttribute("visit", visitOptional.get());
            return "editVisit"; // musi się zgadzać z nazwą pliku HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Wizyta nie została znaleziona.");
            return "redirect:/visits";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        visitService.deleteVisit(id);
        redirectAttributes.addFlashAttribute("message", "Wizyta została usunięta.");
        return "redirect:/visits";
    }
}

