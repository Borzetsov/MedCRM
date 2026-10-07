package by.tms.medcrm.repository;

import by.tms.medcrm.entity.MedicalServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalServiceCategoryRepository extends JpaRepository<MedicalServiceCategory, Long> {
    Optional<MedicalServiceCategory> findByName(String name);
}
