package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.NomineeEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.TravelExpenseDetailEntity;
import com.cardview.demo.repository.PFNomineeRepository;
import com.cardview.demo.repository.TravelExpenseDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TravelExpenseDetailService {

    @Autowired
    TravelExpenseDetailRepository repository;

    public List<TravelExpenseDetailEntity> getAllTravelExpenseDetail()
    {
        List<TravelExpenseDetailEntity> result = (List<TravelExpenseDetailEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<TravelExpenseDetailEntity>();
        }
    }

    public TravelExpenseDetailEntity getTravelExpenseDetailEntityById(Long id) throws RecordNotFoundException
    {
        Optional<TravelExpenseDetailEntity> travelExpenseDetailEntity = repository.findById(id);

        if(travelExpenseDetailEntity.isPresent()) {
            return travelExpenseDetailEntity.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public boolean deleteTravelExpenseDetailEntityById(Long id) throws RecordNotFoundException
    {
        Optional<TravelExpenseDetailEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public TravelExpenseDetailEntity createOrUpdateTravelExpenseDetailEntity(TravelExpenseDetailEntity entity)
    {


        if(entity.getId()  == null)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<TravelExpenseDetailEntity> employee = repository.findById(entity.getId());

            if(employee.isPresent())
            {
                TravelExpenseDetailEntity newEntity = employee.get();

                newEntity.setBillAvailable(entity.getBillAvailable());
                newEntity.setCGSTAmount(entity.getCGSTAmount());
                newEntity.setExpenseDescription(entity.getExpenseDescription());
                newEntity.setDestination(entity.getDestination());
                newEntity.setDistance(entity.getDistance());
                newEntity.setDocument(entity.getDocument());
                newEntity.setClaimAmount(entity.getClaimAmount());
                newEntity.setEntitledAmount(entity.getEntitledAmount());
                newEntity.setIGSTAmount(entity.getIGSTAmount());
                newEntity.setNetAmount(entity.getNetAmount());
                newEntity.setNoOfDays(entity.getNoOfDays());
                newEntity.setOrigin(entity.getOrigin());
                newEntity.setPNRNo(entity.getPNRNo());
                newEntity.setRemarks(entity.getRemarks());
                newEntity.setSGSTAmount(entity.getSGSTAmount());
                newEntity.setTravelEndDate(entity.getTravelEndDate());
                newEntity.setTotalAmount(entity.getTotalAmount());
                newEntity.setTravelStartDate(entity.getTravelStartDate());

                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<TravelExpenseDetailEntity> updateTravelExpenseDetailEntity(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<TravelExpenseDetailEntity> result = new ArrayList<TravelExpenseDetailEntity>();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<TravelExpenseDetailEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                TravelExpenseDetailEntity newEntity = employee.get();
                repository.save(newEntity);

                result.add(newEntity);
            }
        }

        return result;
    }

}
