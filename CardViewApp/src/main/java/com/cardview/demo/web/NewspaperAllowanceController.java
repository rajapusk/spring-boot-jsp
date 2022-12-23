package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.EmpDocEntity;
import com.cardview.demo.model.NewspaperAllowanceEntity;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.outputModels.NewspaperAllowanceEntitledAmount;
import com.cardview.demo.outputModels.NewspaperAllowanceOutput;
import com.cardview.demo.service.NewspaperAllowanceService;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.PFLoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/newspaper")
public class NewspaperAllowanceController {

    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    NewspaperAllowanceService baService;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
	PFLoanService pfService;
    
    @GetMapping("/get/{id}")
    public NewspaperAllowanceEntity getNewspaperAllowanceById(@PathVariable("id") Long id) {
        try {
            System.out.println("@@@ id " + id);
            return baService.getNewspaperAllowanceById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public NewspaperAllowanceEntity createOrUpdateNewspaperAllowanceContribution(@RequestBody NewspaperAllowanceEntity loan) {
        NewspaperAllowanceEntity entity = baService.createOrUpdateNewspaperAllowance(loan);

        try {
            PFAccountEntity account = paService.getPFAccountById(loan.getEmpCode());
            String body = account.getNAME() + " has applied the Briefcase Allowance. ";
            emailService.sendSimpleMail(_managerEmail, body, "Newspaper Allowance Application");

        } catch (Exception e) {
        }

        return  entity;
    }

    @DeleteMapping("/delete/id")
    public boolean deleteNewspaperAllowanceById(@PathVariable("id") long id) throws RecordNotFoundException {
        return baService.deleteNewspaperAllowanceById(id);
    }

    @GetMapping("/manager")
    public List<NewspaperAllowanceOutput> managerNewspaperAllowanceGetAll() {
        try {
            List<NewspaperAllowanceOutput> result = new ArrayList<NewspaperAllowanceOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<NewspaperAllowanceEntity> lstNewspaperAllowance = baService.getAllNewspaperAllowance();

            for (PFAccountEntity account : listAllAccount) {
                for (NewspaperAllowanceEntity ba : lstNewspaperAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 0) {
                        NewspaperAllowanceOutput output = new NewspaperAllowanceOutput();
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
                        output.glcode = account.getGlcode();
                        output.months = ba.getMonths();
                        output.quarterType = ba.getQuarterType();
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
    public List<NewspaperAllowanceOutput> hrNewspaperAllowanceGetAll() {
        try {
            List<NewspaperAllowanceOutput> result = new ArrayList<NewspaperAllowanceOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<NewspaperAllowanceEntity> lstNewspaperAllowance = baService.getAllNewspaperAllowance();

            for (PFAccountEntity account : listAllAccount) {
                for (NewspaperAllowanceEntity ba : lstNewspaperAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 1 && ba.getHRApproved() == 0) {
                        NewspaperAllowanceOutput output = new NewspaperAllowanceOutput();
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
                        output.glcode = account.getGlcode();
                        output.months = ba.getMonths();
                        output.quarterType = ba.getQuarterType();
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
    public NewspaperAllowanceEntitledAmount getEntitledAmount() {
        try {
            return new NewspaperAllowanceEntitledAmount();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateNewspaperAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<NewspaperAllowanceEntity> lstVPF = baService.updateNewspaperAllowance(loan, true);

        for (NewspaperAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Newspaper Allowance Approved";
                body = "Your Newspaper Allowance has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "Newspaper Allowance Rejected";
                body = "Your Newspaper Allowance has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrNewspaperAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<NewspaperAllowanceEntity> lstVPF = baService.updateNewspaperAllowance(loan, false);

        for (NewspaperAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Newspaper Allowance Approved";
                body = "Your Newspaper Allowance has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "Newspaper Allowance Rejected";
                body = "Your Newspaper Allowance has been rejected by HR.";
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
					return FileUploadHelper.uploadFile(file, pageId, empCode, "newspaper", pfService);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
