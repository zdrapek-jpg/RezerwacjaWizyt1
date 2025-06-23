package com.example.RezerwacjaWizyt1.Controller;

import com.example.RezerwacjaWizyt1.Entity.Doctor;
import com.example.RezerwacjaWizyt1.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Handles GET requests to /doctors or /doctors/add.
     * Displays the form to add a new doctor and the list of existing doctors.
     */
    @GetMapping({"", "/add"}) // Maps to both /doctors and /doctors/add
    public String showAddDoctorFormAndList(Model model) {
        model.addAttribute("doctor", new Doctor()); // Empty Doctor object for the add form
        model.addAttribute("specializations", doctorService.getAllSpecializations()); // List of enum instances for dropdown
        model.addAttribute("doctors", doctorService.getAllDoctors()); // List of existing doctors for the table
        return "add_doctor"; // Refers to the Thymeleaf template file: addDoctor.html
    }

    /**
     * Processes the form submission to add a new doctor.
     * Handles POST requests to /doctors/add.
     */
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctor") Doctor doctor, RedirectAttributes redirectAttributes) {
        doctorService.addDoctor(doctor);
        redirectAttributes.addFlashAttribute("message", "Lekarz został pomyślnie dodany!");
        return "redirect:/doctors"; // Redirects to GET /doctors to refresh the page with the new list
    }

    /**
     * Displays the form to edit an existing doctor.
     * Handles GET requests to /doctors/edit/{id}.
     */
    @GetMapping("/edit/{pesel}")
    public String showEditDoctorForm(@PathVariable String pesel, Model model, RedirectAttributes redirectAttributes) {
        Optional<Doctor> doctorOptional = doctorService.findDoctorByPesel(pesel);
        if (doctorOptional.isPresent()) {
            model.addAttribute("doctor", doctorOptional.get()); // Pre-fill form with existing doctor data
            model.addAttribute("specializations", doctorService.getAllSpecializations());
            model.addAttribute("doctors", doctorService.getAllDoctors()); // Optional: show list in background
            return "editDoctor"; // Use existing form template
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Lekarz o podanym PESELu nie został znaleziony.");
            return "redirect:/doctors";
        }
    }

    @PostMapping("/edit/{pesel}")
    public String updateDoctor(@RequestParam("oryginalPesel")String OryginalPesel, @ModelAttribute("doctor") Doctor doctor, RedirectAttributes redirectAttributes) {
        // Prefer using PESEL to uniquely identify doctor
        Optional<Doctor> optionalDoctor = doctorService.findDoctorByPesel(OryginalPesel);

        if (optionalDoctor.isPresent()) {
            Doctor existingDoctor = optionalDoctor.get();
            doctorService.deleteDoctor(OryginalPesel);

            doctorService.addDoctor(doctor); // Save changes (rename to saveDoctor if more semantically correct)

            redirectAttributes.addFlashAttribute("successMessage", "Dane lekarza zostały pomyślnie zaktualizowane.");

        }

        redirectAttributes.addFlashAttribute("errorMessage", "Nie znaleziono lekarza do aktualizacji.");
        return "redirect:/doctors"; // Redirect even on failure, following Post-Redirect-Get pattern
    }


    /**
     * Processes the form submission to update an existing doctor.
     * Handles POST requests to /doctors/edit.
     */

    /**
     * Deletes a doctor by ID.
     * Handles GET requests to /doctors/delete/{id}. (Consider POST for actual deletion in production)
     */
    @GetMapping("/delete/{pesel}") // For production, consider using @PostMapping or a more secure method for deletion
    public String deleteDoctor(@PathVariable String pesel, RedirectAttributes redirectAttributes) {
        doctorService.deleteDoctor(pesel);
        return "redirect:/doctors"; // Redirect to the list page
    }
}
