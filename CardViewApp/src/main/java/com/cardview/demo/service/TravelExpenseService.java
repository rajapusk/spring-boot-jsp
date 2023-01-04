package com.cardview.demo.service;

import com.cardview.demo.outputModels.BranchOutput;
import com.cardview.demo.outputModels.ContributionOutput;
import com.cardview.demo.outputModels.LedgerOutput;
import com.cardview.demo.outputModels.WithdrawalOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TravelExpenseService {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String driverUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    public BranchOutput getBranchByCode(int code) throws ClassNotFoundException {
        BranchOutput output = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
            Statement getFromDb = dbConnection.createStatement();
            String query = " SELECT BRANCH_CODE,LOCATION_NAME, City, POPULATION, Type, Category FROM vetan10.tbl_branches where BRANCH_CODE = "+code+"";
            ResultSet emplics = getFromDb
                    .executeQuery(query);
            while (emplics.next()) {
                output = new BranchOutput();

                output.branchCode = emplics.getInt("BRANCH_CODE");
                output.city = emplics.getString("City");
                output.population = emplics.getString("POPULATION");
                output.locationName = emplics.getString("LOCATION_NAME");
                output.category = emplics.getString("Category");
                output.type = emplics.getString("Type");
                break;
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

}
