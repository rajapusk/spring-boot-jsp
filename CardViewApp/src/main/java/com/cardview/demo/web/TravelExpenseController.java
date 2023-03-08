package com.cardview.demo.web;

import com.cardview.demo.exception.RecordNotFoundException;
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
    @Value("${manager.mail}") private String _managerEmail;
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

        if(input.totalAmount >=50000 ){
            entity.setL1Approved((byte) 1);
            entity.setL2Approved((byte)1);
        }

        TravelExpenseEntity dbEntity = teService.createOrUpdateTravelExpenseEntity(entity);
        ArrayList<TravelExpenseDetailEntity> lst = new ArrayList<TravelExpenseDetailEntity>();
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
            lst.add(dbNomineeEntity);
        }
        dbEntity.setTravelExpenseDetailEntity(lst);
        try {
            PFAccountEntity account = paService.getPFAccountById(input.empCode);
            String body = account.getNAME() + " has applied the Pf Nominee. ";
            emailService.sendSimpleMail(_managerEmail, body, "Pf Nominee Application");

        } catch (Exception e) {
        }

        return  dbEntity;
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
            List<TravelExpenseEntity> lstTravelExpenseEntity = teService.getAllTravelExpense();
            List<TravelExpenseDetailEntity> allTravelExpenseDetail = expenseDetailService.getAllTravelExpenseDetail();

            String empCodes = "";
            for (TravelExpenseEntity ba : lstTravelExpenseEntity) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }

            if(lstTravelExpenseEntity.size() > 0) {
                StringBuffer sb = new StringBuffer(empCodes);
                sb.deleteCharAt(sb.length() - 1);
                List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

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

                            for (TravelExpenseDetailEntity entity : allTravelExpenseDetail) {
                                if (entity.getTravelExpenseId() == ba.getId()) {
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
            List<TravelExpenseEntity> lstTravelExpenseEntity = teService.getAllTravelExpense();
            List<TravelExpenseDetailEntity> allTravelExpenseDetail = expenseDetailService.getAllTravelExpenseDetail();



            String empCodes = "";
            for (TravelExpenseEntity ba : lstTravelExpenseEntity) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }

            if(lstTravelExpenseEntity.size() > 0) {
                StringBuffer sb = new StringBuffer(empCodes);
                sb.deleteCharAt(sb.length() - 1);
                List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

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

                            for (TravelExpenseDetailEntity entity : allTravelExpenseDetail) {
                                if (entity.getTravelExpenseId() == ba.getId()) {
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
            List<TravelExpenseEntity> lstTravelExpenseEntity = teService.getAllTravelExpense();
            List<TravelExpenseDetailEntity> allTravelExpenseDetail = expenseDetailService.getAllTravelExpenseDetail();

            String empCodes = "";
            for (TravelExpenseEntity ba : lstTravelExpenseEntity) {
                empCodes = empCodes +  ba.getEmpCode() + ",";
            }

            if(lstTravelExpenseEntity.size() > 0) {
                StringBuffer sb = new StringBuffer(empCodes);
                sb.deleteCharAt(sb.length() - 1);
                List<PFAccountEntity> listAllAccount = paService.getAllPFAccountViewByIds(sb.toString());

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

                            for (TravelExpenseDetailEntity entity : allTravelExpenseDetail) {
                                if (entity.getTravelExpenseId() == ba.getId()) {
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
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(path = "/l1/update", method = RequestMethod.PUT)
    public boolean UpdateL1(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<TravelExpenseEntity> lstVPF = teService.updateTravelExpenseEntity(loan, "l1");

        for (TravelExpenseEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getL1Approved() == 1) {
                subject = "Travel Expense Approved";
                body = "Your Travel Expense has been approved by L1 manager.";
            } else if (entity.getL1Approved() == 2) {
                subject = "Travel Expense Rejected";
                body = "Your Travel Expense has been rejected by L1 manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/l2/update", method = RequestMethod.PUT)
    public boolean UpdateL2(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<TravelExpenseEntity> lstVPF = teService.updateTravelExpenseEntity(loan, "l2");

        for (TravelExpenseEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";


            if (entity.getL2Approved() == 1) {
                subject = "Travel Expense Approved";
                body = "Your Travel Expense has been approved by L2 manager.";
            } else if (entity.getL2Approved() == 2) {
                subject = "Travel Expense Rejected";
                body = "Your Travel Expense has been rejected by L2 manager.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }

    @RequestMapping(path = "/hr/update", method = RequestMethod.PUT)
    public boolean UpdateHrTravelExpenseseAllowance(@RequestBody PfLoanUpdateInput[] loan) throws RecordNotFoundException {
        List<TravelExpenseEntity> lstVPF = teService.updateTravelExpenseEntity(loan, "hr");

        for (TravelExpenseEntity entity : lstVPF) {
            PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
            String subject = "", body = "";

            if (entity.getHRApproved() == 1) {
                subject = "Travel Expense Approved";
                body = "Your Travel Expense has been approved by HR.";
            } else if (entity.getHRApproved() == 2) {
                subject = "Travel Expense Rejected";
                body = "Your Travel Expense has been rejected by HR.";
            }

            emailService.sendSimpleMail(account.getEmail(), body, subject);
        }
        return true;
    }


}

