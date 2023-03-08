package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.MedicalAllowanceEntity;
import com.cardview.demo.model.VehicleAllowanceEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.VehicleAllowanceRepository;
import com.cardview.demo.repository.PFAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleAllowanceService {
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    VehicleAllowanceRepository repository;

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


    public List<VehicleAllowanceEntity> getAllVehicleAllowance()
    {
        List<VehicleAllowanceEntity> result = (List<VehicleAllowanceEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<VehicleAllowanceEntity>();
        }
    }

    public boolean deleteVehicleAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<VehicleAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }

    }

    public VehicleAllowanceEntity getVehicleAllowanceById(Long id) throws RecordNotFoundException
    {
        Optional<VehicleAllowanceEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public VehicleAllowanceEntity createOrUpdateVehicleAllowance(VehicleAllowanceEntity entity)
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
            Optional<VehicleAllowanceEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                VehicleAllowanceEntity newEntity = employee.get();
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
                entity.setServiceCentreName(entity.getServiceCentreName());
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<VehicleAllowanceEntity> updateVehicleAllowance(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<VehicleAllowanceEntity> result = new ArrayList<VehicleAllowanceEntity>();
        long millis=System.currentTimeMillis();

        for(PfLoanUpdateInput entity : entityArray) {
            Optional<VehicleAllowanceEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                VehicleAllowanceEntity newEntity = employee.get();

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

    public List<VehicleAllowanceEntity> getUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<VehicleAllowanceEntity> emplicList = new ArrayList<VehicleAllowanceEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, empcode, approved, hrapproved, created_on, updated_on   FROM tbl_vehicle_allowance where submitted = 1 and approved = 0");
        while (rs.next()) {

            VehicleAllowanceEntity newEntity = new VehicleAllowanceEntity();
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

    public List<VehicleAllowanceEntity> getHrUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<VehicleAllowanceEntity> emplicList = new ArrayList<VehicleAllowanceEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, empcode, approved, hrapproved, created_on, updated_on   FROM tbl_vehicle_allowance where submitted = 1 and approved = 1 and hrapproved = 0 ");
        while (rs.next()) {

            VehicleAllowanceEntity newEntity = new VehicleAllowanceEntity();
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
