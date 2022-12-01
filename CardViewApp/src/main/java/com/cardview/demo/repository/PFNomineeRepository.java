package com.cardview.demo.repository;

import com.cardview.demo.model.PFNomineeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PFNomineeRepository  extends CrudRepository<PFNomineeEntity, Long> {
}
