package by.tms.medcrm.service;

import by.tms.medcrm.dto.AccountDTO;
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
    private final AccountService accountService;
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

    public MedicalProfessional findByLogin(String login) throws EntityNotFoundException {
        Account account = accountService.findByLogin(login);
        if (account == null) {
            throw new EntityNotFoundException("Аккаунт не найден");
        }
        return medicalProfessionalRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));
    }

    public MedicalProfessional create(MedicalProfessionalDTO medicalProfessionalDTO,
                                      AccountDTO accountDTO) throws EntityNotFoundException {
        MedicalProfessional medicalProfessional = new MedicalProfessional();

        MedicalProfessionalCategory category = medicalProfessionalCategoryService
                .findByName(medicalProfessionalDTO.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Категория " + medicalProfessionalDTO.getCategory() + "не найдена"));

        medicalProfessional.setFirstName(medicalProfessionalDTO.getFirstName());
        medicalProfessional.setLastName(medicalProfessionalDTO.getLastName());
        medicalProfessional.setCategory(category);

        Account account = new Account();
        account.setRole(Role.ROLE_MEDICAL_PROFESSIONAL);
        account.setLogin(accountDTO.getLogin());
        account.setPasswordHash(passwordUtil.hash(accountDTO.getPassword()));

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
