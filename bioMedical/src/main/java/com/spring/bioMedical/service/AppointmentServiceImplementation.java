package com.spring.bioMedical.service;

import java.util.List;

import com.spring.bioMedical.entity.AppointmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.bioMedical.entity.Admin;
import com.spring.bioMedical.entity.Appointment;
import com.spring.bioMedical.repository.AppointmentRepository;

/**
 * 
 * @author Ksheer Sagar

 *
 */
@Service
public class AppointmentServiceImplementation  {

	private AppointmentRepository appointmentRepository;

	//inject employee dao
	@Autowired   //Adding bean id @Qualifier
	public AppointmentServiceImplementation( AppointmentRepository obj)
	{
		appointmentRepository=obj;
	}
	
	
	public void save(AppointmentEntity app)
	{
		appointmentRepository.save(app);
	}
	
	
	public List<AppointmentEntity> findAll() {
		return appointmentRepository.findAll();
	}


	
}
