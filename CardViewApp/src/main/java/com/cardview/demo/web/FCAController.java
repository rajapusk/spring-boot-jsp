package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.*;
import com.cardview.demo.outputModels.FcaOutput;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.FcaService;
import com.cardview.demo.service.PFAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fca")
public class FCAController {

    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    FcaService fcaService;
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/get/{id}")
    public FcaEntity getBriefcaseAllowanceById(@PathVariable("id") Long id) {
        try {
            System.out.println("@@@ id " + id);
            return fcaService.getFCAById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public FcaEntity createOrUpdateBriefcaseAllowanceContribution(@RequestBody FcaEntity loan) {
        FcaEntity entity = fcaService.createOrUpdateFCA(loan);

        try {
            PFAccountEntity account = paService.getPFAccountById(loan.getEmpCode());
            String body = account.getNAME() + " has applied the Briefcase Allowance. ";
            emailService.sendSimpleMail(_managerEmail, body, "Briefcase Allowance Application");

        } catch (Exception e) {
        }

        return  entity;
    }

    @DeleteMapping("/delete/id")
    public boolean deleteBriefcaseAllowanceById(@PathVariable("id") long id) throws RecordNotFoundException {
        return fcaService.deleteFCAById(id);
    }

    @GetMapping("/manager")
    public List<FcaOutput> managerFcaGetAll() {
        try {
            List<FcaOutput> result = new ArrayList<FcaOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<FcaEntity> lstBriefcaseAllowance = fcaService.getAllFCA();

            for (PFAccountEntity account : listAllAccount) {
                for (FcaEntity ba : lstBriefcaseAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 0) {
                        FcaOutput output = new FcaOutput();
                        output.id = ba.getid();
                        output.doj = account.getDOJ();
                        output.name = account.getNAME();
                        output.empcode = account.getEMPCODE();
                        output.claimAmount = ba.getClaimAmount();
                        output.remarks = ba.getremarks();
                        output.submitted = ba.getsubmitted();
                        output.approved = ba.getapproved();
                        output.hrApproved = ba.getHRApproved();
                        output.entitledAmount = ba.getEntitledAmount();
                        output.months = ba.getMonths();
                        output.quarterType = ba.getQuarterType();
                        output.managerRemarks = ba.getManagerRemarks();
                        output.hrRemarks=ba.getHrRemarks();
                        result.add(output);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/hr")
    public List<FcaOutput> hrFcaGetAll() {
        try {
            List<FcaOutput> result = new ArrayList<FcaOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<FcaEntity> lstBriefcaseAllowance = fcaService.getAllFCA();

            for (PFAccountEntity account : listAllAccount) {
                for (FcaEntity ba : lstBriefcaseAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 1 && ba.getHRApproved() == 0) {
                        FcaOutput output = new FcaOutput();
                        output.id = ba.getid();
                        output.doj = account.getDOJ();
                        output.name = account.getNAME();
                        output.empcode = account.getEMPCODE();
                        output.claimAmount = ba.getClaimAmount();
                        output.remarks = ba.getremarks();
                        output.submitted = ba.getsubmitted();
                        output.approved = ba.getapproved();
                        output.hrApproved = ba.getHRApproved();
                        output.entitledAmount = ba.getEntitledAmount();
                        output.months = ba.getMonths();
                        output.quarterType = ba.getQuarterType();
                        output.managerRemarks = ba.getManagerRemarks();
                        output.hrRemarks=ba.getHrRemarks();
                        output.grade = account.getGRADE();
                        result.add(output);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    @GetMapping("/getEntitle")
    public EntitledAmount getEntitledAmount() {
        try {
            return new EntitledAmount();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateFCA(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<FcaEntity> lstVPF = fcaService.updateFCA(loan, true);

        for (FcaEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "FCA Approved";
                body = "Your FCA has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "FCA Rejected";
                body = "Your FCA has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrBriefcaseAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<FcaEntity> lstVPF = fcaService.updateFCA(loan, false);

        for (FcaEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "FCA Approved";
                body = "Your FCA has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "FCA Rejected";
                body = "Your FCA has been rejected by HR.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }
}
