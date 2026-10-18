package by.tms.medcrm.controller.appointments;

import by.tms.medcrm.dto.AppointmentDTO;
import by.tms.medcrm.entity.*;
import by.tms.medcrm.repository.MedicalProfessionalRepository;
import by.tms.medcrm.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final MedicalServiceCategoryService medicalServiceCategoryService;
    private final MedicalServiceService medicalServiceService;
    private final MedicalProfessionalRepository medicalProfessionalRepository;
    private final MedicalProfessionalService medicalProfessionalService;

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

    @PostMapping("/services")
    public String showService(@RequestParam(name = "medicalServiceName") String medicalServiceName,
                              Authentication authentication, Model model) {
        MedicalService service = medicalServiceService.findByName(medicalServiceName);
        List<Map<LocalDate, Set<Integer>>> timeslotsForWeek = new ArrayList<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            timeslotsForWeek.add(new HashMap<>());
            timeslotsForWeek.get(i).put(date, appointmentService.getAvailableTimeslotsOnDate(service, date));
            date = date.plusDays(1);
        }
        model.addAttribute("timeslotsForWeek", timeslotsForWeek);
        model.addAttribute("medicalService", service);

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_PATIENT")) {
            String login = authentication.getName();
            model.addAttribute("currentPatient", patientService.findByLogin(login));
        }
        return "appointments/book";
    }

    @GetMapping("/book")
    public String showBookForm(@RequestParam(name = "serviceId") Long serviceId,
                               @RequestParam(name = "categoryId") Long categoryId,
                               @RequestParam(name = "timeslot") Integer timeslot,
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
    public String appointmentBook(@RequestParam(name = "serviceId") Long serviceId,
                                  @RequestParam(name = "categoryId") Long categoryId,
                                  @RequestParam(name = "appointmentDate") LocalDate appointmentDate,
                                  @RequestParam(name = "timeslot") Integer timeslot,
                                  Authentication authentication,
                                  Model model) {

        MedicalServiceId medicalServiceId = new MedicalServiceId();
        medicalServiceId.setId(serviceId);
        medicalServiceId.setRequiredMedicalProfessionalCategory(categoryId);
        MedicalService medicalService = medicalServiceService.findById(medicalServiceId);

        Patient patient = patientService.findByLogin(authentication.getName());
        MedicalProfessional medicalProfessional =
                appointmentService.findProfessional(medicalService.getRequiredMedicalProfessionalCategory(),
                        appointmentDate, timeslot)
                        .orElseThrow(() -> new EntityNotFoundException("Нет доступных специалистов"));

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setPatientLastName(patient.getLastName());
        appointmentDTO.setMedicalService(medicalService.getName());
        appointmentDTO.setMedicalProfessionalLastName(medicalProfessional.getLastName());
        appointmentDTO.setMedicalProfessionalCategory(medicalService
                .getRequiredMedicalProfessionalCategory().getName());
        appointmentDTO.setAppointmentDate(appointmentDate);
        appointmentDTO.setTimeslot(timeslot);

        appointmentService.book(appointmentDTO);
        return "redirect:/patients/profile";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam(name = "appointmentId") Long appointmentId) {
        appointmentService.cancel(appointmentId);
        return "redirect:/patients/profile/appointments";
    }
}
