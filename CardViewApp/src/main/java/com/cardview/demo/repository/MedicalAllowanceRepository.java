package com.cardview.demo.repository;

import com.cardview.demo.model.MedicalAllowanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalAllowanceRepository  extends CrudRepository<MedicalAllowanceEntity, Long> {
}
