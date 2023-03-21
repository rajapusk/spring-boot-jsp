package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.Doctor;
import com.spring.bioMedical.model.PatientAddressEntity;
import com.spring.bioMedical.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<PatientAddressEntity, Long> {
    public List<PatientAddressEntity> findAllByPatientId(long id);
}
