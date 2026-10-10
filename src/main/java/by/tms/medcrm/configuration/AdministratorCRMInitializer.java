package by.tms.medcrm.configuration;

import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.Role;
import by.tms.medcrm.repository.AccountRepository;
import by.tms.medcrm.util.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdministratorCRMInitializer {

    @Value("${ADMINISTRATOR_CRM_LOGIN}")
    private String login;

    @Value("${ADMINISTRATOR_CRM_PASSWORD}")
    private String password;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordUtil passwordUtil;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initialize() {
        Optional<Account> optionalAccount = accountRepository.findByRole(Role.ROLE_ADMINISTRATOR_CRM);
        if (optionalAccount.isEmpty()) {
            Account account = new Account();
            account.setLogin(login);
            account.setPasswordHash(passwordUtil.hash(password));
            account.setRole(Role.ROLE_ADMINISTRATOR_CRM);
            accountRepository.save(account);
        }
    }
}
