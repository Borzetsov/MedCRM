package by.tms.medcrm.repository;

import by.tms.medcrm.entity.MedicalProfessional;
import by.tms.medcrm.entity.MedicalProfessionalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalProfessionalRepository extends JpaRepository<MedicalProfessional, MedicalProfessionalId> {
}
