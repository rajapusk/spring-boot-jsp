package com.cardview.demo.repository;

import com.cardview.demo.model.NomineeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomineeRepository extends CrudRepository<NomineeEntity, Long> {
}
