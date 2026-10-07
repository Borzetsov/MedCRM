package by.tms.medcrm.controller;

import by.tms.medcrm.dto.MedicalProfessionalCategoryDTO;
import by.tms.medcrm.dto.MedicalServiceCategoryDTO;
import by.tms.medcrm.dto.MedicalServiceDTO;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.entity.MedicalService;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.repository.MedicalProfessionalCategoryRepository;
import by.tms.medcrm.repository.MedicalServiceCategoryRepository;
import by.tms.medcrm.repository.MedicalServiceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

    @Autowired
    MedicalServiceCategoryRepository medicalServiceCategoryRepository;

    @Autowired
    MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    @Autowired
    MedicalServiceRepository medicalServiceRepository;

    @GetMapping("/medical_service_categories")
    public String medical_service_categories() {
        return "administration/medical_service_categories";
    }

    @PostMapping("/medical_service_categories")
    public String medical_service_categories(MedicalServiceCategoryDTO medicalServiceCategoryDTO) {
        MedicalServiceCategory medicalServiceCategory = new MedicalServiceCategory();
        medicalServiceCategory.setName(medicalServiceCategoryDTO.getName());
        medicalServiceCategoryRepository.save(medicalServiceCategory);
        return "administration/medical_service_categories";
    }

    @GetMapping("/medical_services")
    public String medical_service() {
        return "administration/medical_services";
    }

    @PostMapping("/medical_services")
    public String medical_service(@Valid MedicalServiceDTO medicalServiceDTO) {
        MedicalService medicalService = new MedicalService();
        medicalService.setName(medicalServiceDTO.getName());
        medicalService.setPrice(medicalServiceDTO.getPrice());

        Optional<MedicalServiceCategory> medicalServiceCategory = medicalServiceCategoryRepository
                .findByName(medicalServiceDTO.getCategory());
        if (medicalServiceCategory.isPresent()) {
            medicalService.setCategory(medicalServiceCategory.get());
        }

        Optional<MedicalProfessionalCategory> medicalProfessionalCategory = medicalProfessionalCategoryRepository
                .findByName(medicalServiceDTO.getRequiredMedicalProfessionalCategory());
        if (medicalProfessionalCategory.isPresent()) {
            medicalService.setRequiredMedicalProfessionalCategory(medicalProfessionalCategory.get());
        }

        medicalServiceRepository.save(medicalService);
        return "administration/medical_services";
    }

    @GetMapping("/medical_professionals_categories")
    public String medical_professionals_categories() {
        return "administration/medical_professionals_categories";
    }

    @PostMapping("/medical_professionals_categories")
    public String medical_professionals_categories(MedicalProfessionalCategoryDTO medicalProfessionalCategoryDTO) {
        MedicalProfessionalCategory medicalProfessionalCategory = new MedicalProfessionalCategory();
        medicalProfessionalCategory.setName(medicalProfessionalCategoryDTO.getName());
        medicalProfessionalCategoryRepository.save(medicalProfessionalCategory);
        return "administration/medical_professionals_categories";
    }
}
