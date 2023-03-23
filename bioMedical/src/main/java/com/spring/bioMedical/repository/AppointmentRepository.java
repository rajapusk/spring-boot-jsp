package com.spring.bioMedical.repository;

import java.util.List;

import com.spring.bioMedical.entity.AppointmentEntity;
import com.spring.bioMedical.model.PatientAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bioMedical.entity.Appointment;
import com.spring.bioMedical.entity.User;

/**
 * 
 * @author Ksheer Sagar

 *
 */
@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    public List<AppointmentEntity> findAllByPatientId(long id);
	
}