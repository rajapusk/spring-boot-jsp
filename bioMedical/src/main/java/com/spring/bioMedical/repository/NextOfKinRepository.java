package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.NextOfKinEntity;
import com.spring.bioMedical.model.PatientAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NextOfKinRepository extends CrudRepository<NextOfKinEntity, Long> {

}
