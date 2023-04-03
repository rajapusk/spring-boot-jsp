package com.spring.bioMedical.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.spring.bioMedical.model.DocumentEntity;


@Repository
public interface DocumentRepository 
			extends CrudRepository<DocumentEntity, Long> {

}
