package com.cardview.demo.web;

import com.cardview.demo.model.*;
import com.cardview.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Value("${manager.mail}") private String _managerEmail;
    @Value("${hr.mail}") private String _hrEmail;
    @Autowired
    PFAccountService paService;
    @Autowired
    BriefcaseAllowanceService baService;

    @Autowired
    VehicleAllowanceService vaService;
    @Autowired
    MedicalAllowanceService maService;
    @Autowired
    NewspaperAllowanceService naService;
    @Autowired
    TravelExpenseService teService;
    @Autowired
    VpfService vpfService;
    @Autowired
    FcaService fcaService;
    @Autowired
    private EmailServiceImpl emailService;

    @RequestMapping(value = "/briefcaseallowance",method = RequestMethod.GET)
    public void BriefcaseAllowanceAutoApprove()
    {
        try {
            List<BriefcaseAllowanceEntity> lstBA = baService.getUnApproveEntity();

            for (BriefcaseAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    baService.updateBriefcaseAllowance(input, true);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Briefcase Allowance Auto Approved";
                    body = "Your Briefcase Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Briefcase Allowance has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = baService.getHrUnApproveEntity();

            for (BriefcaseAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    baService.updateBriefcaseAllowance(input, false);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Briefcase Allowance Auto Approved";
                    body = "Your Briefcase Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Briefcase Allowance has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    @RequestMapping(value = "/fcallowance",method = RequestMethod.GET)
    public void FCAAutoApprove()
    {
        try {
            List<FcaEntity> lstBA = fcaService.getUnApproveEntity();

            for (FcaEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    fcaService.updateFCA(input, true);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "FCA Auto Approved";
                    body = "Your FCA has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied FCA has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = fcaService.getHrUnApproveEntity();

            for (FcaEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    baService.updateBriefcaseAllowance(input, false);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "FCA Auto Approved";
                    body = "Your FCA has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied FCA has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    @RequestMapping(value = "/vehicleallowance",method = RequestMethod.GET)
    public void VehicleAllowanceAutoApprove()
    {
        try {
            List<VehicleAllowanceEntity> lstBA = vaService.getUnApproveEntity();

            for (VehicleAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    vaService.updateVehicleAllowance(input, true);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Vehicle Allowance Auto Approved";
                    body = "Your Vehicle Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Vehicle Allowance has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = vaService.getHrUnApproveEntity();

            for (VehicleAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    vaService.updateVehicleAllowance(input, false);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Vehicle Allowance Auto Approved";
                    body = "Your Vehicle Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied FCA has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    @RequestMapping(value = "/medicalallowance",method = RequestMethod.GET)
    public void MedicalAllowanceAutoApprove()
    {
        try {
            List<MedicalAllowanceEntity> lstBA = maService.getUnApproveEntity();

            for (MedicalAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    maService.updateMedicalAllowance(input, true);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Medical Allowance Auto Approved";
                    body = "Your Medical Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Medical Allowance has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = maService.getHrUnApproveEntity();

            for (MedicalAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    maService.updateMedicalAllowance(input, false);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Medical Allowance Auto Approved";
                    body = "Your Medical Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Medical Allowance has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    @RequestMapping(value = "/newspaperallowance",method = RequestMethod.GET)
    public void NewspaperAllowanceAutoApprove()
    {
        try {
            List<NewspaperAllowanceEntity> lstBA = naService.getUnApproveEntity();

            for (NewspaperAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    naService.updateNewspaperAllowance(input, true);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Newspaper Allowance Auto Approved";
                    body = "Your Newspaper Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Newspaper Allowance has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = naService.getHrUnApproveEntity();

            for (NewspaperAllowanceEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    naService.updateNewspaperAllowance(input, false);

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Newspaper Allowance Auto Approved";
                    body = "Your Newspaper Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Newspaper Allowance has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    @RequestMapping(value = "/travelexpenseseallowance",method = RequestMethod.GET)
    public void TravelExpenseseAllowanceAutoApprove()
    {
        try {
            List<TravelExpenseEntity> lstBA = teService.getL1UnApproveEntity();

            for (TravelExpenseEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    teService.updateTravelExpenseEntity(input, "l1");

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Travel Expensese Allowance Auto Approved";
                    body = "Your Travel Expensese Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Travel Expensese Allowance has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = teService.getL2UnApproveEntity();

            for (TravelExpenseEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    teService.updateTravelExpenseEntity(input, "l2");

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Travel Expensese Allowance Auto Approved";
                    body = "Your Travel Expensese Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Travel Expensese Allowance has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = teService.getHrUnApproveEntity();

            for (TravelExpenseEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    teService.updateTravelExpenseEntity(input, "hr");

                    PFAccountEntity account = paService.getPFAccountById(entity.getEmpCode());
                    String subject = "", body = "";

                    subject = "Travel Expensese Allowance Auto Approved";
                    body = "Your Travel Expensese Allowance has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied Travel Expensese Allowance has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    @RequestMapping(value = "/vpf",method = RequestMethod.GET)
    public void VpfAutoApprove()
    {
        try {
            List<VpfContributionEntity> lstBA = vpfService.getUnApproveEntity();

            for (VpfContributionEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].approved = 1;
                    input[0].remarks = "Auto approved";

                    vpfService.updateVpfContribution(input, true);

                    PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
                    String subject = "", body = "";

                    subject = "VPF Auto Approved";
                    body = "Your VPF has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied VPF has been auto approved.";
                    emailService.sendSimpleMail(_managerEmail, body, subject);
                }
            }

            lstBA = vpfService.getHrUnApproveEntity();

            for (VpfContributionEntity entity:lstBA) {
                if(getNumberOfDays(entity.getUpdatedOn()) > 3)
                {
                    PfLoanUpdateInput input[] = new PfLoanUpdateInput[1];
                    input[0].hrApproved = 1;
                    input[0].remarks = "Auto approved.";

                    vpfService.updateVpfContribution(input, false);

                    PFAccountEntity account = paService.getPFAccountById(entity.getempcode());
                    String subject = "", body = "";

                    subject = "VPF Auto Approved";
                    body = "Your VPF has been auto approved.";

                    emailService.sendSimpleMail(account.getEmail(), body, subject);

                    body = account.getNAME() + " applied VPF has been auto approved.";
                    emailService.sendSimpleMail(_hrEmail, body, subject);
                }
            }
        }
        catch (Exception ex)
        {

        }
    }

    public int getNumberOfDays(Date updated)
    {
        Date today =java.sql.Date.valueOf(LocalDate.now());
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(updated);
        cal2.setTime(today);

        int numberOfDays = 0;
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            cal1.add(Calendar.DATE,1);
        }

        return numberOfDays;
    }
}
