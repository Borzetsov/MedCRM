package by.tms.medcrm.service;

import by.tms.medcrm.dto.AccountDTO;
import by.tms.medcrm.entity.Account;
import by.tms.medcrm.entity.Role;
import by.tms.medcrm.repository.AccountRepository;
import by.tms.medcrm.util.PasswordUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordUtil passwordUtil;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + login + "не найден"));
        return new User(account.getLogin(), account.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority(account.getRole().toString())));
    }

    public Account create(AccountDTO accountDTO, Role role) throws IllegalArgumentException {
        if (accountRepository.findByLogin(accountDTO.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Логин занят");
        }
        Account account = new Account();
        account.setLogin(accountDTO.getLogin());
        account.setPasswordHash(passwordUtil.hash(accountDTO.getPassword()));
        account.setRole(role);
        return accountRepository.save(account);
    }

    public Account findByLogin(String login) throws EntityNotFoundException {
        return accountRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден"));
    }
}
