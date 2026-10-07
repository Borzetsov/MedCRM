package by.tms.medcrm.repository;

import by.tms.medcrm.entity.MedicalProfessionalCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalProfessionalCategoryRepository extends JpaRepository<MedicalProfessionalCategory, Long> {
    Optional<MedicalProfessionalCategory> findByName(String name);
}
