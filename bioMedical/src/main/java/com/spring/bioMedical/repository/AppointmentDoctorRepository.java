package com.spring.bioMedical.repository;

import com.spring.bioMedical.entity.AppointmentEntity;
import com.spring.bioMedical.model.AppointmentDoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author Ksheer Sagar

 *
 */
@Repository
public interface AppointmentDoctorRepository extends JpaRepository<AppointmentDoctorEntity, Long> {

	
}