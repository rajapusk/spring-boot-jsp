package com.cardview.demo.repository;

import com.cardview.demo.model.ExpenseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository
			extends CrudRepository<ExpenseEntity, Long> {

}
