package com.cardview.demo.repository;

import com.cardview.demo.model.TravelExpenseDetailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelExpenseDetailRepository
			extends CrudRepository<TravelExpenseDetailEntity, Long> {

}
