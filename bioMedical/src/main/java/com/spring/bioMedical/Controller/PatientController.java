package com.spring.bioMedical.Controller;

import com.spring.bioMedical.entity.AppointmentEntity;
import com.spring.bioMedical.model.*;
import com.spring.bioMedical.outputModel.*;
import com.spring.bioMedical.service.AddressService;
import com.spring.bioMedical.service.AppointmentService;
import com.spring.bioMedical.service.DocumentService;
import com.spring.bioMedical.service.NextOfKinService;
import com.spring.bioMedical.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    PatientService patientService;
    @Autowired
    AddressService addressService;
    @Autowired
    NextOfKinService nextOfKinService;

    @Autowired
    AppointmentService appointmentService;
    
    @Autowired
    DocumentService docService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public PatientEntity CreateOrUpdatePatient(@RequestBody PatientOutput input) {

        PatientEntity patient = new PatientEntity();
        patient.setEmailId(input.emailId);
        patient.setDateOfOpVisit(input.dateOfOpVisit);
        patient.setDob(input.dob);
        patient.setFirstName(input.firstName);
        patient.setLastName(input.lastName);
        patient.setMobileNumber(input.mobileNumber);
        patient.setId(input.mrNo);
        patient.setMotherName(input.motherName);
        patient.setTimeOfOpVisit(input.timeOfOpVisit);
        patient.setIsDeleted(false);

        PatientEntity entity = patientService.createOrUpdatePatient(patient);

        PatientAddressEntity addressEntity = new PatientAddressEntity();
        addressEntity.setPatientId(entity.getId());
        addressEntity.setCity(input.address.city);
        addressEntity.setState(input.address.state);
        addressEntity.setBuilding(input.address.building);
        addressEntity.setDistrict(input.address.district);
        addressEntity.setPinCode(input.address.pinCode);
        addressEntity.setColony(input.address.colony);
        addressEntity.setStreet(input.address.street);
        addressEntity.setId(input.address.id);
        addressService.createOrUpdateAddress(addressEntity);

        for (NextOfKin item : input.nextOfKin) {

            NextOfKinEntity nextOfKinEntity = new NextOfKinEntity();
            nextOfKinEntity.setName(item.name);
            nextOfKinEntity.setRelation(item.relation);
            nextOfKinEntity.setMobileNumber(item.mobile);
            nextOfKinEntity.setId(item.id);
            nextOfKinEntity.setPatientId(entity.getId());
            nextOfKinService.createOrUpdateNextOfKin(nextOfKinEntity);
        }

        return entity;
    }

    @DeleteMapping("/delete/{id}")
    public boolean DetetePatient(@PathVariable("id") long id) {
        return patientService.deletePatientById(id);
    }

    @GetMapping()
    public List<PatientOutput> getAllPatientOutput() {
        List<PatientEntity> lstPatient = patientService.getAllPatient();
        List<PatientOutput> patientOutputList = new ArrayList<>();

        for (PatientEntity patient : lstPatient) {
            if (patient.getIsDeleted() == false) {
                PatientOutput output = new PatientOutput();
                output.dob = patient.getDob();
                output.age =calculateAge(output.dob);
                output.firstName = patient.getFirstName();
                output.lastName = patient.getLastName();
                output.emailId = patient.getEmailId();
                output.dateOfOpVisit = patient.getDateOfOpVisit();
                output.mobileNumber = patient.getMobileNumber();
                output.motherName = patient.getMotherName();
                output.mrNo = patient.getId();
                output.timeOfOpVisit = patient.getTimeOfOpVisit();
                patientOutputList.add(output);
            }
        }

        return patientOutputList;
    }

    private static int calculateAge(java.sql.Date dob)
    {
//creating an instance of the LocalDate class and invoking the now() method
//now() method obtains the current date from the system clock in the default time zone
        LocalDate curDate = LocalDate.now();
//calculates the amount of time between two dates and returns the years
        if ((dob != null) && (curDate != null))
        {
            return Period.between(dob.toLocalDate(), curDate).getYears();
        }
        else
        {
            return 0;
        }
    }

    @GetMapping("/{id}")
    public PatientOutput getAllPatientOutput(@PathVariable("id") long id) {
        PatientEntity patient = patientService.getPatientById(id);

        if (patient != null) {
            PatientOutput output = new PatientOutput();
            output.dob = patient.getDob();
            output.firstName = patient.getFirstName();
            output.lastName = patient.getLastName();
            output.emailId = patient.getEmailId();
            output.dateOfOpVisit = patient.getDateOfOpVisit();
            output.mobileNumber = patient.getMobileNumber();
            output.motherName = patient.getMotherName();
            output.mrNo = patient.getId();
            output.timeOfOpVisit = patient.getTimeOfOpVisit();
            List<PatientAddressEntity> patientAddressEntityList = addressService.getAllAddress();
            List<NextOfKinEntity> allNextOfKin = nextOfKinService.getAllNextOfKin();

            for (PatientAddressEntity patientAddress : patientAddressEntityList) {
                if (patientAddress.getPatientId() == patient.getId()) {
                    output.address = new AddressOutput();
                    output.address.building = patientAddress.getBuilding();
                    output.address.city = patientAddress.getCity();
                    output.address.colony = patientAddress.getColony();
                    output.address.pinCode = patientAddress.getPinCode();
                    output.address.state = patientAddress.getState();
                    output.address.district = patientAddress.getDistrict();
                    output.address.hNo = patientAddress.getHouseNo();
                    output.address.street = patientAddress.getStreet();
                    output.address.id = patientAddress.getId();
                    break;
                }
            }
            output.nextOfKin = new ArrayList<>();
            for (NextOfKinEntity nextOfKinEntity : allNextOfKin) {
                if (nextOfKinEntity.getPatientId() == patient.getId()) {
                    NextOfKin kin = new NextOfKin();
                    kin.mobile = nextOfKinEntity.getMobileNumber();
                    kin.name = nextOfKinEntity.getName();
                    kin.relation = nextOfKinEntity.getRelation();
                    kin.id = nextOfKinEntity.getId();
                    output.nextOfKin.add(kin);
                }
            }

            return output;
        }
        return null;
    }

    @RequestMapping(path = "/appointment/book", method = RequestMethod.POST)
    public AppointmentEntity BookAppointment(@RequestBody AppointmentOutput input) {

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setPatientId(input.patientId);
        appointment.setPatientType(input.patientType);
        appointment.setAmountPaid(input.amountPaid);
        appointment.setDepartment(input.department);
        appointment.setPayeeType(input.payeeType);
        appointment.setReferral(input.referral);
        appointment.setTotalAmount(input.totalAmount);
        appointment.setUpiCard(input.upiCard);
        appointment.setVisitType(input.visitType);
        appointment.setIsDeleted(false);

        AppointmentEntity entity = appointmentService.createOrUpdateAppointment(appointment);

        for (ConsultationDoctorOutput doctor : input.consultationDoctor) {
            AppointmentDoctorEntity addressEntity = new AppointmentDoctorEntity();
            addressEntity.setPatientId(entity.getPatientId());
            addressEntity.setAppointmentId(entity.getId());
            addressEntity.setFee(doctor.fee);
            addressEntity.setDoctorId(doctor.id);
            appointmentService.createOrUpdateAppointmentDoctor(addressEntity);
        }

        for (ServiceOutput doctor : input.services) {
            AppointmentServiceEntity serviceEntity = new AppointmentServiceEntity();
            serviceEntity.setPatientId(entity.getPatientId());
            serviceEntity.setAppointmentId(entity.getId());
            serviceEntity.setFee(doctor.fee);
            serviceEntity.setServiceId(doctor.id);
            appointmentService.createOrUpdateAppointmentServcie(serviceEntity);
        }

        for (DiagnosticOutput service : input.diagnostics) {
            AppointmentDiagnosticEntity diagnostic = new AppointmentDiagnosticEntity();
            diagnostic.setPatientId(entity.getPatientId());
            diagnostic.setAppointmentId(entity.getId());
            diagnostic.setFee(service.fee);
            diagnostic.setDiagnosticId(service.id);
            appointmentService.createOrUpdateAppointmentDiagnostic(diagnostic);
        }


        return entity;
    }
    
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
	public DocumentEntity uploadFile(@RequestParam("document") MultipartFile file, String pageId) {
		try {
			if (pageId != null) {
				if (!file.isEmpty()) {
					return FileUploadHelper.uploadFile(file, pageId, "patient", docService);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
