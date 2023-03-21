package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.NextOfKinEntity;
import com.spring.bioMedical.model.PatientAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NextOfKinRepository extends JpaRepository<NextOfKinEntity, Long> {
    public List<NextOfKinEntity> findAllByPatientId(long id);
}
