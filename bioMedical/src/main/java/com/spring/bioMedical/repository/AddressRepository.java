package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.PatientAddressEntity;
import com.spring.bioMedical.model.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<PatientAddressEntity, Long> {

}
