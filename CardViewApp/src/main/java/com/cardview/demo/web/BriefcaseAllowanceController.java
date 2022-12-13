package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.outputModels.BriefcaseAllowanceOutput;
import com.cardview.demo.service.BriefcaseAllowanceService;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ba")
public class BriefcaseAllowanceController {

    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    BriefcaseAllowanceService baService;
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/get/{id}")
    public BriefcaseAllowanceEntity getBriefcaseAllowanceById(@PathVariable("id") Long id) {
        try {
            System.out.println("@@@ id " + id);
            return baService.getBriefcaseAllowanceById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public BriefcaseAllowanceEntity createOrUpdateBriefcaseAllowanceContribution(@RequestBody BriefcaseAllowanceEntity loan) {
        BriefcaseAllowanceEntity entity = baService.createOrUpdateBriefcaseAllowance(loan);

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
        return baService.deleteBriefcaseAllowanceById(id);
    }

    @GetMapping("/manager")
    public List<BriefcaseAllowanceOutput> managerBriefcaseAllowanceGetAll() {
        try {
            List<BriefcaseAllowanceOutput> result = new ArrayList<BriefcaseAllowanceOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<BriefcaseAllowanceEntity> lstBriefcaseAllowance = baService.getAllBriefcaseAllowance();

            for (PFAccountEntity account : listAllAccount) {
                for (BriefcaseAllowanceEntity ba : lstBriefcaseAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 0) {
                        BriefcaseAllowanceOutput output = new BriefcaseAllowanceOutput();
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
                        output.invoiceAmount = ba.getInvoiceAmount();
                        output.invoiceDate = ba.getInvoiceDate();
                        output.invoiceNo = ba.getInvoiceNo();
                        output.vendorName = ba.getVendorName();
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
    public List<BriefcaseAllowanceOutput> hrBriefcaseAllowanceGetAll() {
        try {
            List<BriefcaseAllowanceOutput> result = new ArrayList<BriefcaseAllowanceOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<BriefcaseAllowanceEntity> lstBriefcaseAllowance = baService.getAllBriefcaseAllowance();

            for (PFAccountEntity account : listAllAccount) {
                for (BriefcaseAllowanceEntity ba : lstBriefcaseAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 1 && ba.getHRApproved() == 0) {
                        BriefcaseAllowanceOutput output = new BriefcaseAllowanceOutput();
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
                        output.invoiceAmount = ba.getInvoiceAmount();
                        output.invoiceDate = ba.getInvoiceDate();
                        output.invoiceNo = ba.getInvoiceNo();
                        output.vendorName = ba.getVendorName();
                        result.add(output);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
}
