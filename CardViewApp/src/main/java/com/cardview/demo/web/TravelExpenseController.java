package com.cardview.demo.web;

import com.cardview.demo.model.VehicleAllowanceEntity;
import com.cardview.demo.outputModels.BranchOutput;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.TravelExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel")
public class TravelExpenseController {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private TravelExpenseService teService;

    @GetMapping("/branch/{id}")
    public BranchOutput getBranchByCode(@PathVariable("id") int code) {
        try {
            return teService.getBranchByCode(code);
        } catch (Exception ex) {
            return null;
        }
    }
}
