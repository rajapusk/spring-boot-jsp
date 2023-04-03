package com.spring.bioMedical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.bioMedical.model.DocumentEntity;
import com.spring.bioMedical.repository.DocumentRepository;

@Service
public class DocumentService {
	@Autowired
	DocumentRepository docRepository;
	
	public DocumentEntity updateDocs(DocumentEntity entity) {
		docRepository.save(entity);
		
		return entity;
	}
}
