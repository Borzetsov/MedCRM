package by.tms.medcrm.service;

import by.tms.medcrm.dto.AdministratorDTO;
import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.Administrator;
import by.tms.medcrm.entity.Role;
import by.tms.medcrm.repository.AdministratorRepository;
import by.tms.medcrm.util.PasswordUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final PasswordUtil passwordUtil;

    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    public Administrator findByLastName(String lastName) throws EntityNotFoundException {
        return administratorRepository.findByLastName(lastName)
                .orElseThrow(() -> new EntityNotFoundException("Администратор " + lastName + "не найден"));
    }

    public Administrator create(AdministratorDTO administratorDTO) throws EntityNotFoundException {
        Administrator administrator = new Administrator();
        administrator.setFirstName(administratorDTO.getFirstName());
        administrator.setLastName(administratorDTO.getLastName());

        Account account = new Account();
        account.setRole(Role.ROLE_ADMINISTRATOR);
        account.setLogin("AD_" + administratorDTO.getLastName());
        account.setPasswordHash(passwordUtil.hash("123"));

        administrator.setAccount(account);

        return administratorRepository.save(administrator);
    }

    public void delete(Long id) throws EntityNotFoundException {
        administratorRepository.deleteById(id);
    }
}
