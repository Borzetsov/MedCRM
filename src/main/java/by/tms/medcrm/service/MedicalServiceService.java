package by.tms.medcrm.service;

import by.tms.medcrm.dto.MedicalServiceDTO;
import by.tms.medcrm.entity.MedicalProfessionalCategory;
import by.tms.medcrm.entity.MedicalService;
import by.tms.medcrm.entity.MedicalServiceCategory;
import by.tms.medcrm.entity.MedicalServiceId;
import by.tms.medcrm.repository.MedicalProfessionalCategoryRepository;
import by.tms.medcrm.repository.MedicalServiceCategoryRepository;
import by.tms.medcrm.repository.MedicalServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalServiceCategoryRepository medicalServiceCategoryRepository;
    private final MedicalProfessionalCategoryRepository medicalProfessionalCategoryRepository;

    public List<MedicalService> findAll() {
        return medicalServiceRepository.findAll();
    }

    public MedicalService findById(MedicalServiceId medicalServiceId) throws EntityNotFoundException {
        return medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new EntityNotFoundException("Услуга не найдена"));
    }

    public MedicalService create(MedicalServiceDTO medicalServiceDTO) {
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

        return medicalServiceRepository.save(medicalService);
    }

    @Transactional
    public MedicalService update(MedicalService medicalService) {
        MedicalServiceId medicalServiceId = medicalService.getId();
        MedicalService managedMedicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new EntityNotFoundException("Услуга не найдена"));
        managedMedicalService.setName(medicalService.getName());
        managedMedicalService.setPrice(medicalService.getPrice());
        return medicalServiceRepository.save(managedMedicalService);
    }

    public void delete(MedicalServiceId id) throws EntityNotFoundException {
        medicalServiceRepository.deleteById(id);
    }
}
