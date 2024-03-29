package com.spring.bioMedical.service;

import com.spring.bioMedical.model.PatientEntity;
import com.spring.bioMedical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository repository;

    public PatientEntity createOrUpdatePatient(PatientEntity entity)
    {
        long millis=System.currentTimeMillis();

        if(entity.getId()  == null || entity.getId() == 0)
        {
            entity.setCreatedOn(new java.sql.Date(millis));
            entity.setUpdatedOn(new java.sql.Date(millis));
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<PatientEntity> patient = repository.findById(entity.getId());

            if(patient.isPresent())
            {
                PatientEntity newEntity = patient.get();
                newEntity.setUpdatedOn(new java.sql.Date(millis));
                newEntity.setEmailId(entity.getEmailId());
                //newEntity.setDateOfOpVisit(entity.getDateOfOpVisit());
                newEntity.setDob(entity.getDob());
                newEntity.setAge(entity.getAge());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());
                newEntity.setMobileNumber(entity.getMobileNumber());
                newEntity.setMotherName(entity.getMotherName());
                //newEntity.setTimeOfOpVisit(entity.getTimeOfOpVisit());
                newEntity.setIsDeleted(false);
                newEntity.setUpdatedOn(new java.sql.Date(millis));
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

    public boolean deletePatientById(long id) {
        Optional<PatientEntity> patient = repository.findById(id);

        if (patient.isPresent()) {
            PatientEntity newEntity = patient.get();

            newEntity.setIsDeleted(true);
            repository.save(newEntity);
            return true;
        }

        return false;
    }

    public List<PatientEntity> getAllPatient()
    {
        List<PatientEntity> result = (List<PatientEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<PatientEntity>();
        }
    }

    public PatientEntity getPatientById(long id) {
        Optional<PatientEntity> patient = repository.findById(id);

        if (patient.isPresent()) {
           return patient.get();
        }

        return null;
    }

}
