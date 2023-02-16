package com.cardview.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.outputModels.PfLoanOutput;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.PFLoanService;

@RestController
@RequestMapping("/pfaccount")

public class PFAccountController {
	@Value("${spring.project.directory}")
	private String targetPath;

	@Autowired
	PFAccountService paService;
	
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
	public PFAccountEntity getEmployeeById(@PathVariable("id")  String id) {
		try {
			System.out.print("Entering...");
			return paService.getPFAccountById(Long.parseLong(id));
		} catch (Exception ex) {
			return null;
		}
	}
	
	@GetMapping("/getall")
	public List<PFAccountEntity> getPFAccount() {
		try {
			System.out.print("Entering...");
			return paService.getAllPFAccountView();
		} catch (Exception ex) {
			return null;
		}

	}

}
