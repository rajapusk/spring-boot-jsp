package com.cardview.demo.service;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cardview.demo.model.EmployeeEntity;
import com.cardview.demo.model.MyImage;
import com.cardview.demo.model.PFAccountEntity;

import com.cardview.demo.outputModels.LedgerOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.repository.PFAccountRepository;

@Service
public class PFAccountService {
	
	@Value("${spring.project.directory}")
	private String targetPath;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String driverUrl;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Autowired
	PFAccountRepository repository;

	public List<PFAccountEntity> getAllPFAccountView() throws ClassNotFoundException, SQLException {
		List<PFAccountEntity> emplicList = new ArrayList<PFAccountEntity>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
		Statement getFromDb = dbConnection.createStatement();

		ResultSet emplics = getFromDb
				.executeQuery("select EMPCODE, NAME, DOJ, PRESENT_EXPERIENCE, DOR, SERVICE_LEFT, MONTHLY_SALARY, PF_BALANCE, Interest, DESIGNATION, GRADE, BAND, " +
						"ROLE, BRANCH, PREVNETSALARY, NETSALPER, RATEOFINTEREST, net_sal_per, prev_net_salary, dob, location, presentvpf, worksitecode, email, pf_doj, gender, nameasperaadhaar, panno, pf_nps_ac_no, glcode from pfAccountView;");
		while (emplics.next()) {
			PFAccountEntity input = new PFAccountEntity();

			input.setEMPCODE(emplics.getLong("EMPCODE"));
			input.setNAME(emplics.getString("NAME"));
			input.setDOB(emplics.getDate("DOJ"));
			input.setPRESENT_EXPERIENCE(emplics.getInt("PRESENT_EXPERIENCE"));
			input.setDOR(emplics.getDate("DOR"));
			input.setSERVICE_LEFT(emplics.getInt("SERVICE_LEFT"));
			input.setMONTHLY_SALARY(emplics.getInt("MONTHLY_SALARY"));
			input.setPF_BALANCE(emplics.getLong("PF_BALANCE"));
			input.setInterest(emplics.getInt("Interest"));
			input.setDESIGNATION(emplics.getString("DESIGNATION"));
			input.setGRADE(emplics.getString("GRADE"));
			input.setBAND(emplics.getString("BAND"));
			input.setROLE(emplics.getString("ROLE"));
			input.setBRANCH(emplics.getInt("BRANCH"));
			input.setPrevNetSalary(emplics.getInt("PREVNETSALARY"));
			input.setNetSalPer(emplics.getDouble("NETSALPER"));
			input.setRATEOFINTEREST(emplics.getInt("RATEOFINTEREST"));
			input.setNetSalPer(emplics.getLong("net_sal_per"));
			input.setPrevNetSalary(emplics.getInt("prev_net_salary"));
			input.setDOB(emplics.getDate("dob"));
			input.setLocation(emplics.getString("location"));
			input.setPresentVPF(emplics.getLong("presentvpf"));
			input.setWorksiteCode(emplics.getString("worksitecode"));
			input.setEmail(emplics.getString("email"));
			input.setPfDOJ(emplics.getDate("pf_doj"));
			input.setGender(emplics.getString("gender"));
			input.setNameInAadhaar(emplics.getString("nameasperaadhaar"));
			input.setPanNo(emplics.getString("panno"));
			input.setGlcode(emplics.getString("glcode"));



			emplicList.add(input);

		}

		return  emplicList;
	}

	public List<PFAccountEntity> getAllPFAccount()
	{
		List<PFAccountEntity> result = (List<PFAccountEntity>) repository.findAll();
		
		if(result.size() > 0) {

			/*
			 * for (PFAccountEntity entity: result) {
			 * retriveAttachmentsAndStore(entity.getAttach()); }
			 */
			return result;
		} else {
			return new ArrayList<PFAccountEntity>();
		}
	}
	
	public PFAccountEntity getPFAccountById(long id) throws RecordNotFoundException
	{
		Optional<PFAccountEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
}
