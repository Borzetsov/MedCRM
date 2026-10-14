package by.tms.medcrm.controller.appointments;

import by.tms.medcrm.dto.AppointmentDTO;
import by.tms.medcrm.entity.MedicalProfessional;
import by.tms.medcrm.entity.MedicalService;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.entity.MedicalServiceId;
import by.tms.medcrm.repository.MedicalProfessionalRepository;
import by.tms.medcrm.service.AppointmentService;
import by.tms.medcrm.service.MedicalServiceCategoryService;
import by.tms.medcrm.service.MedicalServiceService;
import by.tms.medcrm.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final MedicalServiceCategoryService medicalServiceCategoryService;
    private final MedicalServiceService medicalServiceService;
    private final MedicalProfessionalRepository medicalProfessionalRepository;

    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("medicalServiceCategories", medicalServiceCategoryService.findAll());
        return "appointments/categories";
    }

    @GetMapping("/services/{categoryId}")
    public String showServices(@PathVariable Long categoryId, Model model) {
        MedicalServiceCategory medicalServiceCategory = medicalServiceCategoryService.findById(categoryId);
        model.addAttribute("medicalServices", medicalServiceService.findAllByCategory(medicalServiceCategory));
        return "appointments/services";
    }

    @GetMapping("/book")
    public String showBookForm(@RequestParam(name = "serviceId") Long serviceId,
                               @RequestParam(name="categoryId") Long categoryId,
                               Authentication authentication,
                               Model model) {
        MedicalServiceId medicalServiceId = new MedicalServiceId();
        medicalServiceId.setId(serviceId);
        medicalServiceId.setRequiredMedicalProfessionalCategory(categoryId);
        MedicalService medicalService = medicalServiceService.findById(medicalServiceId);
        model.addAttribute("medicalService", medicalService);

        List<MedicalProfessional> medicalProfessionals = medicalProfessionalRepository
                .findAllByCategory(medicalService.getRequiredMedicalProfessionalCategory());
        model.addAttribute("medicalProfessionals", medicalProfessionals);

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
