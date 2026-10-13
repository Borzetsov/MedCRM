package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.MedicalServiceDTO;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.entity.MedicalService;
import by.tms.medcrm.entity.MedicalServiceId;
import by.tms.medcrm.service.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("administration/medical-services")
public class AdminMedicalServicesController {

    private final MedicalServiceService medicalServiceService;

    @GetMapping()
    public String showAllServices(Model model) {
        model.addAttribute("medicalServices", medicalServiceService.findAll());
        return "medical-services/list";
    }

    @PostMapping
    public String createService(MedicalServiceDTO medicalServiceDTO, Model model) {
        medicalServiceService.create(medicalServiceDTO);
        model.addAttribute("medicalServices", medicalServiceService.findAll());
        return "medical-services/list";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam(name = "serviceId") Long serviceId,
                                 @RequestParam(name = "categoryId") Long categoryId,
                                 Model model) {
        MedicalServiceId medicalServiceId = new MedicalServiceId();
        medicalServiceId.setId(serviceId);
        medicalServiceId.setRequiredMedicalProfessionalCategory(categoryId);

        model.addAttribute("medicalService", medicalServiceService.findById(medicalServiceId));
        model.addAttribute("currentServiceId", serviceId);
        model.addAttribute("currentCategoryId", categoryId);
        return "medical-services/edit";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("medicalService") MedicalService medicalService,
                                 @RequestParam(name = "serviceId") Long serviceId,
                                 @RequestParam(name = "categoryId") Long categoryId,
                                 Model model) {
        medicalService.setId(serviceId);
        MedicalProfessionalCategory category = new MedicalProfessionalCategory();
        category.setId(categoryId);
        medicalService.setRequiredMedicalProfessionalCategory(category);

        medicalServiceService.update(medicalService);
        model.addAttribute("medicalServices", medicalServiceService.findAll());
        return "medical-services/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "serviceId") Long serviceId,
                         @RequestParam(name = "categoryId") Long categoryId,
                         Model model) {
        MedicalServiceId medicalServiceId = new MedicalServiceId();
        medicalServiceId.setId(serviceId);
        medicalServiceId.setRequiredMedicalProfessionalCategory(categoryId);
        medicalServiceService.delete(medicalServiceId);
        model.addAttribute("medicalServices", medicalServiceService.findAll());
        return "medical-services/list";
    }
}
