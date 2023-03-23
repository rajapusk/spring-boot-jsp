package com.spring.bioMedical.service;

import com.spring.bioMedical.entity.AppointmentEntity;
import com.spring.bioMedical.model.AppointmentDiagnosticEntity;
import com.spring.bioMedical.model.AppointmentDoctorEntity;
import com.spring.bioMedical.model.AppointmentServiceEntity;
import com.spring.bioMedical.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository repository;

    @Autowired
    AppointmentDoctorRepository appointmentDoctorRepository;
    @Autowired
    AppointmentDiagnosticRepository appointmentDiagnosticRepository;
    @Autowired
    AppointmentServiceRepository serviceRepository;


    public AppointmentEntity createOrUpdateAppointment(AppointmentEntity entity) {
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
            Optional<AppointmentEntity> patient = repository.findById(entity.getId());

            if(patient.isPresent())
            {
                AppointmentEntity newEntity = patient.get();
                entity.setUpdatedOn(new java.sql.Date(millis));
                newEntity.setPatientId(entity.getPatientId());
                newEntity.setPatientType(entity.getPatientType());
                newEntity.setAmountPaid(entity.getAmountPaid());
                newEntity.setDepartment(entity.getDepartment());
                newEntity.setPayeeType(entity.getPayeeType());
                newEntity.setReferral(entity.getReferral());
                newEntity.setTotalAmount(entity.getTotalAmount());
                newEntity.setUpiCard(entity.getUpiCard());
                newEntity.setVisitType(entity.getVisitType());
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

    public AppointmentDoctorEntity createOrUpdateAppointmentDoctor(AppointmentDoctorEntity entity) {
        long millis=System.currentTimeMillis();

        if(entity.getId()  == null || entity.getId() == 0)
        {
            entity = appointmentDoctorRepository.save(entity);

            return entity;
        }
        else
        {
            Optional<AppointmentDoctorEntity> patient = appointmentDoctorRepository.findById(entity.getId());

            if(patient.isPresent())
            {
                AppointmentDoctorEntity newEntity = patient.get();

                newEntity.setPatientId(entity.getPatientId());
                newEntity.setAppointmentId(entity.getId());
                newEntity.setFee(entity.getFee());
                newEntity.setDoctorId(entity.getDoctorId());
                newEntity = appointmentDoctorRepository.save(newEntity);

                return newEntity;
            }
            else
            {
                entity = appointmentDoctorRepository.save(entity);

                return entity;
            }
        }
    }

    public AppointmentServiceEntity createOrUpdateAppointmentServcie(AppointmentServiceEntity entity) {
        long millis=System.currentTimeMillis();

        if(entity.getId()  == null || entity.getId() == 0)
        {
            entity = serviceRepository.save(entity);

            return entity;
        }
        else
        {
            Optional<AppointmentServiceEntity> patient = serviceRepository.findById(entity.getId());

            if(patient.isPresent())
            {
                AppointmentServiceEntity newEntity = patient.get();
                newEntity.setPatientId(entity.getPatientId());
                newEntity.setAppointmentId(entity.getId());
                newEntity.setFee(entity.getFee());
                newEntity.setServiceId(entity.getServiceId());
                newEntity = serviceRepository.save(newEntity);

                return newEntity;
            }
            else
            {
                entity = serviceRepository.save(entity);

                return entity;
            }
        }
    }

    public AppointmentDiagnosticEntity createOrUpdateAppointmentDiagnostic(AppointmentDiagnosticEntity entity) {
        long millis=System.currentTimeMillis();

        if(entity.getId()  == null || entity.getId() == 0)
        {
            entity = appointmentDiagnosticRepository.save(entity);

            return entity;
        }
        else
        {
            Optional<AppointmentDiagnosticEntity> patient = appointmentDiagnosticRepository.findById(entity.getId());

            if(patient.isPresent())
            {
                AppointmentDiagnosticEntity newEntity = patient.get();
                newEntity.setPatientId(entity.getPatientId());
                newEntity.setAppointmentId(entity.getId());
                newEntity.setFee(entity.getFee());
                newEntity.setDiagnosticId(entity.getDiagnosticId());
                newEntity = appointmentDiagnosticRepository.save(newEntity);

                return newEntity;
            }
            else
            {
                entity = appointmentDiagnosticRepository.save(entity);

                return entity;
            }
        }
    }
}
