package com.cardview.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cardview.demo.model.PFAccountEntity;

@Repository
public interface PFAccountRepository 
			extends CrudRepository<PFAccountEntity, Long> {

}
