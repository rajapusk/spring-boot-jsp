package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.VehicleAllowanceEntity;
import com.cardview.demo.model.EmpDocEntity;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.outputModels.VehicleAllowanceEntitledAmount;
import com.cardview.demo.outputModels.VehicleAllowanceOutput;
import com.cardview.demo.service.VehicleAllowanceService;
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
@RequestMapping("/vehicle")
public class VehicleAllowanceController {

    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    VehicleAllowanceService baService;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
   	PFLoanService pfService;
    
    @GetMapping("/get/{id}")
    public VehicleAllowanceEntity getVehicleAllowanceById(@PathVariable("id") Long id) {
        try {
            System.out.println("@@@ id " + id);
            return baService.getVehicleAllowanceById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public VehicleAllowanceEntity createOrUpdateVehicleAllowanceContribution(@RequestBody VehicleAllowanceEntity loan) {
        VehicleAllowanceEntity entity = baService.createOrUpdateVehicleAllowance(loan);

        try {
            PFAccountEntity account = paService.getPFAccountById(loan.getEmpCode());
            String body = account.getNAME() + " has applied the Vehicle Allowance. ";
            emailService.sendSimpleMail(_managerEmail, body, "Vehicle Allowance Application");

        } catch (Exception e) {
        }

        return  entity;
    }

    @DeleteMapping("/delete/id")
    public boolean deleteVehicleAllowanceById(@PathVariable("id") long id) throws RecordNotFoundException {
        return baService.deleteVehicleAllowanceById(id);
    }

    @GetMapping("/manager")
    public List<VehicleAllowanceOutput> managerVehicleAllowanceGetAll() {
        try {
            List<VehicleAllowanceOutput> result = new ArrayList<VehicleAllowanceOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<VehicleAllowanceEntity> lstVehicleAllowance = baService.getAllVehicleAllowance();

            for (PFAccountEntity account : listAllAccount) {
                for (VehicleAllowanceEntity ba : lstVehicleAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 0) {
                        VehicleAllowanceOutput output = new VehicleAllowanceOutput();
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
                        output.serviceCentreName = ba.getServiceCentreName();
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
    public List<VehicleAllowanceOutput> hrVehicleAllowanceGetAll() {
        try {
            List<VehicleAllowanceOutput> result = new ArrayList<VehicleAllowanceOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<VehicleAllowanceEntity> lstVehicleAllowance = baService.getAllVehicleAllowance();

            for (PFAccountEntity account : listAllAccount) {
                for (VehicleAllowanceEntity ba : lstVehicleAllowance) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getapproved() == 1 && ba.getHRApproved() == 0) {
                        VehicleAllowanceOutput output = new VehicleAllowanceOutput();
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
                        output.serviceCentreName = ba.getServiceCentreName();
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

    @GetMapping("/getEntitle")
    public VehicleAllowanceEntitledAmount getEntitledAmount() {
        try {
            return new VehicleAllowanceEntitledAmount();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateVehicleAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<VehicleAllowanceEntity> lstVPF = baService.updateVehicleAllowance(loan, true);

        for (VehicleAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Vehicle Allowance Approved";
                body = "Your Vehicle Allowance has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "Vehicle Allowance Rejected";
                body = "Your Vehicle Allowance has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrVehicleAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<VehicleAllowanceEntity> lstVPF = baService.updateVehicleAllowance(loan, false);

        for (VehicleAllowanceEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "Vehicle Allowance Approved";
                body = "Your Vehicle Allowance has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "Vehicle Allowance Rejected";
                body = "Your Vehicle Allowance has been rejected by HR.";
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
   					return FileUploadHelper.uploadFile(file, pageId, empCode, "vehical", pfService);
   				}
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   		}

   		return null;
   	}
}
