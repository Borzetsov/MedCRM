package by.tms.medcrm.service;

import by.tms.medcrm.dto.MedicalProfessionalCategoryDTO;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.repository.MedicalProfessionalCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalProfessionalCategoryService {

    private final MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    public List<MedicalProfessionalCategory> findAll() {
        return medicalProfessionalCategoryRepository.findAll();
    }

    public Optional<MedicalProfessionalCategory> findByName(String Name) {
        return medicalProfessionalCategoryRepository.findByName(Name);
    }

    public MedicalProfessionalCategory findById(Long id) throws EntityNotFoundException {
        return medicalProfessionalCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
    }

    public MedicalProfessionalCategory create(MedicalProfessionalCategoryDTO medicalProfessionalCategoryDTO) {
        MedicalProfessionalCategory medicalProfessionalCategory = new MedicalProfessionalCategory();
        medicalProfessionalCategory.setName(medicalProfessionalCategoryDTO.getName());
        return medicalProfessionalCategoryRepository.save(medicalProfessionalCategory);
    }

    public MedicalProfessionalCategory update(MedicalProfessionalCategory medicalProfessionalCategory) {
        return medicalProfessionalCategoryRepository.save(medicalProfessionalCategory);
    }

    public void delete(Long id) {
        medicalProfessionalCategoryRepository.deleteById(id);
    }
}
