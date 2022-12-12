package com.cardview.demo.repository;

import com.cardview.demo.model.BriefcaseAllowanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefcaseAllowanceRepository
        extends CrudRepository<BriefcaseAllowanceEntity, Long>  {
}
