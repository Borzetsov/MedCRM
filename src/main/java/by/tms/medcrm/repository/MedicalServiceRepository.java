package by.tms.medcrm.repository;

import by.tms.medcrm.entity.MedicalService;
import by.tms.medcrm.entity.MedicalServiceId;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, MedicalServiceId> {
    Optional<MedicalService> findByName(String name);
}
