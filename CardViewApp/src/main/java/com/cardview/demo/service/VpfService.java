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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VpfService {

    @Value("${spring.project.directory}")
    private String targetPath;

    @Autowired
    VpfRepository repository;

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
        if(entity.getid()  == null)
        {
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
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public boolean updateVpfContribution(PfLoanUpdateInput[] entityArray) {
        for(PfLoanUpdateInput entity : entityArray) {
            Optional<VpfContributionEntity> employee = repository.findById(entity.id);
            if (employee.isPresent()) {
                VpfContributionEntity newEntity = employee.get();
                newEntity.setremarks(newEntity.getremarks() + "; " + entity.remarks);
                newEntity.setapproved(entity.approved);
                newEntity = repository.save(newEntity);
            }
        }

        return true;
    }
}
