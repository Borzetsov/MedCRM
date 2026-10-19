package by.tms.medcrm.controller;

import by.tms.medcrm.entity.Appointment;
import by.tms.medcrm.entity.AppointmentStatusType;
import by.tms.medcrm.entity.MedicalProfessional;
import by.tms.medcrm.service.AppointmentService;
import by.tms.medcrm.service.MedicalProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medical-professionals")
public class MedicalProfessionalController {

    private final MedicalProfessionalService medicalProfessionalService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String profile(Authentication authentication, Model model) {
        String login = authentication.getName();
        model.addAttribute("medicalProfessional", medicalProfessionalService.findByLogin(login));
        return "administration/medical-professionals/main";
    }

    @GetMapping("/appointments")
    public String appointments(Authentication authentication, Model model) {
        String login = authentication.getName();
        MedicalProfessional professional = medicalProfessionalService.findByLogin(login);
        model.addAttribute("medicalProfessional", professional);
        model.addAttribute("appointments", appointmentService.findAllByMedicalProfessional(professional));
        return "administration/medical-professionals/appointments";
    }

    @GetMapping("/appointments/{id}")
    public String appointments(Authentication authentication,
                               @PathVariable Long id, Model model) {
        String login = authentication.getName();
        MedicalProfessional professional = medicalProfessionalService.findByLogin(login);
        model.addAttribute("medicalProfessional", professional);
        model.addAttribute("appointment", appointmentService.findById(id));
        return "appointments/card";
    }

    @PostMapping("/appointments/{id}")
    public String setAppointmentStatus(@PathVariable Long id,
                                       @RequestParam(name = "status") AppointmentStatusType status,
                                       Authentication authentication, Model model) {
        Appointment appointment = appointmentService.findById(id);
        appointment.setStatus(status);
        appointmentService.update(appointment);
        return "redirect:/medical-professionals/appointments";
    }
}
