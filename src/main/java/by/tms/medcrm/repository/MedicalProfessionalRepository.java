package by.tms.medcrm.repository;

import by.tms.medcrm.entity.MedicalProfessional;
import by.tms.medcrm.entity.MedicalProfessionalId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalProfessionalRepository extends JpaRepository<MedicalProfessional, MedicalProfessionalId> {
    Optional<MedicalProfessional> findByLastName(String lastName);
}
