package com.cardview.demo.service;

import com.cardview.demo.outputModels.*;
import com.cardview.demo.repository.TravelExpenseDetailRepository;
import com.cardview.demo.repository.TravelExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TravelExpenseRepository repository;

    public BranchOutput getBranchByCode(int code) throws ClassNotFoundException {
        BranchOutput output = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
            Statement getFromDb = dbConnection.createStatement();
            String query = " SELECT BRANCH_CODE,LOCATION_NAME, City, POPULATION, Type, Category FROM vetan10.tbl_branches where BRANCH_CODE = "+code+";";
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

    public List<LodgingEntitlementAmountOutput> getLodgingEntitlementAmount() throws ClassNotFoundException {
        List<LodgingEntitlementAmountOutput> lodgingEntitlementAmountOutputList = new ArrayList<LodgingEntitlementAmountOutput>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
            Statement getFromDb = dbConnection.createStatement();
            String query = " SELECT DESIGNATION, Area1_12L, Area1_7L,Others, State_Capital FROM vetan10.tbl_lodging_entitlement;";
            ResultSet emplics = getFromDb
                    .executeQuery(query);
            while (emplics.next()) {
                LodgingEntitlementAmountOutput output = new LodgingEntitlementAmountOutput();

                output.designation = emplics.getString("DESIGNATION");
                output.Area1_2L = emplics.getInt("Area1_12L");
                output.Area1_7L = emplics.getInt("Area1_7L");
                output.Others = emplics.getInt("Others");
                output.stateCapital = emplics.getInt("State_Capital");
                lodgingEntitlementAmountOutputList.add(output);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lodgingEntitlementAmountOutputList;
    }

    public List<HaltingEntitlementAmountOutput> getHaltingEntitlementAmount() throws ClassNotFoundException {
        List<HaltingEntitlementAmountOutput> haltingEntitlementAmountOutputList = new ArrayList<HaltingEntitlementAmountOutput>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
            Statement getFromDb = dbConnection.createStatement();
            String query = " SELECT DESIGNATION, Area1_12L, Area1_7L,Others, State_Capital FROM vetan10.tbl_Halting_entitlement;";
            ResultSet emplics = getFromDb
                    .executeQuery(query);
            while (emplics.next()) {
                HaltingEntitlementAmountOutput output = new HaltingEntitlementAmountOutput();

                output.designation = emplics.getString("DESIGNATION");
                output.Area1_2L = emplics.getInt("Area1_2L");
                output.Area1_7L = emplics.getInt("Area1_7L");
                output.Others = emplics.getInt("Others");
                output.stateCapital = emplics.getInt("State_Capital");
                haltingEntitlementAmountOutputList.add(output);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return haltingEntitlementAmountOutputList;
    }

}
