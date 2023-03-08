package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.*;
import com.cardview.demo.outputModels.BriefcaseAllowanceEntitledAmount;
import com.cardview.demo.outputModels.BriefcaseAllowanceOutput;
import com.cardview.demo.service.BriefcaseAllowanceService;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.PFLoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ba")
public class BriefcaseAllowanceController {

    @Value("${manager.mail}") private String _managerEmail;
    @Value("${hr.mail}") private String _hrEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    BriefcaseAllowanceService baService;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
   	PFLoanService pfService;
    
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
        try {
        if(baService.validateRecord(loan.getEmpCode()) == false)
        {
            BriefcaseAllowanceEntity entity = baService.createOrUpdateBriefcaseAllowance(loan);


            PFAccountEntity account = paService.getPFAccountById(loan.getEmpCode());
            String body = account.getNAME() + " has applied the Briefcase Allowance. ";
            emailService.sendSimpleMail(_managerEmail, body, "Briefcase Allowance Application");
            return  entity;
        }
        else {
            throw new EntityExistsException();
        }
        }
        catch (Exception e) {

        }

        return null;
    }

    @DeleteMapping("/delete/id")
    public boolean deleteBriefcaseAllowanceById(@PathVariable("id") long id) throws RecordNotFoundException {
        return baService.deleteBriefcaseAllowanceById(id);
    }

    @GetMapping("/manager")
    public List<BriefcaseAllowanceOutput> managerBriefcaseAllowanceGetAll() {
        try {
            List<BriefcaseAllowanceOutput> result = new ArrayList<BriefcaseAllowanceOutput>();

            List<BriefcaseAllowanceEntity> lstBriefcaseAllowance = baService.getAllBriefcaseAllowance();
            String empCodes = "";
            for (BriefcaseAllowanceEntity ba : lstBriefcaseAllowance) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }
           if(lstBriefcaseAllowance.size() > 0) {
               StringBuffer sb = new StringBuffer(empCodes);
               sb.deleteCharAt(sb.length() - 1);
               List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

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
                        output.managerRemarks = ba.getManagerRemarks();
                        output.hrRemarks=ba.getHrRemarks();
                        result.add(output);
                    }
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

            List<BriefcaseAllowanceEntity> lstBriefcaseAllowance = baService.getAllBriefcaseAllowance();
            String empCodes = "";
            for (BriefcaseAllowanceEntity ba : lstBriefcaseAllowance) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }
            if(lstBriefcaseAllowance.size() > 0) {
                StringBuffer sb = new StringBuffer(empCodes);
                sb.deleteCharAt(sb.length() - 1);
                List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

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
                            output.managerRemarks = ba.getManagerRemarks();
                            output.hrRemarks = ba.getHrRemarks();
                            output.grade = account.getGRADE();
                            result.add(output);
                        }
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    @GetMapping("/getEntitle")
    public BriefcaseAllowanceEntitledAmount getEntitledAmount() {
        try {
            return new BriefcaseAllowanceEntitledAmount();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateBriefcaseAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<BriefcaseAllowanceEntity> lstVPF = baService.updateBriefcaseAllowance(loan, true);

        for (BriefcaseAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Briefcase Allowance Approved";
                body = "Your Briefcase Allowance has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "Briefcase Allowance Rejected";
                body = "Your Briefcase Allowance has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrBriefcaseAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<BriefcaseAllowanceEntity> lstVPF = baService.updateBriefcaseAllowance(loan, false);

        for (BriefcaseAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Briefcase Allowance Approved";
                body = "Your Briefcase Allowance has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "Briefcase Allowance Rejected";
                body = "Your Briefcase Allowance has been rejected by HR.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }
    
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
   	public EmpDocEntity uploadFile(@RequestParam("emp_doc") MultipartFile file, String pageId, String empCode) {
   		try {
   			if (pageId != null) {
   				if (!file.isEmpty()) {
   					return FileUploadHelper.uploadFile(file, pageId, empCode, "briefcase", pfService);
   				}
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   		}

   		return null;
   	}

}
