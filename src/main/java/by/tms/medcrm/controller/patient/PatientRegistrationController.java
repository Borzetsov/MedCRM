package by.tms.medcrm.controller.patient;

import by.tms.medcrm.dto.AccountDTO;
import by.tms.medcrm.dto.PatientDTO;
import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.service.PatientRegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients/registration")
public class PatientRegistrationController {

    private final PatientRegistrationService patientRegistrationService;

    @GetMapping
    public String showRegistrationForm() {
        return "patients/registration";
    }

    @PostMapping
    public String createNewPatient(PatientDTO patientDTO,
                                   AccountDTO accountDTO,
                                   HttpServletRequest request) {
        Patient patient = patientRegistrationService.createPatient(patientDTO, accountDTO);
        try {
            request.login(accountDTO.getLogin(), accountDTO.getPassword());
        } catch (ServletException e) {
            return "redirect:/auth/login-error";
        }
        return "redirect:/patients/" + patient.getId();
    }
}
