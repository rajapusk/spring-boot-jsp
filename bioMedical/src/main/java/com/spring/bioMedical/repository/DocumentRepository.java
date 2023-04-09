package com.spring.bioMedical.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.bioMedical.model.DocumentEntity;


@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
	Optional<DocumentEntity> findById(Long Id);
	List<DocumentEntity> findByPageIdAndPage(String pageid, String page);
}
