package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.PfLoanUpdateInput;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.repository.PFLoanRepository;
import com.cardview.demo.repository.VpfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VpfService {

    @Value("${spring.project.directory}")
    private String targetPath;

    @Autowired
    VpfRepository repository;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String driverUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    public List<VpfContributionEntity> getAllVpf()
    {
        List<VpfContributionEntity> result = (List<VpfContributionEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<VpfContributionEntity>();
        }
    }

    public VpfContributionEntity getVpfContributionById(Long id) throws RecordNotFoundException
    {
        Optional<VpfContributionEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
    
    public boolean deleteVpfContributionById(Long id) throws RecordNotFoundException
    {
    	Optional<VpfContributionEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
        	repository.deleteById(id);
        	return true;
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
        
	}

    public VpfContributionEntity createOrUpdateVpfContribution(VpfContributionEntity entity)
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
            Optional<VpfContributionEntity> employee = repository.findById(entity.getid());

            if(employee.isPresent())
            {
                VpfContributionEntity newEntity = employee.get();
                newEntity.setapproved(entity.getapproved());
                newEntity.setHRApproved(entity.getHRApproved());
                newEntity.setempcode(entity.getempcode());
                newEntity.setnewNetSalary(entity.getnewNetSalary());
                newEntity.setnewNetSalaryPer(entity.getnewNetSalaryPer());
                newEntity.setsubmitted(entity.getsubmitted());
                newEntity.setPresentVPF(entity.getPresentVPF());
                newEntity.setremarks(entity.getremarks());
                newEntity.setRevisedVPF(entity.getRevisedVPF());
                entity.setUpdatedOn(new java.sql.Date(millis));
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public List<VpfContributionEntity> updateVpfContribution(PfLoanUpdateInput[] entityArray, boolean isManager) {
        List<VpfContributionEntity> result = new ArrayList<VpfContributionEntity>();
        long millis=System.currentTimeMillis();
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<VpfContributionEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                VpfContributionEntity newEntity = employee.get();
                newEntity.setremarks(newEntity.getremarks() + "; " + entity.remarks);

                if(isManager == true)
                    newEntity.setapproved(entity.approved);
                else
                    newEntity.setHRApproved(entity.hrApproved);

                newEntity.setUpdatedOn(new java.sql.Date(millis));
                repository.save(newEntity);

                result.add(newEntity);
            }
        }

        return result;
    }

    public List<VpfContributionEntity> getUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<VpfContributionEntity> emplicList = new ArrayList<VpfContributionEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, empcode, approved, hrapproved, created_on, updated_on   FROM tbl_vehicle_allowance where submitted = 1 and approved = 0");
        while (rs.next()) {

            VpfContributionEntity newEntity = new VpfContributionEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.setid(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setapproved(rs.getByte("approved"));
            newEntity.setempcode(rs.getLong("empcode"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }

    public List<VpfContributionEntity> getHrUnApproveEntity() throws ClassNotFoundException, SQLException {
        List<VpfContributionEntity> emplicList = new ArrayList<VpfContributionEntity>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(driverUrl, userName, password);
        Statement getFromDb = dbConnection.createStatement();

        ResultSet rs = getFromDb
                .executeQuery("select id, empcode, approved, hrapproved, created_on, updated_on   FROM tbl_vpf_contribution where submitted = 1 and approved = 1 and hrapproved = 0 ");
        while (rs.next()) {

            VpfContributionEntity newEntity = new VpfContributionEntity();
            newEntity.setHRApproved(rs.getByte("hrapproved"));
            newEntity.setid(rs.getLong("id"));
            newEntity.setUpdatedOn(rs.getDate("updated_on"));
            newEntity.setCreatedOn(rs.getDate("created_on"));
            newEntity.setapproved(rs.getByte("approved"));
            newEntity.setempcode(rs.getLong("empcode"));

            emplicList.add(newEntity);
        }

        return emplicList;
    }


}
