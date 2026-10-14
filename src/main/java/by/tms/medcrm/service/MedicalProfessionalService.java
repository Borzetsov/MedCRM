package by.tms.medcrm.service;

import by.tms.medcrm.dto.MedicalProfessionalDTO;
import by.tms.medcrm.entity.*;
import by.tms.medcrm.repository.MedicalProfessionalRepository;
import by.tms.medcrm.util.PasswordUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalProfessionalService {

    private final MedicalProfessionalRepository medicalProfessionalRepository;
    private final MedicalProfessionalCategoryService medicalProfessionalCategoryService;
    private final PasswordUtil passwordUtil;

    public List<MedicalProfessional> findAll() {
        return medicalProfessionalRepository.findAll();
    }

    public List<MedicalProfessional> findAllByCategory(MedicalProfessionalCategory category) {
        return medicalProfessionalRepository.findAllByCategory(category);
    }

    public MedicalProfessional findById(MedicalProfessionalId medicalProfessionalId) throws EntityNotFoundException {
        return medicalProfessionalRepository.findById(medicalProfessionalId)
                .orElseThrow(() -> new EntityNotFoundException("Медицинский работник не найден"));
    }

    public MedicalProfessional create(MedicalProfessionalDTO medicalProfessionalDTO) throws EntityNotFoundException {
        MedicalProfessional medicalProfessional = new MedicalProfessional();

        MedicalProfessionalCategory category = medicalProfessionalCategoryService
                .findByName(medicalProfessionalDTO.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Категория " + medicalProfessionalDTO.getCategory() + "не найдена"));

        medicalProfessional.setFirstName(medicalProfessionalDTO.getFirstName());
        medicalProfessional.setLastName(medicalProfessionalDTO.getLastName());
        medicalProfessional.setCategory(category);

        Account account = new Account();
        account.setRole(Role.ROLE_MEDICAL_PROFESSIONAL);
        account.setLogin("MD_" + medicalProfessionalDTO.getLastName());
        account.setPasswordHash(passwordUtil.hash("123"));

        medicalProfessional.setAccount(account);

        return medicalProfessionalRepository.save(medicalProfessional);
    }

    public MedicalProfessional update(MedicalProfessional medicalProfessional) throws EntityNotFoundException {
        MedicalProfessionalId medicalProfessionalId = medicalProfessional.getId();
        MedicalProfessional managedProfessional = medicalProfessionalRepository.findById(medicalProfessionalId)
                .orElseThrow(() -> new EntityNotFoundException("Медицинский работник не найден"));
        managedProfessional.setFirstName(medicalProfessional.getFirstName());
        managedProfessional.setLastName(medicalProfessional.getLastName());
        return medicalProfessionalRepository.save(managedProfessional);
    }

    public void delete(MedicalProfessionalId id) {
        medicalProfessionalRepository.deleteById(id);
    }
}
