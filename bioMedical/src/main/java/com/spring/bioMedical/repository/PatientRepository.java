package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {

}
