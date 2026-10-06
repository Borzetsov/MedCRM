package by.tms.medcrm.controller;

import by.tms.medcrm.dto.AccountDTO;
import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.AccountType;
import by.tms.medcrm.entity.Administrator;
import by.tms.medcrm.repository.AccountRepository;
import by.tms.medcrm.repository.AdministratorRepository;
import by.tms.medcrm.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/registration")
public class RegistrationController {

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AdministratorRepository administratorRepository;

    @GetMapping
    public String reg() {
        return "account/registration";
    }

    @PostMapping
    public String reg(AccountDTO accountDTO) {
        Account account = new Account();
        account.setLogin(accountDTO.getLogin());
        account.setPasswordHash(passwordUtil.hash(accountDTO.getPassword()));
        account.setType(AccountType.ADMINISTRATOR);

        Administrator administrator = new Administrator();
        administrator.setAccount(account);
        administrator.setFirstName("Alex");
        administrator.setLastName("Random");

        administratorRepository.save(administrator);
        return "account/registration";
    }
}
