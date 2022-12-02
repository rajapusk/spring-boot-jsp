package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.NomineeEntity;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PFNomineeEntity;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.NomineeInput;
import com.cardview.demo.outputModels.PfNomineeInput;
import com.cardview.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pfnominee")
public class PFNomineeController {

    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    PFNomineeService pfNomineeService;
    @Autowired
    NomineeService nomineeService;
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/get/{id}")
    public PFNomineeEntity getEmployeeById(@PathVariable("id") Long id) {
        try {
            return pfNomineeService.getPFNomineeById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public PFNomineeEntity createOrUpdateVpfContribution(@RequestBody PfNomineeInput input) {

        PFNomineeEntity entity = new PFNomineeEntity();
        entity.setsubmitted(input.submitted);
        entity.setapproved(input.approved);
        entity.setHRApproved(input.hrApproved);
        entity.setid(input.id);
        entity.setempcode(input.empCode);

        PFNomineeEntity dbEntity = pfNomineeService.createOrUpdatePFNomineeEntity(entity);

        for (NomineeInput data:input.nominees) {
            NomineeEntity nomineeEntity = new NomineeEntity();
            nomineeEntity.setPfNomineeId(dbEntity.getid());
            nomineeEntity.setAddress(data.address);
            nomineeEntity.setDOB(data.dob);
            nomineeEntity.setId(data.id);
            nomineeEntity.setGender(data.gender);
            nomineeEntity.setNomineeAadhaarNo(data.nomineeAadhaarNo);
            nomineeEntity.setName(data.name);
            nomineeEntity.setRelation(data.relation);
            nomineeEntity.setGuardiansName(data.guardiansName);
            nomineeEntity.setIsDeleted(data.isdeleted);
            nomineeEntity.setIsMinor(data.isMinor);
            nomineeEntity.setMinorDOB(data.minorDOB);
            nomineeEntity.setProportion(data.proportion);
            NomineeEntity dbNomineeEntity = nomineeService.createOrUpdateNomineeEntity(nomineeEntity);
        }

        try {




            PFAccountEntity account = paService.getPFAccountById(input.empCode);
            String body = account.getNAME() + " has applied the Pf Nominee. ";
            emailService.sendSimpleMail(_managerEmail, body, "Pf Nominee Application");

        } catch (Exception e) {
        }

        return  entity;
    }

    @DeleteMapping("/delete/id")
    public boolean getPFAccount(@PathVariable("id") long id) throws RecordNotFoundException {

        return pfNomineeService.deletePFNomineeById(id);
    }

}
