package by.tms.medcrm.controller.patient;

import by.tms.medcrm.dto.RegistrationDTO;
import by.tms.medcrm.entity.Patient;
import by.tms.medcrm.service.AccountService;
import by.tms.medcrm.service.PatientRegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients/registration")
public class PatientsRegistrationController {

    private final PatientRegistrationService patientRegistrationService;
    private final AccountService accountService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "patients/registration";
    }

    @PostMapping
    public String createNewPatient(@ModelAttribute("registrationDTO") @Valid RegistrationDTO registrationDTO,
                                   BindingResult bindingResult, HttpServletRequest request) {
        if (accountService.existsByLogin(registrationDTO.getNewAccountDTO().getLogin())) {
            bindingResult.rejectValue("newAccountDTO.login", "error.login.exists", "Логин занят");
        }
        if (patientRegistrationService.existsByEmail(registrationDTO.getNewPatientDTO().getEmail())) {
            bindingResult.rejectValue("newPatientDTO.email", "error.email.exists", "Электронная почта занята");
        }
        if (patientRegistrationService.existsByPhone(registrationDTO.getNewPatientDTO().getPhone())) {
            bindingResult.rejectValue("newPatientDTO.phone", "error.phone.exists", "Номер телефона занят");
        }
        if (bindingResult.hasErrors()) {
            return "patients/registration";
        }
        Patient patient = patientRegistrationService.createPatient(registrationDTO);
        try {
            request.login(registrationDTO.getNewAccountDTO().getLogin(), registrationDTO.getNewAccountDTO().getPassword());
        } catch (ServletException e) {
            return "redirect:/auth/login-error";
        }
        return "redirect:/patients/profile";
    }
}
