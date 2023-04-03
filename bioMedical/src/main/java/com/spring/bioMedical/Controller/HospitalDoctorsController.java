package com.spring.bioMedical.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.spring.bioMedical.model.DiagnosticEntity;
import com.spring.bioMedical.model.ServiceEntity;
import com.spring.bioMedical.repository.DiagnosticRepository;
import com.spring.bioMedical.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bioMedical.exception.ResourceNotFoundException;
import com.spring.bioMedical.model.Doctor;
import com.spring.bioMedical.repository.DoctorRepository;


@RestController
@RequestMapping("/api/v1")
public class HospitalDoctorsController {
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private DiagnosticRepository diagnosticRepository;

	@GetMapping("/doctors")
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAllByDeleteFlag("N");
	}

	@GetMapping("/service")
	public List<ServiceEntity> getAllService() {
		return serviceRepository.findAll();
	}

	@GetMapping("/diagnostic")
	public List<DiagnosticEntity> getAllDiagnostic() {
		return diagnosticRepository.findAll();
	}

	@GetMapping("/doctors/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable(value = "id") Long doctorId)
			throws ResourceNotFoundException {
		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not found for this id :: " + doctorId));
		return ResponseEntity.ok().body(doctor);
	}

	@PostMapping("/doctors")
	public Doctor createDoctor(@Valid @RequestBody Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	@PutMapping("/doctors/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable(value = "id") Long doctorId,
			@Valid @RequestBody Doctor doctorDetails) throws ResourceNotFoundException {
		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not found for this id :: " + doctorId));

		//doctor.setDeleteFlag(doctorDetails.);
		
		final Doctor updatedEmployee = doctorRepository.save(doctor);
		return ResponseEntity.ok(updatedEmployee);
	}

	
	//permanant delete
	@DeleteMapping("/doctors/{id}")
	public Map<String, Boolean> deleteDoctor(@PathVariable(value = "id") Long doctorId)
			throws ResourceNotFoundException {
		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not found for this id :: " + doctorId));

		doctorRepository.delete(doctor);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
