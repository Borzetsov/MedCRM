package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.MedicalProfessionalCategoryDTO;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.service.MedicalProfessionalCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("administration/medical-professionals/categories")
public class AdminMedicalProfessionalCategoriesController {

    private final MedicalProfessionalCategoryService medicalProfessionalCategoryService;

    @GetMapping()
    public String showAllCategories(Model model) {
        model.addAttribute("medicalProfessionalCategories", medicalProfessionalCategoryService.findAll());
        return "administration/medical-professionals/categories";
    }

    @PostMapping()
    public String createCategory(MedicalProfessionalCategoryDTO medicalProfessionalCategoryDTO, Model model) {
        medicalProfessionalCategoryService.create(medicalProfessionalCategoryDTO);
        model.addAttribute("medicalProfessionalCategories", medicalProfessionalCategoryService.findAll());
        return "administration/medical-professionals/categories";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("medicalProfessionalCategory", medicalProfessionalCategoryService.findById(id));
        return "administration/medical-professionals/category-edit";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("medicalProfessionalCategory") MedicalProfessionalCategory medicalServiceCategory,
                                 Model model) {
        medicalProfessionalCategoryService.update(medicalServiceCategory);
        model.addAttribute("medicalProfessionalCategories", medicalProfessionalCategoryService.findAll());
        return "administration/medical-professionals/categories";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        medicalProfessionalCategoryService.delete(id);
        model.addAttribute("medicalProfessionalCategories", medicalProfessionalCategoryService.findAll());
        return "administration/medical-professionals/categories";
    }
}
