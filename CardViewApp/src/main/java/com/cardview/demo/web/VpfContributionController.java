package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.VpfContributionOutput;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.VpfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vpf")
public class VpfContributionController {

    @Autowired
    PFAccountService paService;
    @Autowired
    VpfService vpfService;
    @Autowired private EmailServiceImpl emailService;
    
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
        return vpfService.createOrUpdateVpfContribution(loan);
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
    public boolean UpdateVPFLoan(@RequestBody PfLoanUpdateInput[] loan) {
        return vpfService.updateVpfContribution(loan,true    );

    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrVPFLoan(@RequestBody PfLoanUpdateInput[] loan) {
        return vpfService.updateVpfContribution(loan, false);

    }
}
