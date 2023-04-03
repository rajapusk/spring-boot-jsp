package com.spring.bioMedical.repository;

import java.util.List;

import com.spring.bioMedical.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ksheer Sagar

 *
 */
@Repository
public interface AppointmentRepositoryNew extends JpaRepository<AppointmentEntity, Long> {
    public List<AppointmentEntity> findAllByPatientId(long id);
	
}