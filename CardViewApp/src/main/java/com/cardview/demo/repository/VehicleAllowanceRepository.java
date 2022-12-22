package com.cardview.demo.repository;

import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.VehicleAllowanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleAllowanceRepository
        extends CrudRepository<VehicleAllowanceEntity, Long>  {
}
