package com.cardview.demo.repository;

import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.TravelExpenseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelExpenseRepository
			extends CrudRepository<TravelExpenseEntity, Long> {

}
