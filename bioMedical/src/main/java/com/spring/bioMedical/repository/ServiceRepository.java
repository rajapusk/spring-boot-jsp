package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.Doctor;
import com.spring.bioMedical.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {


}