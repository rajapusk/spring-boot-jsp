package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.AppointmentDoctorEntity;
import com.spring.bioMedical.model.AppointmentServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ksheer Sagar

 *
 */
@Repository
public interface AppointmentServiceRepository extends JpaRepository<AppointmentServiceEntity, Long> {

	
}