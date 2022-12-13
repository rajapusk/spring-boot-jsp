package com.cardview.demo.service;

import com.cardview.demo.exception.RecordNotFoundException;
import com.cardview.demo.model.BriefcaseAllowanceEntity;
import com.cardview.demo.model.PFLoanEntity;
import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.repository.BriefcaseAllowanceRepository;
import com.cardview.demo.repository.PFAccountRepository;
import com.cardview.demo.repository.PFLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BriefcaseAllowanceService {
    @Autowired
    private EmailServiceImpl emailService;


    @Autowired
    BriefcaseAllowanceRepository repository;

    @Autowired
    PFAccountRepository accountRepository;


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
        if(entity.getid()  == null)
        {
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
                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }
}
