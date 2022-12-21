package com.cardview.demo.repository;

import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.NewspaperAllowanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewspaperAllowanceRepository
        extends CrudRepository<NewspaperAllowanceEntity, Long>  {
}
