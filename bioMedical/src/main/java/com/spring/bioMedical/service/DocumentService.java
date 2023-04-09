package com.spring.bioMedical.service;

import java.util.List;
import java.util.Optional;

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

	public List<DocumentEntity> findByPageIdAndPage(String pageId, String page) {
		return docRepository.findByPageIdAndPage(pageId, page);
	}
}
