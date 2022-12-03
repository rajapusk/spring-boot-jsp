package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.*;
import com.cardview.demo.outputModels.NomineeInput;
import com.cardview.demo.outputModels.PfNomineeInput;
import com.cardview.demo.outputModels.PfNomineeOutput;
import com.cardview.demo.outputModels.VpfContributionOutput;
import com.cardview.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public PfNomineeOutput getEmployeeById(@PathVariable("id") Long id) {
        try {
            PFNomineeEntity entity = pfNomineeService.getPFNomineeById(id);
            PFAccountEntity account =  paService.getPFAccountById(entity.getempcode());
            PfNomineeOutput output = new PfNomineeOutput();
            output.id = entity.getid();
            output.doj = account.getDOJ();
            output.name  = account.getNAME();
            output.nameInAadhaar = account.getNameInAadhaar();
            output.gender = account.getGender();
            output.dor = account.getDOR();
            output.pfDoj = account.getPfDOJ();
            output.panNo = account.getPanNo();
            output.pf_nps_AcNo = account.getPf_nps();
            output.submitted = entity.getsubmitted();
            output.approved = entity.getapproved();
            output.hrApproved = entity.getHRApproved();
            output.nominees = new ArrayList<>();
            List<NomineeEntity> nomineeEntities = nomineeService.getAllNominee();
            for (NomineeEntity nominee : nomineeEntities)
            {
                if(nominee.getIsDeleted() == false && nominee.getPfNomineeId() == entity.getid()){
                    NomineeInput input = new NomineeInput();
                    input.id = nominee.getId();
                    input.dob = nominee.getDOB();
                    input.pfNominee_id = nominee.getPfNomineeId();
                    input.address = nominee.getAddress();
                    input.gender = nominee.getGender();
                    input.guardiansName = nominee.getGuardiansName();
                    input.isMinor = nominee.getIsMinor();
                    input.isdeleted = false;
                    input.name = nominee.getName();
                    input.nomineeAadhaarNo = nominee.getNomineeAadhaarNo();
                    input.proportion = nominee.getProportion();
                    input.relation = nominee.getRelation();
                    output.nominees.add(input);
                }
            }

            return output;
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

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateVPFLoan(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<PFNomineeEntity> lstVPF = pfNomineeService.updatePFNomineeEntity(loan,true);

        for (PFNomineeEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "PF Nominee Approved";
                body = "Your VPF has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "PF Nominee Rejected";
                body = "Your PF Nominee has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrVPFLoan(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<PFNomineeEntity> lstVPF = pfNomineeService.updatePFNomineeEntity(loan, false);

        for (PFNomineeEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "PF Nominee Approved";
                body = "Your PF Nominee has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "PF Nominee Rejected";
                body = "Your PF Nominee has been rejected by HR.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @GetMapping("/manager")
    public List<PfNomineeOutput> getAllPfNominee() {
        try {
            List<PfNomineeOutput> result = new ArrayList<PfNomineeOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<PFNomineeEntity> lstPFNominee = pfNomineeService.getAllPFNominee();

            for (PFAccountEntity account : listAllAccount) {
                for (PFNomineeEntity entity : lstPFNominee) {
                    if (account.getEMPCODE() == entity.getempcode() && entity.getapproved() == 0) {
                        PfNomineeOutput output = new PfNomineeOutput();
                        output.id = entity.getid();
                        output.doj = account.getDOJ();
                        output.name  = account.getNAME();
                        output.nameInAadhaar = account.getNameInAadhaar();
                        output.gender = account.getGender();
                        output.dor = account.getDOR();
                        output.pfDoj = account.getPfDOJ();
                        output.panNo = account.getPanNo();
                        output.pf_nps_AcNo = account.getPf_nps();
                        output.submitted = entity.getsubmitted();
                        output.approved = entity.getapproved();
                        output.hrApproved = entity.getHRApproved();
                        result.add(output);
                    }
                }
            }
            return result;
            // return pfService.getAllPFLoan();
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/hr")
    public List<PfNomineeOutput> getHrAllPfNominee() {
        try {
            List<PfNomineeOutput> result = new ArrayList<PfNomineeOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<PFNomineeEntity> lstPFNominee = pfNomineeService.getAllPFNominee();

            for (PFAccountEntity account : listAllAccount) {
                for (PFNomineeEntity entity : lstPFNominee) {
                    if (account.getEMPCODE() == entity.getempcode() && entity.getapproved() == 1 &&  entity.getHRApproved() == 0) {
                        PfNomineeOutput output = new PfNomineeOutput();
                        output.id = entity.getid();
                        output.doj = account.getDOJ();
                        output.name  = account.getNAME();
                        output.nameInAadhaar = account.getNameInAadhaar();
                        output.gender = account.getGender();
                        output.dor = account.getDOR();
                        output.pfDoj = account.getPfDOJ();
                        output.panNo = account.getPanNo();
                        output.pf_nps_AcNo = account.getPf_nps();
                        output.submitted = entity.getsubmitted();
                        output.approved = entity.getapproved();
                        output.hrApproved = entity.getHRApproved();
                        result.add(output);
                    }
                }
            }
            return result;
            // return pfService.getAllPFLoan();
        } catch (Exception ex) {
            return null;
        }
    }
}
