package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.FcaEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.repository.BriefcaseAllowanceRepository;
import com.cardview.demo.repository.FcaRepository;
import com.cardview.demo.repository.PFAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FcaService {

    @Autowired
    FcaRepository repository;

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

    public List<FcaEntity> getAllFCA()
    {
        List<FcaEntity> result = (List<FcaEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<FcaEntity>();
        }
    }

    public boolean deleteFCAById(Long id) throws RecordNotFoundException
    {
        Optional<FcaEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public FcaEntity getFCAById(Long id) throws RecordNotFoundException
    {
        Optional<FcaEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public FcaEntity createOrUpdateFCA(FcaEntity entity)
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
            Optional<FcaEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                FcaEntity newEntity = employee.get();
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setapproved(entity.getapproved());
                newEntity.setremarks(entity.getremarks());
                newEntity.setsubmitted(entity.getsubmitted());
                newEntity.setClaimAmount(entity.getClaimAmount());
                newEntity.setEntitledAmount(entity.getEntitledAmount());
                newEntity.setHrRemarkss(entity.getHrRemarks());
                newEntity.setManagerRemarks(entity.getManagerRemarks());
                newEntity.setMonths(entity.getMonths());
                newEntity.setQuarterType(entity.getQuarterType());
                entity.setUpdatedOn(new java.sql.Date(millis));
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<FcaEntity> updateFCA(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<FcaEntity> result = new ArrayList<FcaEntity>();
        long millis=System.currentTimeMillis();
        for (PfLoanUpdateInput entity : entityArray) {
            Optional<FcaEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                FcaEntity newEntity = employee.get();

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

    public List<FcaEntity> getUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<FcaEntity> emplicList = new ArrayList<FcaEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, empcode, approved, hrapproved, created_on, updated_on   FROM TBL_FCA where submitted = 1 and approved = 0");
        while (rs.next()) {

            FcaEntity newEntity = new FcaEntity();
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

    public List<FcaEntity> getHrUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<FcaEntity> emplicList = new ArrayList<FcaEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, empcode, approved, hrapproved, created_on, updated_on   FROM TBL_FCA where submitted = 1 and approved = 1 and hrapproved = 0 ");
        while (rs.next()) {

            FcaEntity newEntity = new FcaEntity();
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
