package fr.leo.patientsmvc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.leo.patientsmvc.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	Page<Patient> findByNomContains(String keyword, Pageable pageable);  
}
