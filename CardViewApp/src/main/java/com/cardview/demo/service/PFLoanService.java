package com.cardview.demo.service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.*;

import com.cardview.demo.model.EmpDocEntity;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PFLoanEntity;


import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.outputModels.PfLoanOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.repository.EmpDocRepository;
import com.cardview.demo.repository.PFAccountRepository;
import com.cardview.demo.repository.PFLoanRepository;

@Service
public class PFLoanService {

	@Autowired private EmailServiceImpl emailService;
	
	@Value("${spring.project.directory}")
	private String targetPath;

	@Autowired
	PFLoanRepository repository;
	
	@Autowired
	PFAccountRepository accountRepository;
	
	@Autowired
	EmpDocRepository docRepository;
	
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
	
	public List<PFLoanEntity> updatePFLoan(PfLoanUpdateInput[] entityArray) {

		List<PFLoanEntity> result = new ArrayList<PFLoanEntity>();
		for(PfLoanUpdateInput entity : entityArray) {
			Optional<PFLoanEntity> employee = repository.findById(entity.id);
			if (employee.isPresent()) {
				PFLoanEntity newEntity = employee.get();
				newEntity.setapproved(entity.approved);
				newEntity.setremarks(newEntity.getremarks() + "; " + entity.remarks);
				repository.save(newEntity);

				result.add(newEntity);
			}
		}

		return result;
	}

	public void sendMail(PFLoanEntity entity)
	{

	}
	
	public EmpDocEntity updateDocs(EmpDocEntity entity) {
		docRepository.save(entity);
		
		return entity;
	}
}
