package com.cardview.demo.repository;

import com.cardview.demo.model.VpfContributionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VpfRepository
        extends CrudRepository<VpfContributionEntity, Long> {

}
