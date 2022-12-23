package com.cardview.demo.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.EmpDocEntity;
import com.cardview.demo.model.EmployeeEntity;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.outputModels.PfLoanOutput;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.EmployeeService;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.PFLoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pfloan")

public class PFLoanController {

	
	@Value("${manager.mail}") private String _managerEmail;
	@Value("${spring.project.directory}")
	private String targetPath;
	private String docURL = "http://localhost:8080/static/images/pfloan";

	@Autowired
	PFAccountService paService;
	@Autowired
	PFLoanService pfService;

	@Autowired
	private EmailServiceImpl emailService;

	/**
	 * This method is used to fetch all employees from the database and set the
	 * values into the model attribute which will be used by html template to
	 * populate data.
	 *
	 * @param model This attribute is used to set values which will be used in the
	 *              html template to populate data
	 * @return String This returns the name of the html page to be displayed.
	 */

	@GetMapping("/get/{id}")
	public PFLoanEntity getEmployeeById(@PathVariable("id") Long id) {
		try {
			System.out.print("Entering...");

			// Long idvalue = Long.parseLong(id);
			return pfService.getPFAccountById(id);
		} catch (Exception ex) {
			return null;
		}

	}


	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public PFLoanEntity createOrUpdatePFLoan(@RequestBody PFLoanEntity loan) {
		PFLoanEntity entity = pfService.createOrUpdatePFLoan(loan);
		try {
			PFAccountEntity account = paService.getPFAccountById(loan.getempcode());
			String body = account.getNAME() + " has applied the loan. ";
			emailService.sendSimpleMail(_managerEmail, body, "Loan Application");

		} catch (Exception e) {
		}

		return entity;

	}

	@RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
	public EmpDocEntity uploadFile(@RequestParam("emp_doc") MultipartFile file, String pageId, String empCode) {
		try {
			if (pageId != null) {
				if (!file.isEmpty()) {
					return FileUploadHelper.uploadFile(file, pageId, empCode, "pfloan", pfService);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping("/getall/{type}")
	public List<PfLoanOutput> getPFAccount(@PathVariable("type") byte type) {
		try {
			List<PfLoanOutput> result = new ArrayList<PfLoanOutput>();
			List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
			List<PFLoanEntity> lstPFAccount = pfService.getAllPFLoan();

			for (PFAccountEntity account : listAllAccount) {
				for (PFLoanEntity loan : lstPFAccount) {
					if (account.getEMPCODE() == loan.getempcode() && type == loan.getType() && loan.getapproved() == 0) {
						PfLoanOutput output = new PfLoanOutput();
						output.id = loan.getid();
						output.advanceType = loan.getadvanceType();
						output.monthlySalary = account.getMONTHLY_SALARY();
						output.pfBalance = account.getPF_BALANCE();
						output.prevNetSalary = account.getPrevNetSalary();
						output.rateOfInterest = account.getRATEOFINTEREST();
						output.presentExperience = account.getPRESENT_EXPERIENCE();
						output.band = account.getBAND();
						output.branch = account.getBRANCH();
						output.designation = account.getDESIGNATION();
						output.doj = account.getDOJ();
						output.dor = account.getDOR();
						output.name = account.getNAME();
						output.empcode = account.getEMPCODE();
						output.serviceLeft = account.getSERVICE_LEFT();
						output.interest = account.getInterest();
						output.role = account.getROLE();
						output.netSalaryPercentage = account.getNetSalPer();
						output.docPath = (loan.getfileName() != null ? docURL + loan.getfileName() : null);
						output.noOfEMI = loan.getnoOfEMI();
						output.requiredAmount = loan.getrequiredAmount();
						output.emiAmount = loan.getemiAmount();
						output.newNetSalary = loan.getnewNetSalary();
						output.newNetSalaryPercentage = loan.getnewNetSalaryPer();
						output.remarks = loan.getremarks();
						output.submitted = loan.getsubmitted();
						output.approved = loan.getapproved();
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

	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	public boolean UpdatePFLoan(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
		List<PFLoanEntity> lstPFAccount = pfService.updatePFLoan(loan);
		for (PFLoanEntity entity : lstPFAccount) {
			PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
			String subject = "", body = "";

			if (entity.getapproved() == 1) {
				subject = "Loan Approved";
				body = "Your loan has been approved by manager.";
			} else if (entity.getapproved() == 2) {
				subject = "Loan Rejected";
				body = "Your loan has been rejected by manager.";
			}

			emailService.sendSimpleMail(account.getEmail(), body, subject);
		}
		return true;
	}

}
