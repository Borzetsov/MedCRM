package by.tms.medcrm.controller.administration;

import by.tms.medcrm.dto.MedicalServiceCategoryDTO;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.service.MedicalServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("administration/medical-services/categories")
public class AdminMedicalServiceCategoriesController {

    private final MedicalServiceCategoryService medicalServiceCategoryService;

    @GetMapping()
    public String showAllCategories(Model model) {
        model.addAttribute("medicalServiceCategories", medicalServiceCategoryService.findAll());
        return "medical-services/categories";
    }

    @PostMapping
    public String createCategory(MedicalServiceCategoryDTO medicalServiceCategoryDTO, Model model) {
        medicalServiceCategoryService.create(medicalServiceCategoryDTO);
        model.addAttribute("medicalServiceCategories", medicalServiceCategoryService.findAll());
        return "medical-services/categories";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("medicalServiceCategory", medicalServiceCategoryService.findById(id));
        return "medical-services/category-edit";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("medicalServiceCategory") MedicalServiceCategory medicalServiceCategory,
                                 Model model) {
        medicalServiceCategoryService.update(medicalServiceCategory);
        model.addAttribute("medicalServiceCategories", medicalServiceCategoryService.findAll());
        return "medical-services/categories";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        medicalServiceCategoryService.delete(id);
        model.addAttribute("medicalServiceCategories", medicalServiceCategoryService.findAll());
        return "medical-services/categories";
    }
}
