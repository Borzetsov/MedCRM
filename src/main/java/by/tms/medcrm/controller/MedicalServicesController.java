package by.tms.medcrm.controller;

import by.tms.medcrm.service.MedicalServiceCategoryService;
import by.tms.medcrm.service.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("medical-services")
public class MedicalServicesController {

    private final MedicalServiceCategoryService medicalServiceCategoryService;
    private final MedicalServiceService medicalServiceService;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("medicalServices", medicalServiceService.findAll());
        return "medical-services/list";
    }

    @GetMapping("/categories")
    public String showAllCategories(Model model) {
        model.addAttribute("medicalServiceCategories", medicalServiceCategoryService.findAll());
        return "medical-services/categories";
    }
}
