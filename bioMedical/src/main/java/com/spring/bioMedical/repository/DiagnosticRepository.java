package com.spring.bioMedical.repository;

import com.spring.bioMedical.model.DiagnosticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticRepository extends JpaRepository<DiagnosticEntity, Integer> {

}