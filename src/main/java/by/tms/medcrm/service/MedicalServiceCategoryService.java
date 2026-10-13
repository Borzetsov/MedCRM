package by.tms.medcrm.service;

import by.tms.medcrm.dto.MedicalServiceCategoryDTO;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.repository.MedicalServiceCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceCategoryService {

    private final MedicalServiceCategoryRepository medicalServiceCategoryRepository;

    public List<MedicalServiceCategory> findAll() {
        return medicalServiceCategoryRepository.findAll();
    }

    public MedicalServiceCategory findById(Long id) throws EntityNotFoundException {
        return medicalServiceCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
    }

    public MedicalServiceCategory create(MedicalServiceCategoryDTO medicalServiceCategoryDTO) {
        MedicalServiceCategory medicalServiceCategory = new MedicalServiceCategory();
        medicalServiceCategory.setName(medicalServiceCategoryDTO.getName());
        medicalServiceCategory.setDefaultProfessionalCategory(medicalServiceCategoryDTO.getDefaultProfessionalCategory());
        return medicalServiceCategoryRepository.save(medicalServiceCategory);
    }

    public MedicalServiceCategory update(MedicalServiceCategory medicalServiceCategory) {
        return medicalServiceCategoryRepository.save(medicalServiceCategory);
    }

    public void delete(Long id) {
        medicalServiceCategoryRepository.deleteById(id);
    }

}
