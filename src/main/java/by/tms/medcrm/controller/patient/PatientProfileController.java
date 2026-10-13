package by.tms.medcrm.controller.patient;

import by.tms.medcrm.entity.Appointment;
import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.service.AppointmentService;
import by.tms.medcrm.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients/profile")
public class PatientProfileController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @GetMapping()
    public String showMainPage(Authentication authentication, Model model) {
        String login = authentication.getName();
        model.addAttribute("patient", patientService.findByLogin(login));
        return "patients/main";
    }

    @GetMapping("/edit")
    public String showEditForm(Authentication authentication, Model model) {
        String login = authentication.getName();
        model.addAttribute("currentPatient", patientService.findByLogin(login));
        return "patients/edit";
    }

    @PostMapping("/update")
    public String updatePatient(@ModelAttribute(name="currentPatient") Patient currentPatient) {
        patientService.update(currentPatient);
        return "redirect:/patients/profile";
    }

    @GetMapping("/appointments")
    public String showAppointments(Authentication authentication, Model model) {
        String login = authentication.getName();
        Patient currentPatient = patientService.findByLogin(login);
        model.addAttribute("appointments", appointmentService.findAllByPatient(currentPatient));
        return "appointments/list";
    }
}
