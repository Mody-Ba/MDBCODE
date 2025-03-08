package net.patient.patient.repository;

import net.patient.patient.entite.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient ,Long> {
}
