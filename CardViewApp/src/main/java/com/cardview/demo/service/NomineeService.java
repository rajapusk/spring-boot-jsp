package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.NomineeEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.NomineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NomineeService {
    @Autowired
    NomineeRepository repository;

    public List<NomineeEntity> getAllNominee()
    {
        List<NomineeEntity> result = (List<NomineeEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<NomineeEntity>();
        }
    }

    public NomineeEntity getNomineeById(Long id) throws RecordNotFoundException
    {
        Optional<NomineeEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public boolean deleteNomineeEntityById(Long id) throws RecordNotFoundException
    {
        Optional<NomineeEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public NomineeEntity createOrUpdateNomineeEntity(NomineeEntity entity)
    {
        if(entity.getId()  == null)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<NomineeEntity> employee = repository.findById(entity.getId());

            if(employee.isPresent())
            {
                NomineeEntity newEntity = employee.get();

                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<NomineeEntity> updateNomineeEntity(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<NomineeEntity> result = new ArrayList<NomineeEntity>();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<NomineeEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                NomineeEntity newEntity = employee.get();
                repository.save(newEntity);

                result.add(newEntity);
            }
        }

        return result;
    }
}
