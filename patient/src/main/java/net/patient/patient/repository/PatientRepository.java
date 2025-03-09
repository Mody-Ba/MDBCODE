package net.patient.patient.repository;

import net.patient.patient.entite.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient ,Long> {
    Page<Patient> findByNomContainsIgnoreCase(String keyword ,Pageable pageable);
}
