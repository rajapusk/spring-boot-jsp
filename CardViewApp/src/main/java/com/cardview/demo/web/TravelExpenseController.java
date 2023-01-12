package com.cardview.demo.web;

import com.cardview.demo.model.*;
import com.cardview.demo.outputModels.*;
import com.cardview.demo.service.EmailServiceImpl;
import com.cardview.demo.service.PFAccountService;
import com.cardview.demo.service.PFLoanService;
import com.cardview.demo.service.TravelExpenseDetailService;
import com.cardview.demo.service.TravelExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/travel")
public class TravelExpenseController {
    @Value("${manager.mail}")
    private String _managerEmail;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    PFAccountService paService;
    @Autowired
    private TravelExpenseService teService;
    @Autowired
    private TravelExpenseDetailService expenseDetailService;

    @Autowired
    PFLoanService pfService;

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
        ArrayList<TravelExpenseDetailEntity> lst = new ArrayList<TravelExpenseDetailEntity>();
        for (TravelExpenseDetailInput data : input.expenses) {
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
            lst.add(dbNomineeEntity);
        }
        dbEntity.setTravelExpenseDetailEntity(lst);
        try {
            PFAccountEntity account = paService.getPFAccountById(input.empCode);
            String body = account.getNAME() + " has applied the Pf Nominee. ";
            emailService.sendSimpleMail(_managerEmail, body, "Pf Nominee Application");

        } catch (Exception e) {
        }

        return entity;
    }

    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
    public EmpDocEntity uploadFile(@RequestParam("emp_doc") MultipartFile file, String pageId, String empCode) {
        try {
            if (pageId != null) {
                if (!file.isEmpty()) {
                    return FileUploadHelper.uploadFile(file, pageId, empCode, "expense_detail", pfService);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/l2")
    public List<TravelExpenseOutput> l2GetAllTravelExpenseDetailEntity() {
        try {
            List<TravelExpenseOutput> result = new ArrayList<TravelExpenseOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<TravelExpenseEntity> lstTravelExpenseEntity = teService.getAllTravelExpense();
            List<TravelExpenseDetailEntity> allTravelExpenseDetail = expenseDetailService.getAllTravelExpenseDetail();

            for (PFAccountEntity account : listAllAccount) {
                for (TravelExpenseEntity ba : lstTravelExpenseEntity) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getL1Approved() == 1 && ba.getL2Approved() == 0) {
                        TravelExpenseOutput output = new TravelExpenseOutput();
                        output.id = ba.getId();
                        output.name = account.getNAME();
                        output.empCode = account.getEMPCODE();
                        output.advanceAmount = ba.getAdvanceAmount();
                        output.submitted = ba.getSubmitted();
                        output.destinationBranchCode = ba.getDestinationBranchCode();
                        output.originBranchCode = ba.getOriginBranchCode();
                        output.permissionMode = ba.getPermissionMode();
                        output.hrRemarks = ba.getHrRemarks();
                        output.l2ManagerRemarks = ba.getL2ManagerRemarks();
                        output.l1ManagerRemarks = ba.getL1ManagerRemarks();
                        output.grade = account.getGRADE();
                        output.permittedBy = ba.getPermittedBy();
                        output.permittedName = ba.getPermittedName();
                        output.permittedDate = ba.getPermittedDate();
                        output.l1Approved = ba.getL1Approved();
                        output.l2Approved = ba.getL2Approved();
                        output.totalAmount = ba.getTotalAmount();
                        output.travelPurpose = ba.getTravelPurpose();

                        for (TravelExpenseDetailEntity entity:allTravelExpenseDetail) {
                            if(entity.getTravelExpenseId() == ba.getId()){
                                TravelExpenseDetailOutput outputDetail = new TravelExpenseDetailOutput();
                                outputDetail.billAvailable = entity.getBillAvailable();
                                outputDetail.cgstAmount = entity.getCGSTAmount();
                                outputDetail.expenseDescription = entity.getExpenseDescription();
                                outputDetail.claimAmount = entity.getClaimAmount();
                                outputDetail.id = entity.getId();
                                outputDetail.destination = entity.getDestination();
                                outputDetail.document = entity.getDocument();
                                outputDetail.distance = entity.getDistance();
                                outputDetail.entitledAmount = entity.getEntitledAmount();
                                outputDetail.igstAmount = entity.getIGSTAmount();
                                outputDetail.netAmount = entity.getNetAmount();
                                outputDetail.noOfDays = entity.getNoOfDays();
                                outputDetail.origin = entity.getOrigin();
                                outputDetail.pnrNo = entity.getPNRNo();
                                outputDetail.remarks = entity.getRemarks();
                                outputDetail.sgstAmount = entity.getSGSTAmount();
                                outputDetail.totalAmount = entity.getTotalAmount();
                                outputDetail.travelEndDate = entity.getTravelEndDate();
                                outputDetail.travelStartDate = entity.getTravelStartDate();
                                output.travelExpenseDetailOutputs.add(outputDetail);
                            }
                        }
                        result.add(output);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/l1")
    public List<TravelExpenseOutput> l1GetAllTravelExpenseDetailEntity() {
        try {
            List<TravelExpenseOutput> result = new ArrayList<TravelExpenseOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<TravelExpenseEntity> lstTravelExpenseEntity = teService.getAllTravelExpense();
            List<TravelExpenseDetailEntity> allTravelExpenseDetail = expenseDetailService.getAllTravelExpenseDetail();

            for (PFAccountEntity account : listAllAccount) {
                for (TravelExpenseEntity ba : lstTravelExpenseEntity) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getL1Approved() == 0) {
                        TravelExpenseOutput output = new TravelExpenseOutput();
                        output.id = ba.getId();
                        output.name = account.getNAME();
                        output.empCode = account.getEMPCODE();
                        output.advanceAmount = ba.getAdvanceAmount();
                        output.submitted = ba.getSubmitted();
                        output.destinationBranchCode = ba.getDestinationBranchCode();
                        output.originBranchCode = ba.getOriginBranchCode();
                        output.permissionMode = ba.getPermissionMode();
                        output.hrRemarks = ba.getHrRemarks();
                        output.l2ManagerRemarks = ba.getL2ManagerRemarks();
                        output.l1ManagerRemarks = ba.getL1ManagerRemarks();
                        output.grade = account.getGRADE();
                        output.permittedBy = ba.getPermittedBy();
                        output.permittedName = ba.getPermittedName();
                        output.permittedDate = ba.getPermittedDate();
                        output.l1Approved = ba.getL1Approved();
                        output.l2Approved = ba.getL2Approved();
                        output.totalAmount = ba.getTotalAmount();
                        output.travelPurpose = ba.getTravelPurpose();

                        for (TravelExpenseDetailEntity entity:allTravelExpenseDetail) {
                            if(entity.getTravelExpenseId() == ba.getId()){
                                TravelExpenseDetailOutput outputDetail = new TravelExpenseDetailOutput();
                                outputDetail.billAvailable = entity.getBillAvailable();
                                outputDetail.cgstAmount = entity.getCGSTAmount();
                                outputDetail.expenseDescription = entity.getExpenseDescription();
                                outputDetail.claimAmount = entity.getClaimAmount();
                                outputDetail.id = entity.getId();
                                outputDetail.destination = entity.getDestination();
                                outputDetail.document = entity.getDocument();
                                outputDetail.distance = entity.getDistance();
                                outputDetail.entitledAmount = entity.getEntitledAmount();
                                outputDetail.igstAmount = entity.getIGSTAmount();
                                outputDetail.netAmount = entity.getNetAmount();
                                outputDetail.noOfDays = entity.getNoOfDays();
                                outputDetail.origin = entity.getOrigin();
                                outputDetail.pnrNo = entity.getPNRNo();
                                outputDetail.remarks = entity.getRemarks();
                                outputDetail.sgstAmount = entity.getSGSTAmount();
                                outputDetail.totalAmount = entity.getTotalAmount();
                                outputDetail.travelEndDate = entity.getTravelEndDate();
                                outputDetail.travelStartDate = entity.getTravelStartDate();
                                output.travelExpenseDetailOutputs.add(outputDetail);
                            }
                        }
                        result.add(output);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/hr")
    public List<TravelExpenseOutput> hrGetAllTravelExpenseDetailEntity() {
        try {
            List<TravelExpenseOutput> result = new ArrayList<TravelExpenseOutput>();
            List<PFAccountEntity> listAllAccount = paService.getAllPFAccount();
            List<TravelExpenseEntity> lstTravelExpenseEntity = teService.getAllTravelExpense();
            List<TravelExpenseDetailEntity> allTravelExpenseDetail = expenseDetailService.getAllTravelExpenseDetail();

            for (PFAccountEntity account : listAllAccount) {
                for (TravelExpenseEntity ba : lstTravelExpenseEntity) {
                    if (account.getEMPCODE() == ba.getEmpCode() && ba.getL1Approved() == 1 && ba.getL2Approved() == 1 && ba.getHRApproved() == 0) {
                        TravelExpenseOutput output = new TravelExpenseOutput();
                        output.id = ba.getId();
                        output.name = account.getNAME();
                        output.empCode = account.getEMPCODE();
                        output.advanceAmount = ba.getAdvanceAmount();
                        output.submitted = ba.getSubmitted();
                        output.destinationBranchCode = ba.getDestinationBranchCode();
                        output.originBranchCode = ba.getOriginBranchCode();
                        output.permissionMode = ba.getPermissionMode();
                        output.hrRemarks = ba.getHrRemarks();
                        output.l2ManagerRemarks = ba.getL2ManagerRemarks();
                        output.l1ManagerRemarks = ba.getL1ManagerRemarks();
                        output.grade = account.getGRADE();
                        output.permittedBy = ba.getPermittedBy();
                        output.permittedName = ba.getPermittedName();
                        output.permittedDate = ba.getPermittedDate();
                        output.l1Approved = ba.getL1Approved();
                        output.l2Approved = ba.getL2Approved();
                        output.totalAmount = ba.getTotalAmount();
                        output.travelPurpose = ba.getTravelPurpose();

                        for (TravelExpenseDetailEntity entity:allTravelExpenseDetail) {
                            if(entity.getTravelExpenseId() == ba.getId()){
                                TravelExpenseDetailOutput outputDetail = new TravelExpenseDetailOutput();
                                outputDetail.billAvailable = entity.getBillAvailable();
                                outputDetail.cgstAmount = entity.getCGSTAmount();
                                outputDetail.expenseDescription = entity.getExpenseDescription();
                                outputDetail.claimAmount = entity.getClaimAmount();
                                outputDetail.id = entity.getId();
                                outputDetail.destination = entity.getDestination();
                                outputDetail.document = entity.getDocument();
                                outputDetail.distance = entity.getDistance();
                                outputDetail.entitledAmount = entity.getEntitledAmount();
                                outputDetail.igstAmount = entity.getIGSTAmount();
                                outputDetail.netAmount = entity.getNetAmount();
                                outputDetail.noOfDays = entity.getNoOfDays();
                                outputDetail.origin = entity.getOrigin();
                                outputDetail.pnrNo = entity.getPNRNo();
                                outputDetail.remarks = entity.getRemarks();
                                outputDetail.sgstAmount = entity.getSGSTAmount();
                                outputDetail.totalAmount = entity.getTotalAmount();
                                outputDetail.travelEndDate = entity.getTravelEndDate();
                                outputDetail.travelStartDate = entity.getTravelStartDate();
                                output.travelExpenseDetailOutputs.add(outputDetail);
                            }
                        }
                        result.add(output);
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
}

