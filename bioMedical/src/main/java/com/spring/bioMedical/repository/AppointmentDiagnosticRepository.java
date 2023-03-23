package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.AppointmentDiagnosticEntity;
import com.spring.bioMedical.model.AppointmentDoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ksheer Sagar

 *
 */
@Repository
public interface AppointmentDiagnosticRepository extends JpaRepository<AppointmentDiagnosticEntity, Long> {

	
}