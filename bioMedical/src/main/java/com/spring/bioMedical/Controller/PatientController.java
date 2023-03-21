package com.spring.bioMedical.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.bioMedical.model.NextOfKinEntity;
import com.spring.bioMedical.model.PatientAddressEntity;
import com.spring.bioMedical.model.PatientEntity;
import com.spring.bioMedical.outputModel.NextOfKin;
import com.spring.bioMedical.outputModel.addressoutput;
import com.spring.bioMedical.outputModel.patientOutput;
import com.spring.bioMedical.service.AddressService;
import com.spring.bioMedical.service.NextOfKinService;
import com.spring.bioMedical.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    PatientService patientService;
    @Autowired
    AddressService addressService;
    @Autowired
    NextOfKinService nextOfKinService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public PatientEntity CreateOrUpdatePatient(@RequestBody patientOutput input) {

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
        addressService.createOrUpdateAddress(addressEntity);

        for (NextOfKin item :
                input.nextOfKin) {

            NextOfKinEntity nextOfKinEntity = new NextOfKinEntity();
            nextOfKinEntity.setName(item.name);
            nextOfKinEntity.setRelation(item.relation);
            nextOfKinEntity.setMobileNumber(item.mobile);
            nextOfKinEntity.setPatientId(patient.getId());
            nextOfKinService.createOrUpdateNextOfKin(nextOfKinEntity);
        }

        return entity;
    }

    @DeleteMapping("/delete/{id}")
    public boolean DetetePatient(@PathVariable("id") long id) {
        return patientService.deletePatientById(id);
    }

    @GetMapping()
    public List<patientOutput> getAllPatientOutput() {
        List<PatientEntity> lstPatient = patientService.getAllPatient();
        List<patientOutput> patientOutputList = new ArrayList<>();

        for (PatientEntity patient : lstPatient) {
            patientOutput output = new patientOutput();
            output.dob = patient.getDob();
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

        return patientOutputList;
    }

    @GetMapping("/{id}")
    public patientOutput getAllPatientOutput(@PathVariable("id") long id) {
        PatientEntity patient = patientService.getPatientById(id);

        if (patient != null) {
            patientOutput output = new patientOutput();
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
                    output.address = new addressoutput();
                    output.address.building = patientAddress.getBuilding();
                    output.address.city = patientAddress.getCity();
                    output.address.colony = patientAddress.getColony();
                    output.address.pinCode = patientAddress.getPinCode();
                    output.address.state = patientAddress.getState();
                    output.address.district = patientAddress.getDistrict();
                    output.address.hNo = patientAddress.getHouseNo();
                    output.address.street = patientAddress.getStreet();
                    break;
                }
            }
            output.nextOfKin = new ArrayList<>();
            for (NextOfKinEntity nextOfKinEntity :
                    allNextOfKin) {
                if (nextOfKinEntity.getPatientId() == patient.getId()) {
                    NextOfKin kin = new NextOfKin();
                    kin.mobile = nextOfKinEntity.getMobileNumber();
                    kin.name = nextOfKinEntity.getName();
                    kin.relation = nextOfKinEntity.getRelation();
                    output.nextOfKin.add(kin);
                }
            }

            return output;
        }
        return null;
    }
}
