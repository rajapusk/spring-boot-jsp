package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.*;
import com.cardview.demo.outputModels.MedicalAllowanceOutput;
import com.cardview.demo.service.MedicalAllowanceService;
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
@RequestMapping("/medical")
public class MedicalAllowanceController {

    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    MedicalAllowanceService baService;
    @Autowired
    private EmailServiceImpl emailService;
    
    @Autowired
	PFLoanService pfService;

    @GetMapping("/get/{id}")
    public MedicalAllowanceEntity getMedicalAllowanceById(@PathVariable("id") Long id) {
        try {
            System.out.println("@@@ id " + id);
            return baService.getMedicalAllowanceById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public MedicalAllowanceEntity createOrUpdateMedicalAllowanceContribution(@RequestBody MedicalAllowanceEntity loan) {
        MedicalAllowanceEntity entity = baService.createOrUpdateMedicalAllowance(loan);

        try {
            PFAccountEntity account = paService.getPFAccountById(loan.getEmpCode());
            String body = account.getNAME() + " has applied the Medical Allowance. ";
            emailService.sendSimpleMail(_managerEmail, body, "Medical Allowance Application");

        } catch (Exception e) {
        }

        return  entity;
    }

    @DeleteMapping("/delete/id")
    public boolean deleteMedicalAllowanceById(@PathVariable("id") long id) throws RecordNotFoundException {
        return baService.deleteMedicalAllowanceById(id);
    }

    @GetMapping("/manager")
    public List<MedicalAllowanceOutput> managerMedicalAllowanceGetAll() {
        try {
            List<MedicalAllowanceOutput> result = new ArrayList<MedicalAllowanceOutput>();
            List<MedicalAllowanceEntity> lstMedicalAllowance = baService.getAllMedicalAllowance();
            String empCodes = "";
            for (MedicalAllowanceEntity ba : lstMedicalAllowance) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }

            if(lstMedicalAllowance.size() > 0) {
                StringBuffer sb = new StringBuffer(empCodes);
                sb.deleteCharAt(sb.length() - 1);
                List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

                for (PFAccountEntity account : listAllAccount) {
                    for (MedicalAllowanceEntity ba : lstMedicalAllowance) {
                        if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 0) {
                            MedicalAllowanceOutput output = new MedicalAllowanceOutput();
                            output.id = ba.getid();
                            output.dob = account.getDOB();
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
                            output.hospitalName = ba.getHospitalName();
                            output.managerRemarks = ba.getManagerRemarks();
                            output.hrRemarks = ba.getHrRemarks();
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
    public List<MedicalAllowanceOutput> hrMedicalAllowanceGetAll() {
        try {
            List<MedicalAllowanceOutput> result = new ArrayList<MedicalAllowanceOutput>();
            List<MedicalAllowanceEntity> lstMedicalAllowance = baService.getAllMedicalAllowance();
            String empCodes = "";
            for (MedicalAllowanceEntity ba : lstMedicalAllowance) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }

            if(lstMedicalAllowance.size() > 0) {
                StringBuffer sb = new StringBuffer(empCodes);
                sb.deleteCharAt(sb.length() - 1);
                List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

                for (PFAccountEntity account : listAllAccount) {
                    for (MedicalAllowanceEntity ba : lstMedicalAllowance) {
                        if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 1 && ba.getHRApproved() == 0) {
                            MedicalAllowanceOutput output = new MedicalAllowanceOutput();
                            output.id = ba.getid();
                            output.dob = account.getDOB();
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
                            output.hospitalName = ba.getHospitalName();
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

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateMedicalAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<MedicalAllowanceEntity> lstVPF = baService.updateMedicalAllowance(loan, true);

        for (MedicalAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Medical Allowance Approved";
                body = "Your Medical Allowance has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "Medical Allowance Rejected";
                body = "Your Medical Allowance has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrMedicalAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<MedicalAllowanceEntity> lstVPF = baService.updateMedicalAllowance(loan, false);

        for (MedicalAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Medical Allowance Approved";
                body = "Your Medical Allowance has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "Medical Allowance Rejected";
                body = "Your Medical Allowance has been rejected by HR.";
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
					return FileUploadHelper.uploadFile(file, pageId, empCode, "medical", pfService);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
