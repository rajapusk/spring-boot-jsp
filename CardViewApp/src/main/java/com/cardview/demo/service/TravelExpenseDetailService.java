package com.cardview.demo.service;

import com.cardview.demo.repository.PFNomineeRepository;
import com.cardview.demo.repository.TravelExpenseDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelExpenseDetailService {

    @Autowired
    TravelExpenseDetailRepository repository;


}
