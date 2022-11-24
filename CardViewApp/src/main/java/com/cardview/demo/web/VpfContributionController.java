package com.cardview.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.VpfContributionOutput;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.VpfService;

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

    @GetMapping("/getall")
    public List<VpfContributionOutput> getPFAccount() {
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
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public boolean UpdatePFLoan(@RequestBody PfLoanUpdateInput[] loan) {
        return vpfService.updateVpfContribution(loan);

    }
}
