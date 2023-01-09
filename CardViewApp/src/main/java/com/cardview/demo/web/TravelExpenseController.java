package com.cardview.demo.web;

import com.cardview.demo.model.*;
import com.cardview.demo.outputModels.*;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.TravelExpenseDetailService;
import com.cardview.demo.service.TravelExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel")
public class TravelExpenseController {
    @Value("${manager.mail}") private String _managerEmail;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    PFAccountService paService;
    @Autowired
    private TravelExpenseService teService;
    @Autowired
    private TravelExpenseDetailService expenseDetailService;

    @GetMapping("/branch/{id}")
    public BranchOutput getBranchByCode(@PathVariable("id") int code) {
        try {
            return teService.getBranchByCode(code);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/lodging")
    public List<LodgingEntitlementAmountOutput> getLodgingEntitlementAmount() {
        try {
            return teService.getLodgingEntitlementAmount();
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/halting")
    public List<HaltingEntitlementAmountOutput> getHaltingEntitlementAmount() {
        try {
            return teService.getHaltingEntitlementAmount();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public TravelExpenseEntity createOrUpdateVpfContribution(@RequestBody TravelExpenseInput input) {

        TravelExpenseEntity entity = new TravelExpenseEntity();
        entity.setSubmitted(input.submitted);
        entity.setL1Approved(input.approved);
        entity.setL2Approved(input.getL2Approved);
        entity.setHRApproved(input.getHRApproved);
        entity.setEmpCode(input.empCode);
        entity.setAdvanceAmount(input.advanceAmount);
        entity.setTravelPurpose(input.travelPurpose);
        entity.setDestinationBranchCode(input.destinationBranchCode);
        entity.setOriginBranchCode(input.originBranchCode);
        entity.setPermissionMode(input.permissionMode);
        entity.setPermittedBy(input.permittedBy);
        entity.setPermittedName(input.permittedName);
        entity.setPermittedTime(input.permittedTime);
        entity.setPermittedDate(input.permittedDate);
        entity.setTotalAmount(input.totalAmount);

        TravelExpenseEntity dbEntity = teService.createOrUpdateTravelExpenseEntity(entity);

        for (TravelExpenseDetailInput data:input.expenses) {
            TravelExpenseDetailEntity expense = new TravelExpenseDetailEntity();
            expense.setTravelExpenseId(dbEntity.getId());
            expense.setBillAvailable(data.billAvailable);
            expense.setCGSTAmount(data.cgstAmount);
            expense.setExpenseDescription(data.expenseDescription);
            expense.setDestination(data.destination);
            expense.setDistance(data.distance);
            expense.setDocument(data.document);
            expense.setClaimAmount(data.claimAmount);
            expense.setEntitledAmount(data.entitledAmount);
            expense.setIGSTAmount(data.igstAmount);
            expense.setNetAmount(data.netAmount);
            expense.setNoOfDays(data.noOfDays);
            expense.setOrigin(data.origin);
            expense.setPNRNo(data.pnrNo);
            expense.setRemarks(data.remarks);
            expense.setSGSTAmount(data.sgstAmount);
            expense.setTravelEndDate(data.travelEndDate);
            expense.setTotalAmount(data.totalAmount);
            expense.setTravelStartDate(data.travelStartDate);
            TravelExpenseDetailEntity dbNomineeEntity = expenseDetailService.createOrUpdateTravelExpenseDetailEntity(expense);
        }

        try {
            PFAccountEntity account = paService.getPFAccountById(input.empCode);
            String body = account.getNAME() + " has applied the Pf Nominee. ";
            emailService.sendSimpleMail(_managerEmail, body, "Pf Nominee Application");

        } catch (Exception e) {
        }

        return  entity;
    }
}

