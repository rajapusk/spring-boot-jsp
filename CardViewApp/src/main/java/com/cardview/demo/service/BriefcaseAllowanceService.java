package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.BranchOutput;
import com.cardview.demo.repository.BriefcaseAllowanceRepository;
import com.cardview.demo.repository.PFAccountRepository;
import com.cardview.demo.repository.PFLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.*;

@Service
public class BriefcaseAllowanceService {
    @Autowired
    BriefcaseAllowanceRepository repository;

    @Autowired
    PFAccountRepository accountRepository;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String driverUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;


    public boolean validateRecord(long empCode) throws ClassNotFoundException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
            Statement getFromDb = dbConnection.createStatement();
            String query = " SELECT count(id) FROM vetan10.tbl_briefcase_allowance where empcode = "+empCode+" and MONTH(created_on) = MONTH(NOW()) AND YEAR(created_on) = MONTH(NOW());";

            ResultSet rs = getFromDb
                    .executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
           if(count > 0)
               return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return  false;
    }

    public List<BriefcaseAllowanceEntity> getAllBriefcaseAllowance()
    {
        List<BriefcaseAllowanceEntity> result = (List<BriefcaseAllowanceEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<BriefcaseAllowanceEntity>();
        }
    }

    public boolean deleteBriefcaseAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<BriefcaseAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public BriefcaseAllowanceEntity getBriefcaseAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<BriefcaseAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public BriefcaseAllowanceEntity createOrUpdateBriefcaseAllowance(BriefcaseAllowanceEntity entity)
    {
        long millis=System.currentTimeMillis();

        if(entity.getid()  == null)
        {
            entity.setCreatedOn(new java.sql.Date(millis));
            entity.setUpdatedOn(new java.sql.Date(millis));
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<BriefcaseAllowanceEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                BriefcaseAllowanceEntity newEntity = employee.get();
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setapproved(entity.getapproved());
                newEntity.setremarks(entity.getremarks());
                newEntity.setsubmitted(entity.getsubmitted());
                newEntity.setClaimAmount(entity.getClaimAmount());
                newEntity.setEntitledAmount(entity.getEntitledAmount());
                newEntity.setHrRemarkss(entity.getHrRemarks());
                newEntity.setManagerRemarks(entity.getManagerRemarks());
                newEntity.setInvoiceAmount(entity.getInvoiceAmount());
                newEntity.setInvoiceDate(entity.getInvoiceDate());
                newEntity.setInvoiceNo(entity.getInvoiceNo());
                entity.setUpdatedOn(new java.sql.Date(millis));
                entity.setVendorName(entity.getVendorName());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<BriefcaseAllowanceEntity> updateBriefcaseAllowance(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<BriefcaseAllowanceEntity> result = new ArrayList<BriefcaseAllowanceEntity>();
        long millis=System.currentTimeMillis();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<BriefcaseAllowanceEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                BriefcaseAllowanceEntity newEntity = employee.get();

                if (isManager == true) {
                    newEntity.setapproved(entity.approved);
                    newEntity.setManagerRemarks(entity.remarks);
                    newEntity.setUpdatedOn(new java.sql.Date(millis));
                } else {
                    newEntity.setHRApproved(entity.hrApproved);
                    newEntity.setHrRemarkss(entity.remarks);
                    newEntity.setUpdatedOn(new java.sql.Date(millis));
                }

                repository.save(newEntity);
                result.add(newEntity);
            }
        }

        return result;
    }

    public List<BriefcaseAllowanceEntity> getUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<BriefcaseAllowanceEntity> emplicList = new ArrayList<BriefcaseAllowanceEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, approved, claimamount, claimdate, document, empcode, entitledamount, hrapproved, hr_remarks, invoiceamount, invoicedate, invoiceno, managerremarks, remarks, submitted, vendorname, created_on, updated_on, hrremarks   FROM tbl_briefcase_allowance where submitted = 1 and approved = 0");
        while (rs.next()) {

            BriefcaseAllowanceEntity newEntity = new BriefcaseAllowanceEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.SetIdValue(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setapproved(rs.getByte("approved"));
            newEntity.setEmpCode(rs.getLong("empcode"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }

    public List<BriefcaseAllowanceEntity> getHrUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<BriefcaseAllowanceEntity> emplicList = new ArrayList<BriefcaseAllowanceEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, approved, claimamount, claimdate, document, empcode, entitledamount, hrapproved, hr_remarks, invoiceamount, invoicedate, invoiceno, managerremarks, remarks, submitted, vendorname, created_on, updated_on, hrremarks   FROM tbl_briefcase_allowance where submitted = 1 and approved = 1 and hrapproved = 0 ");
        while (rs.next()) {

            BriefcaseAllowanceEntity newEntity = new BriefcaseAllowanceEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.SetIdValue(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setapproved(rs.getByte("approved"));
            newEntity.setEmpCode(rs.getLong("empcode"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }
}
