package com.example.RezerwacjaWizyt1.Controller;

import com.example.RezerwacjaWizyt1.Entity.Patient;
import com.example.RezerwacjaWizyt1.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService){
        this.patientService= patientService;
    }

    @PostMapping("/add")
    public String form_patient(@ModelAttribute("patient") Patient patient, Model model, RedirectAttributes redirectAttributes ){
        patientService.save(patient);
        redirectAttributes.addFlashAttribute("message", "Lekarz został pomyślnie dodany!");
        return "redirect:/patients";

    }
    @GetMapping({"","/add"})
    public String showAddDoctorFormAndList(Model model ){
        model.addAttribute("patient", new Patient()); // Empty Doctor object for the add form
        model.addAttribute("patients", patientService.findAll()); // List of existing doctors for the table
        return "add_patient"; // Refers to the Thymeleaf template file: addDoctor.html

    }
    @GetMapping("/delete/{pesel}") // For production, consider using @PostMapping or a more secure method for deletion
    public String deleteDoctor(@PathVariable String pesel) {
        patientService.deletePatient(pesel);
        return "redirect:/patients"; // Redirect to the list page
    }
    @GetMapping("/edit/{pesel}")
    public String showEditPatientForm(@PathVariable String pesel, Model model, RedirectAttributes redirectAttributes){
        Optional<Patient> optionalPatient= patientService.findById(pesel);
        if (optionalPatient.isPresent()){
            model.addAttribute("patient" ,optionalPatient.get());
            model.addAttribute("patients",patientService.findAll());
            return "editPatient";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Lekarz o podanym PESELu nie został znaleziony.");
            return "redirect:/patients";
        }
    }
    @PostMapping("/edit/{pesel}")
    public String UpdateEditform( @RequestParam("originalPesel") String originalPesel,
                                  @ModelAttribute("patient") Patient patient,
                                  RedirectAttributes redirectAttributes){
        Optional<Patient> optionalPatient = patientService.findById(originalPesel);
        if (optionalPatient.isPresent()) {
            // Delete the old patient record
            patientService.deletePatient(originalPesel);

            // Save the new one (with updated PESEL)
            patientService.save(patient);

            redirectAttributes.addFlashAttribute("successMessage", "Dane pacjenta zostały zaktualizowane.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie znaleziono pacjenta do aktualizacji.");
        }

        return "redirect:/patients";
    }



}
