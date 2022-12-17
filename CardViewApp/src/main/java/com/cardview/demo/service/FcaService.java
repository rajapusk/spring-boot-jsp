package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.FcaEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.BriefcaseAllowanceRepository;
import com.cardview.demo.repository.FcaRepository;
import com.cardview.demo.repository.PFAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FcaService {

    @Autowired
    FcaRepository repository;

    @Autowired
    PFAccountRepository accountRepository;


    public List<FcaEntity> getAllFCA()
    {
        List<FcaEntity> result = (List<FcaEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<FcaEntity>();
        }
    }

    public boolean deleteFCAById(Long id) throws RecordNotFoundException
    {
        Optional<FcaEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public FcaEntity getFCAById(Long id) throws RecordNotFoundException
    {
        Optional<FcaEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public FcaEntity createOrUpdateFCA(FcaEntity entity)
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
            Optional<FcaEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                FcaEntity newEntity = employee.get();
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setapproved(entity.getapproved());
                newEntity.setremarks(entity.getremarks());
                newEntity.setsubmitted(entity.getsubmitted());
                newEntity.setClaimAmount(entity.getClaimAmount());
                newEntity.setEntitledAmount(entity.getEntitledAmount());
                newEntity.setHrRemarkss(entity.getHrRemarks());
                newEntity.setManagerRemarks(entity.getManagerRemarks());
                newEntity.setMonths(entity.getMonths());
                newEntity.setQuarterType(entity.getQuarterType());
                entity.setUpdatedOn(new java.sql.Date(millis));
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<FcaEntity> updateFCA(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<FcaEntity> result = new ArrayList<FcaEntity>();
        for (PfLoanUpdateInput entity : entityArray) {
            Optional<FcaEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                FcaEntity newEntity = employee.get();

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
