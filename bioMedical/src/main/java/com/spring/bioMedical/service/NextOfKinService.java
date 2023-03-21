package com.spring.bioMedical.service;

import com.spring.bioMedical.model.NextOfKinEntity;
import com.spring.bioMedical.model.PatientAddressEntity;
import com.spring.bioMedical.repository.AddressRepository;
import com.spring.bioMedical.repository.NextOfKinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NextOfKinService {
    @Autowired
    NextOfKinRepository repository;

    public NextOfKinEntity createOrUpdateNextOfKin(NextOfKinEntity entity)
    {
        long millis=System.currentTimeMillis();

        if(entity.getId()  == null || entity.getId() == 0)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<NextOfKinEntity> patient = repository.findById(entity.getId());

            if(patient.isPresent())
            {
                NextOfKinEntity newEntity = patient.get();
                newEntity.setName(entity.getName());
                newEntity.setRelation(entity.getRelation());
                newEntity.setMobileNumber(entity.getMobileNumber());
                newEntity = repository.save(newEntity);

                return newEntity;
            }
            else
            {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<NextOfKinEntity> getAllNextOfKin()
    {
        List<NextOfKinEntity> result = (List<NextOfKinEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<NextOfKinEntity>();
        }
    }
}
