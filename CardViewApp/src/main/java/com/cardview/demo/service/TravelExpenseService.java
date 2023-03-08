package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFNomineeEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.TravelExpenseEntity;
import com.cardview.demo.model.VehicleAllowanceEntity;
import com.cardview.demo.outputModels.*;
import com.cardview.demo.repository.TravelExpenseDetailRepository;
import com.cardview.demo.repository.TravelExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                output.Area1_12L = emplics.getInt("Area1_12L");
                output.Area1_7L = emplics.getInt("Area1_7L");
                output.Others = emplics.getInt("Others");
                output.State_capital = emplics.getInt("State_Capital");
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
                output.Area1_12L = emplics.getInt("Area1_12L");
                output.Area1_7L = emplics.getInt("Area1_7L");
                output.Others = emplics.getInt("Others");
                output.State_capital = emplics.getInt("State_Capital");
                haltingEntitlementAmountOutputList.add(output);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return haltingEntitlementAmountOutputList;
    }

    public List<TravelExpenseEntity> getAllTravelExpense()
    {
        List<TravelExpenseEntity> result = (List<TravelExpenseEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<TravelExpenseEntity>();
        }
    }

    public TravelExpenseEntity getTravelExpenseById(Long id) throws RecordNotFoundException
    {
        Optional<TravelExpenseEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public boolean deleteTravelExpenseById(Long id) throws RecordNotFoundException
    {
        Optional<TravelExpenseEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public TravelExpenseEntity createOrUpdateTravelExpenseEntity(TravelExpenseEntity entity)
    {
        long millis=System.currentTimeMillis();
        if(entity.getId()  == null || entity.getId()  == 0)
        {
            entity = repository.save(entity);
            entity.setCreatedOn(new java.sql.Date(millis));
            entity.setUpdatedOn(new java.sql.Date(millis));
            return entity;
        }
        else
        {
            Optional<TravelExpenseEntity> employee = repository.findById(entity.getId());

            if(employee.isPresent())
            {
                TravelExpenseEntity newEntity = employee.get();
                newEntity.setL1Approved(entity.getL1Approved());
                newEntity.setL2Approved(entity.getL2Approved());
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setEmpCode(entity.getEmpCode());
                newEntity.setSubmitted(entity.getSubmitted());
                newEntity.setUpdatedOn(new java.sql.Date(millis));
                newEntity.setAdvanceAmount(entity.getAdvanceAmount());
                newEntity.setTravelPurpose(entity.getTravelPurpose());
                newEntity.setDestinationBranchCode(entity.getDestinationBranchCode());
                newEntity.setHrRemarks(entity.getHrRemarks());
                newEntity.setL1ManagerRemarks(entity.getL1ManagerRemarks());
                newEntity.setL2ManagerRemarks(entity.getL2ManagerRemarks());
                newEntity.setOriginBranchCode(entity.getOriginBranchCode());
                newEntity.setPermissionMode(entity.getPermissionMode());
                newEntity.setPermittedBy(entity.getPermittedBy());
                newEntity.setPermittedName(entity.getPermittedName());
                newEntity.setPermittedTime(entity.getPermittedTime());
                newEntity.setPermittedDate(entity.getPermittedDate());
                newEntity.setTotalAmount(entity.getTotalAmount());
                newEntity.setSubmitted(entity.getSubmitted());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<TravelExpenseEntity> updateTravelExpenseEntity(PfLoanUpdateInput[] entityArray, String type) {
        List<TravelExpenseEntity> result = new ArrayList<TravelExpenseEntity>();
        long millis=System.currentTimeMillis();
        for (PfLoanUpdateInput entity : entityArray) {
            Optional<TravelExpenseEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                TravelExpenseEntity newEntity = employee.get();

                if (type == "l1") {

                    newEntity.setL1Approved(entity.l1Approved);
                    newEntity.setL1ManagerRemarks(entity.remarks);
                    if(newEntity.getTotalAmount() >=2000)
                    {
                        newEntity.setL2Approved(entity.l1Approved);
                    }
                } else if (type == "l2") {
                    newEntity.setL2Approved(entity.l2Approved);
                    newEntity.setHRApproved(entity.hrApproved);
                    newEntity.setL2ManagerRemarks(entity.remarks);
                } else
                {
                    newEntity.setHRApproved(entity.hrApproved);
                    newEntity.setHrRemarks(entity.remarks);
                }

                newEntity.setUpdatedOn(new java.sql.Date(millis));
                repository.save(newEntity);
                result.add(newEntity);
            }
        }

        return result;
    }

    public List<TravelExpenseEntity> getL1UnApproveEntity() throws ClassNotFoundException, SQLException {
        List<TravelExpenseEntity> emplicList = new ArrayList<TravelExpenseEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, emp_Code, hrapproved, created_on, updated_on, l1approved, l2approved,total_Amount   FROM tbl_travel_expense where submitted = 1 and l1approved = 0 ");
        while (rs.next()) {

            TravelExpenseEntity newEntity = new TravelExpenseEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.SetIdValue(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setL1Approved(rs.getByte("l1approved"));
            newEntity.setL2Approved(rs.getByte("l2approved"));
            newEntity.setTotalAmount(rs.getDouble("total_Amount"));
            newEntity.setEmpCode(rs.getLong("emp_code"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }

    public List<TravelExpenseEntity> getL2UnApproveEntity() throws ClassNotFoundException, SQLException {
        List<TravelExpenseEntity> emplicList = new ArrayList<TravelExpenseEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, emp_Code, hrapproved, created_on, updated_on, l1approved, l2approved, total_Amount   FROM tbl_travel_expense where submitted = 1 and l1approved = 1 and l2approved = 0 ");
        while (rs.next()) {

            TravelExpenseEntity newEntity = new TravelExpenseEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.SetIdValue(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setL1Approved(rs.getByte("l1approved"));
            newEntity.setL2Approved(rs.getByte("l2approved"));
            newEntity.setTotalAmount(rs.getDouble("total_Amount"));
            newEntity.setEmpCode(rs.getLong("emp_code"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }

    public List<TravelExpenseEntity> getHrUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<TravelExpenseEntity> emplicList = new ArrayList<TravelExpenseEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, emp_Code, hrapproved, created_on, updated_on, l1approved, l2approved, total_Amount   FROM tbl_travel_expense where submitted = 1 and l1approved = 1 and  l2approved = 1 and hrapproved = 0 ");
        while (rs.next()) {

            TravelExpenseEntity newEntity = new TravelExpenseEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.SetIdValue(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setL1Approved(rs.getByte("l1approved"));
            newEntity.setL2Approved(rs.getByte("l2approved"));
            newEntity.setTotalAmount(rs.getDouble("total_Amount"));
            newEntity.setEmpCode(rs.getLong("emp_code"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }


}

