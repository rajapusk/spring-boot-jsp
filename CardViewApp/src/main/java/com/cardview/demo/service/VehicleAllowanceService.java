package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.VehicleAllowanceEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.VehicleAllowanceRepository;
import com.cardview.demo.repository.PFAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleAllowanceService {
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    VehicleAllowanceRepository repository;

    @Autowired
    PFAccountRepository accountRepository;


    public List<VehicleAllowanceEntity> getAllVehicleAllowance()
    {
        List<VehicleAllowanceEntity> result = (List<VehicleAllowanceEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<VehicleAllowanceEntity>();
        }
    }

    public boolean deleteVehicleAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<VehicleAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public VehicleAllowanceEntity getVehicleAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<VehicleAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public VehicleAllowanceEntity createOrUpdateVehicleAllowance(VehicleAllowanceEntity entity)
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
            Optional<VehicleAllowanceEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                VehicleAllowanceEntity newEntity = employee.get();
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
                entity.setServiceCentreName(entity.getServiceCentreName());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<VehicleAllowanceEntity> updateVehicleAllowance(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<VehicleAllowanceEntity> result = new ArrayList<VehicleAllowanceEntity>();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<VehicleAllowanceEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                VehicleAllowanceEntity newEntity = employee.get();

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
