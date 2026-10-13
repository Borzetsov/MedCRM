package by.tms.medcrm.controller;

import by.tms.medcrm.dto.AppointmentDTO;
import by.tms.medcrm.entity.Appointment;
import by.tms.medcrm.repository.*;
import by.tms.medcrm.service.AppointmentService;
import by.tms.medcrm.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalProfessionalRepository medicalProfessionalRepository;
    private final MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    @GetMapping("/book")
    public String showBookForm(Authentication authentication, Model model) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_PATIENT")) {
            String login = authentication.getName();
            model.addAttribute("currentPatient", patientService.findByLogin(login));
        }
        return "appointments/book";
    }

    @PostMapping("/book")
    public String appointmentBook(@Valid AppointmentDTO appointmentDTO) {
        appointmentService.book(appointmentDTO);
        return "redirect:/patients/profile";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam(name = "appointmentId") Long appointmentId) {
        appointmentService.cancel(appointmentId);
        return "redirect:/patients/profile/appointments";
    }
}
