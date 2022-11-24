package com.cardview.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cardview.demo.model.PFLoanEntity;

@Repository
public interface PFLoanRepository 
			extends CrudRepository<PFLoanEntity, Long> {

}
