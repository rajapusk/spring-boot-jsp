package com.spring.bioMedical.service;

import com.spring.bioMedical.model.PatientAddressEntity;
import com.spring.bioMedical.model.PatientEntity;
import com.spring.bioMedical.repository.AddressRepository;
import com.spring.bioMedical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository repository;

    public PatientAddressEntity createOrUpdateAddress(PatientAddressEntity entity)
    {
        long millis=System.currentTimeMillis();

        if(entity.getId()  == null || entity.getId() == 0)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<PatientAddressEntity> patient = repository.findById(entity.getId());
            
            if(patient.isPresent())
            {
                PatientAddressEntity newEntity = patient.get();
                newEntity.setCity(entity.getCity());
                newEntity.setState(entity.getState());
                newEntity.setBuilding(entity.getBuilding());
                newEntity.setDistrict(entity.getDistrict());
                newEntity.setPinCode(entity.getPinCode());
                newEntity.setColony(entity.getColony());
                newEntity.setStreet(entity.getStreet());
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

    public List<PatientAddressEntity> getAllAddress()
    {
        List<PatientAddressEntity> result = (List<PatientAddressEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<PatientAddressEntity>();
        }
    }

    public List<PatientAddressEntity> getAllByPatientId(long id) {
      return  repository.findAllByPatientId(id);
    }
}
