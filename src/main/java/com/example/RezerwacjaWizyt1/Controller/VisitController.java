package com.example.RezerwacjaWizyt1.Controller;

import com.example.RezerwacjaWizyt1.Entity.Doctor;
import com.example.RezerwacjaWizyt1.Entity.Patient;
import com.example.RezerwacjaWizyt1.Entity.Visit;
import com.example.RezerwacjaWizyt1.Services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping({"","/add"})
    public String showAddVisitForm(Model model) {
        model.addAttribute("visit", new Visit()); // Empty form object
        model.addAttribute("visits",visitService.findAll());
        model.addAttribute("doctors", visitService.getAllDoctors()); // Dropdown list
        model.addAttribute("patients", visitService.getAllPatients()); // Dropdown list
        return "visit"; // This must match addVisit.html file in templates
    }
    @PostMapping("/add")
    public String saveVisit(@ModelAttribute Visit visit, RedirectAttributes redirectAttributes) {
        // Ensure that only the PESELs are bound by the form
        // Now fetch full Doctor and Patient entities using their PESELs
        String doctorPesel = visit.getDoctor().getPesel();
        String patientPesel = visit.getPatient().getPesel();

        Optional<Doctor> doctor = visitService.findByDoctorPesel(doctorPesel);
        Optional<Patient> patient = visitService.findByPatientPesel(patientPesel);

        if (doctor.isPresent() && patient.isPresent()) {
            visit.setDoctor(doctor.get());
            visit.setPatient(patient.get());

            visitService.saveVisit(visit);
            redirectAttributes.addFlashAttribute("message", "Wizyta została zapisana.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Nieprawidłowy PESEL lekarza lub pacjenta.");
        }

        return "redirect:/visits";
    }


    @PostMapping("/edit/{id}")
    public String updateVisit(@ModelAttribute Visit visit, RedirectAttributes redirectAttributes) {
        visitService.saveVisit(visit); // JPA will update if ID exists
        redirectAttributes.addFlashAttribute("message", "Wizyta została zaktualizowana.");
        return "redirect:/visits";
    }

    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        visitService.deleteVisit(id);
        redirectAttributes.addFlashAttribute("message", "Wizyta została usunięta.");
        return "redirect:/visits";
    }
}

