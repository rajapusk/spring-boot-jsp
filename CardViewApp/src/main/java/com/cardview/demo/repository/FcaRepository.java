package com.cardview.demo.repository;

import com.cardview.demo.model.FcaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcaRepository extends CrudRepository<FcaEntity, Long> {
}
