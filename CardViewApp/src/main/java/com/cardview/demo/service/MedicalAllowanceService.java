package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.MedicalAllowanceEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.MedicalAllowanceRepository;
import com.cardview.demo.repository.PFAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalAllowanceService {
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    MedicalAllowanceRepository repository;

    @Autowired
    PFAccountRepository accountRepository;


    public List<MedicalAllowanceEntity> getAllMedicalAllowance()
    {
        List<MedicalAllowanceEntity> result = (List<MedicalAllowanceEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<MedicalAllowanceEntity>();
        }
    }

    public boolean deleteMedicalAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<MedicalAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public MedicalAllowanceEntity getMedicalAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<MedicalAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public MedicalAllowanceEntity createOrUpdateMedicalAllowance(MedicalAllowanceEntity entity)
    {
        long millis=System.currentTimeMillis();

        if(entity.getid()  == null)
        {
            entity.setCreatedOn(new java.sql.Date(millis));
            entity.setUpdatedOn(new java.sql.Date(millis));
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<MedicalAllowanceEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                MedicalAllowanceEntity newEntity = employee.get();
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setapproved(entity.getapproved());
                newEntity.setremarks(entity.getremarks());
                newEntity.setsubmitted(entity.getsubmitted());
                newEntity.setClaimAmount(entity.getClaimAmount());
                newEntity.setEntitledAmount(entity.getEntitledAmount());
                newEntity.setHrRemarkss(entity.getHrRemarks());
                newEntity.setManagerRemarks(entity.getManagerRemarks());
                newEntity.setInvoiceAmount(entity.getInvoiceAmount());
                newEntity.setInvoiceDate(entity.getInvoiceDate());
                newEntity.setInvoiceNo(entity.getInvoiceNo());
                entity.setUpdatedOn(new java.sql.Date(millis));
                entity.setHospitalName(entity.getHospitalName());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<MedicalAllowanceEntity> updateMedicalAllowance(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<MedicalAllowanceEntity> result = new ArrayList<MedicalAllowanceEntity>();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<MedicalAllowanceEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                MedicalAllowanceEntity newEntity = employee.get();

                if (isManager == true) {
                    newEntity.setapproved(entity.approved);
                    newEntity.setManagerRemarks(entity.remarks);
                } else {
                    newEntity.setHRApproved(entity.hrApproved);
                    newEntity.setHrRemarkss(entity.remarks);
                }

                repository.save(newEntity);
                result.add(newEntity);
            }
        }

        return result;
    }
}
