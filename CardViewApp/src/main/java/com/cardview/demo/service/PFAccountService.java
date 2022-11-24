package com.cardview.demo.service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cardview.demo.model.EmployeeEntity;
import com.cardview.demo.model.MyImage;
import com.cardview.demo.model.PFAccountEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.repository.PFAccountRepository;

@Service
public class PFAccountService {
	
	@Value("${spring.project.directory}")
	private String targetPath;

	@Autowired
	PFAccountRepository repository;
	
	public List<PFAccountEntity> getAllPFAccount()
	{
		List<PFAccountEntity> result = (List<PFAccountEntity>) repository.findAll();
		
		if(result.size() > 0) {

			/*
			 * for (PFAccountEntity entity: result) {
			 * retriveAttachmentsAndStore(entity.getAttach()); }
			 */
			return result;
		} else {
			return new ArrayList<PFAccountEntity>();
		}
	}
	
	public PFAccountEntity getPFAccountById(Long id) throws RecordNotFoundException 
	{
		Optional<PFAccountEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
}
