package by.tms.medcrm.repository;

import by.tms.medcrm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByLogin(String login);
}
