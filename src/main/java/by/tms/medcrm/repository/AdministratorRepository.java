package by.tms.medcrm.repository;

import by.tms.medcrm.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
    Optional<Administrator> findByLastName(String lastName);
}
