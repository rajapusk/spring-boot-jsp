package com.cardview.demo.service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.*;

import com.cardview.demo.model.PFLoanEntity;


import com.cardview.demo.model.PfLoanUpdateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.repository.PFLoanRepository;

@Service
public class PFLoanService {
	
	@Value("${spring.project.directory}")
	private String targetPath;

	@Autowired
	PFLoanRepository repository;
	
	public List<PFLoanEntity> getAllPFLoan()
	{
		List<PFLoanEntity> result = (List<PFLoanEntity>) repository.findAll();
		
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<PFLoanEntity>();
		}
	}
	
	public PFLoanEntity getPFAccountById(Long id) throws RecordNotFoundException 
	{
		Optional<PFLoanEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
	
	public PFLoanEntity createOrUpdatePFLoan(PFLoanEntity entity) 
	{
		if(entity.getid()  == null) 
		{
			entity = repository.save(entity);
			
			return entity;
		} 
		else 
		{
			Optional<PFLoanEntity> employee = repository.findById(entity.getid());
			
			if(employee.isPresent()) 
			{
				PFLoanEntity newEntity = employee.get();
				newEntity.setadvanceType(entity.getadvanceType());
				newEntity.setapproved(entity.getapproved());
				newEntity.setemiAmount(entity.getemiAmount());
				newEntity.setempcode(entity.getempcode());
				newEntity.setnewNetSalary(entity.getnewNetSalary());
				newEntity.setnewNetSalaryPer(entity.getnewNetSalaryPer());
				newEntity.setnoOfEMI(entity.getnoOfEMI());
				newEntity.setremarks(entity.getremarks());
				newEntity.setrequiredAmount(entity.getrequiredAmount());
				newEntity.setsubmitted(entity.getsubmitted());
				newEntity.setType(entity.getType());
				newEntity = repository.save(newEntity);
				
				return newEntity;
			} else {
				entity = repository.save(entity);
				
				return entity;
			}
		}
	}
	
	public boolean updatePFLoan(PfLoanUpdateInput[] entityArray) {
		for(PfLoanUpdateInput entity : entityArray) {
			Optional<PFLoanEntity> employee = repository.findById(entity.id);
			if (employee.isPresent()) {
				PFLoanEntity newEntity = employee.get();
				newEntity.setapproved(entity.approved);
				newEntity.setremarks(newEntity.getremarks() + "; " + entity.remarks);
				newEntity = repository.save(newEntity);				
			}
		}

		return true;
	}

	public void sendMail(PFLoanEntity entity)
	{

	}
	
	public PFLoanEntity updateDocs(PFLoanEntity entity) {
		Optional<PFLoanEntity> employee = repository.findById(entity.getid());
		
		if (employee.isPresent()) {
			PFLoanEntity newEntity = employee.get();
			newEntity.setid(entity.getid());
			newEntity.setfileName(entity.getfileName());
			newEntity = repository.save(newEntity);
			
			return newEntity;
		}

		return null;
	}
}
