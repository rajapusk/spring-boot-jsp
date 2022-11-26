package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.VpfContributionOutput;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.VpfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vpf")
public class VpfContributionController {
	@Value("${manager.mail}") private String _managerEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    VpfService vpfService;
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/get/{id}")
    public VpfContributionEntity getEmployeeById(@PathVariable("id") Long id) {
        try {
            System.out.println("@@@ id " + id);
            return vpfService.getVpfContributionById(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public VpfContributionEntity createOrUpdateVpfContribution(@RequestBody VpfContributionEntity loan) {
        VpfContributionEntity entity = vpfService.createOrUpdateVpfContribution(loan);

        try {
            PFAccountEntity account = paService.getPFAccountById(loan.getempcode());
            String body = account.getNAME() + " has applied the VPF. ";
            emailService.sendSimpleMail(_managerEmail, body, "VPF Application");

        } catch (Exception e) {
        }

        return  entity;
    }

    @DeleteMapping("/delete/id")
    public boolean getPFAccount(@PathVariable("id") long id) throws RecordNotFoundException {

        return vpfService.deleteVpfContributionById(id);
    }

    @GetMapping("/manager")
    public List<VpfContributionOutput> getVPFManagerGetAll() {
        try {
            List<VpfContributionOutput> result = new ArrayList<VpfContributionOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<VpfContributionEntity> lstVPFAccount = vpfService.getAllVpf();

            for (PFAccountEntity account : listAllAccount) {
                for (VpfContributionEntity loan : lstVPFAccount) {
                    if (account.getEMPCODE() == loan.getempcode() && loan.getapproved() == 0) {
                        VpfContributionOutput output = new VpfContributionOutput();
                        output.id = loan.getid();
                        output.presentVPF = account.getPresentVPF();
                        output.monthlySalary = account.getMONTHLY_SALARY();
                        output.revisedVPF = loan.getRevisedVPF();
                        output.prevNetSalary = account.getPrevNetSalary();
                        output.band = account.getBAND();
                        output.designation = account.getDESIGNATION();
                        output.doj = account.getDOJ();
                        output.name = account.getNAME();
                        output.empcode = account.getEMPCODE();
                        output.netSalaryPercentage = account.getNetSalPer();
                        output.newNetSalary = loan.getnewNetSalary();
                        output.newNetSalaryPercentage = loan.getnewNetSalaryPer();
                        output.remarks = loan.getremarks();
                        output.submitted = loan.getsubmitted();
                        output.approved = loan.getapproved();
                        output.hrApproved = loan.getHRApproved();
                        output.location = account.getLocation();
                        output.worksiteCode = account.getWorksiteCode();
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
    public List<VpfContributionOutput> getVPFHrGetAll() {
        try {
            List<VpfContributionOutput> result = new ArrayList<VpfContributionOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<VpfContributionEntity> lstVPFAccount = vpfService.getAllVpf();

            for (PFAccountEntity account : listAllAccount) {
                for (VpfContributionEntity loan : lstVPFAccount) {
                    if (account.getEMPCODE() == loan.getempcode() && loan.getapproved() == 1 && loan.getHRApproved() == 0) {
                        VpfContributionOutput output = new VpfContributionOutput();
                        output.id = loan.getid();
                        output.presentVPF = loan.getPresentVPF();
                        output.monthlySalary = account.getMONTHLY_SALARY();
                        output.revisedVPF = loan.getRevisedVPF();
                        output.prevNetSalary = account.getPrevNetSalary();
                        output.band = account.getBAND();
                        output.designation = account.getDESIGNATION();
                        output.doj = account.getDOJ();
                        output.name = account.getNAME();
                        output.empcode = account.getEMPCODE();
                        output.netSalaryPercentage = account.getNetSalPer();
                        output.newNetSalary = loan.getnewNetSalary();
                        output.newNetSalaryPercentage = loan.getnewNetSalaryPer();
                        output.remarks = loan.getremarks();
                        output.submitted = loan.getsubmitted();
                        output.approved = loan.getapproved();
                        output.hrApproved = loan.getHRApproved();
                        output.location = account.getLocation();
                        output.worksiteCode = account.getWorksiteCode();
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

    @RequestMapping(path = "/manager/update", method = RequestMethod.PUT)
    public boolean UpdateVPFLoan(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<VpfContributionEntity> lstVPF = vpfService.updateVpfContribution(loan, true);

        for (VpfContributionEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "VPF Approved";
                body = "Your VPF has been approved by manager.";
            } else if (entity.getapproved() == 2) {
                subject = "VPF Rejected";
                body = "Your VPF has been rejected by manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrVPFLoan(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<VpfContributionEntity> lstVPF = vpfService.updateVpfContribution(loan, false);

        for (VpfContributionEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
            String subject = "", body = "";

            if (entity.getapproved() == 1) {
                subject = "VPF Approved";
                body = "Your VPF has been approved by HR.";
            } else if (entity.getapproved() == 2) {
                subject = "VPF Rejected";
                body = "Your VPF has been rejected by HR.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }
}
