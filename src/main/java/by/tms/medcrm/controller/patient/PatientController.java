package by.tms.medcrm.controller.patient;

import by.tms.medcrm.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public String showMainPage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("patient", patientService.loadPatient(id));
        return "patients/main";
    }
}
