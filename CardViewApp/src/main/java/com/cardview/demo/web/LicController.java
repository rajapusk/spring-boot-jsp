package com.cardview.demo.web;

import com.cardview.demo.model.PFAccountEntity;
import com.cardview.demo.outputModels.NomineeInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lic")
public class LicController {
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String driverUrl;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@GetMapping("/get1")
	public String lic() {
		return "lic";
	}
	
	
	@GetMapping("/")
	public String getPFAccount() {
		return "lic";
	}
	
	@RequestMapping(value = "/gridlicemp",method = RequestMethod.GET)
	public String gridpf(ModelMap model) {


		return "grid/gridlicemp";
	}

	@RequestMapping(value = "/getLic", method = RequestMethod.GET)
	public @ResponseBody
    List<NomineeInput> getLic() throws ClassNotFoundException {
		List<NomineeInput> emplicList = new ArrayList<NomineeInput>();
       // System.out.println("get employee ");
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
			Statement getFromDb = dbConnection.createStatement();
			ResultSet emplics = getFromDb
					.executeQuery("    SELECT  GENDER,NAME   FROM vetan10.TBL_NOMINEE;");
			while (emplics.next()) {
				NomineeInput input = new NomineeInput();


				input.address = emplics.getString("NAME");
				input.gender = emplics.getString("GENDER");
			  	emplicList.add(input);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return emplicList;
	}
	
}
