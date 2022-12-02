package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFNomineeEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.PFNomineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PFNomineeService {
    @Autowired
    PFNomineeRepository repository;

    public List<PFNomineeEntity> getAllVpf()
    {
        List<PFNomineeEntity> result = (List<PFNomineeEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<PFNomineeEntity>();
        }
    }

    public PFNomineeEntity getPFNomineeById(Long id) throws RecordNotFoundException
    {
        Optional<PFNomineeEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public boolean deletePFNomineeById(Long id) throws RecordNotFoundException
    {
        Optional<PFNomineeEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public PFNomineeEntity createOrUpdatePFNomineeEntity(PFNomineeEntity entity)
    {
        if(entity.getid()  == null || entity.getid()  == 0)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<PFNomineeEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                PFNomineeEntity newEntity = employee.get();
                newEntity.setapproved(entity.getapproved());
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setempcode(entity.getempcode());
                newEntity.setsubmitted(entity.getsubmitted());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<PFNomineeEntity> updatePFNomineeEntity(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<PFNomineeEntity> result = new ArrayList<PFNomineeEntity>();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<PFNomineeEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                PFNomineeEntity newEntity = employee.get();

                if(isManager == true)
                    newEntity.setapproved(entity.approved);
                else
                    newEntity.setHRApproved(entity.hrApproved);

                repository.save(newEntity);

                result.add(newEntity);
            }
        }

        return result;
    }
}
