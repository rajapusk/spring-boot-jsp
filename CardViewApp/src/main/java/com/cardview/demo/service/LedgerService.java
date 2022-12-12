package com.cardview.demo.service;

import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.ContributionOutput;
import com.cardview.demo.outputModels.LedgerOutput;
import com.cardview.demo.outputModels.NomineeInput;
import com.cardview.demo.outputModels.WithdrawalOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class LedgerService {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String driverUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    public List<LedgerOutput> getLedger() throws ClassNotFoundException {
        List<LedgerOutput> emplicList = new ArrayList<LedgerOutput>();
        List<WithdrawalOutput> lstWithdrawals = new ArrayList<WithdrawalOutput>();
        List<ContributionOutput> contributionOutputs = new ArrayList<ContributionOutput>();
        // System.out.println("get employee ");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(driverUrl, userName,password);
            Statement getFromDb = dbConnection.createStatement();
            ResultSet emplics = getFromDb
                    .executeQuery("    SELECT empcode,name,doj,prexp,ob_ee_contri,ob_vpf, ob_er_contri, roi FROM vetan10.tbl_emp_details;");
            while (emplics.next()) {
                LedgerOutput input = new LedgerOutput();

                input.empCode = emplics.getLong("empcode");
                input.name = emplics.getString("name");
                input.doj = emplics.getDate("doj");
                input.pr_exp = emplics.getString("prexp");
                input.ob_ee_contri = emplics.getDouble("ob_ee_contri");
                input.ob_vpf = emplics.getDouble("ob_vpf");
                input.ob_er_contri = emplics.getDouble("ob_er_contri");
                input.roi = emplics.getString("roi");

                emplicList.add(input);

            }

            getFromDb = dbConnection.createStatement();
            emplics = getFromDb
                    .executeQuery(" SELECT empcode,withdrawn_date, withdrawn_amount, withdrawal_purpose FROM vetan10.tbl_pf_withdraw_details;");
            while (emplics.next()) {
                WithdrawalOutput input = new WithdrawalOutput();

                input.empCode = emplics.getLong("empcode");
                input.withdrawalPurpose = emplics.getString("withdrawal_purpose");
                input.withdrawn_date = emplics.getDate("withdrawn_date");
                input.withdrawnAmount = emplics.getDouble("withdrawn_amount");

                lstWithdrawals.add(input);

            }

            getFromDb = dbConnection.createStatement();
            emplics = getFromDb
                    .executeQuery(" SELECT wage_month,empcode,date_of_credit,basic_total,pf_ee,nps_ee,vpf,pf_er,nps_er FROM vetan10.tbl_contribution;");
            while (emplics.next()) {
                ContributionOutput input = new ContributionOutput();

                input.empCode = emplics.getLong("empcode");
                input.wage_month = emplics.getString("wage_month");
                input.date_of_credit = emplics.getDate("date_of_credit");
                input.basic_total = emplics.getDouble("basic_total");
                input.pf_ee = emplics.getDouble("pf_ee");
                input.nps_ee = emplics.getDouble("nps_ee");
                input.vpf = emplics.getDouble("vpf");
                input.pf_er = emplics.getDouble("pf_er");
                input.nps_er = emplics.getDouble("nps_er");

                contributionOutputs.add(input);
            }

            for (LedgerOutput entity : emplicList) {
                for (ContributionOutput  contribution: contributionOutputs) {
                    if(entity.empCode == contribution.empCode){
                        entity.dbcontributions.add(contribution);
                    }
                }

                for (WithdrawalOutput  withdrow: lstWithdrawals) {
                    if(entity.empCode == withdrow.empCode){
                        entity.withdrawals.add(withdrow);
                    }
                }
            }

            for (LedgerOutput entity : emplicList) {
                for (ContributionOutput  contribution: entity.dbcontributions) {
                    if(entity.empCode == contribution.empCode){
                        entity.dbcontributions.add(contribution);
                    }
                }

                for (WithdrawalOutput  withdrow: lstWithdrawals) {
                    if(entity.empCode == withdrow.empCode){
                        entity.withdrawals.add(withdrow);
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DoLogic(emplicList);
    }

    List<LedgerOutput>  DoLogic(List<LedgerOutput> LedgerOutput)
    {
        for (LedgerOutput ledger : LedgerOutput)
        {
            int idx = 0;
            double er_Cont = 0;
            double ee_Cont = 0;
            double ee_totalIntrest = 0;
            double er_totalIntrest = 0;
            double ee_Total = 0;
            double er_Total = 0;
            double totalVpf = 0;
            double sum_ee_Cont = 0;
            double sum_er_Cont = 0;

            List<ContributionOutput> dbcontributions = ledger.dbcontributions;
            Collections.sort(dbcontributions, new Comparator<ContributionOutput>() {
                public int compare(ContributionOutput a, ContributionOutput b) {
                    return a.date_of_credit.compareTo(b.date_of_credit);
                }
            });



            for (ContributionOutput cont : dbcontributions)
            {
                YearMonth yearMonth = YearMonth.of(cont.date_of_credit.getYear(), cont.date_of_credit.toLocalDate().getMonthValue());
                int daysInMonth = yearMonth.lengthOfMonth();

                cont.ee_interest = (((ledger.ob_vpf + ledger.ob_ee_contri + ee_Cont) * 8.5)/100) / 365 * daysInMonth;
                cont.ee_contri =  cont.nps_ee + cont.pf_ee;
                ee_Cont = ee_Cont + cont.ee_contri + cont.vpf;
                totalVpf += cont.vpf;
                sum_ee_Cont += cont.ee_contri;
                ee_totalIntrest += cont.ee_interest;
                ee_Total = ee_Total + cont.vpf + cont.ee_contri + cont.ee_interest;
                cont.ee_total = ee_Cont + cont.ee_interest;


                cont.er_interest = (((ledger.ob_er_contri + er_Cont) * 8.5)/100) / 365 * daysInMonth;
                cont.er_contri = cont.nps_er + cont.pf_er;
                er_Cont = er_Cont + cont.er_contri;
                cont.er_total = cont.er_contri + cont.er_interest;
                sum_er_Cont += cont.er_contri;
                er_Total = er_Total + cont.er_contri + cont.er_interest;
                er_totalIntrest += cont.er_interest;

                ContributionOutput newCont = new ContributionOutput();

                newCont.idx = ++idx;
                newCont.empCode = cont.empCode;
                newCont.ee_total = cont.ee_total;
                newCont.vpf = cont.vpf;
                newCont.basic_total = cont.basic_total;
                newCont.date_of_credit = cont.date_of_credit;
                newCont.ee_contri = cont.ee_contri;
                newCont.ee_interest = cont.ee_interest;
                newCont.er_contri = cont.er_contri;
                newCont.er_interest = cont.er_interest;
                newCont.er_total = cont.er_total;
                newCont.nps_ee = cont.nps_ee;
                newCont.nps_er = cont.nps_er;
                newCont.pf_ee = cont.pf_ee;
                newCont.pf_er = cont.pf_er;
                newCont.wage_month = cont.wage_month;


                ledger.lstContributions.add(newCont);

                if (cont.date_of_credit.toLocalDate().getMonthValue() == 10)
                {
                    ContributionOutput newContSum = new ContributionOutput();
                    LocalDate localDate = LocalDate.of(cont.date_of_credit.getYear(), 10, 15);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                        newContSum.idx = ++idx;
                        newContSum.empCode = cont.empCode;
                        newContSum.ee_total = ee_Total;
                        newContSum.vpf = totalVpf;
                        newContSum.basic_total = 0;
                        newContSum.date_of_credit = sqlDate;
                        newContSum.ee_contri = sum_ee_Cont;
                        newContSum.ee_interest = ee_totalIntrest;
                        newContSum.er_contri = sum_er_Cont;
                        newContSum.er_interest = er_totalIntrest;
                        newContSum.er_total = er_Total;
                        newContSum.wage_month = "for Apr to Sep";


                    ledger.lstContributions.add(newContSum);

                    ee_Cont += ee_totalIntrest;
                    ee_totalIntrest = 0;
                    totalVpf = 0;
                    ee_Total = 0;
                    sum_ee_Cont = 0;
                    sum_er_Cont = 0;
                    er_Cont += er_totalIntrest;
                    er_totalIntrest = 0;
                    er_Total = 0;
                }

                if (cont.date_of_credit.toLocalDate().getMonthValue() == 4)
                {
                    ContributionOutput newContSum = new ContributionOutput();
                    LocalDate localDate = LocalDate.of(cont.date_of_credit.getYear(), 4, 15);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                    newContSum.idx = ++idx;
                    newContSum.empCode = cont.empCode;
                    newContSum.ee_total = ee_Total;
                    newContSum.vpf = totalVpf;
                    newContSum.basic_total = 0;
                    newContSum.date_of_credit = sqlDate;
                    newContSum.ee_contri = sum_ee_Cont;
                    newContSum.ee_interest = ee_totalIntrest;
                    newContSum.er_contri = sum_er_Cont;
                    newContSum.er_interest = er_totalIntrest;
                    newContSum.er_total = er_Total;
                    newContSum.wage_month = "for Oct to Mar";


                    ledger.lstContributions.add(newContSum);
                }
            }

            List<ContributionOutput> contributions = ledger.lstContributions;
            Collections.sort(contributions, new Comparator<ContributionOutput>() {
                public int compare(ContributionOutput a, ContributionOutput b) {
                    return Integer.compare(a.idx, b.idx);
                }
            });
            ledger.lstContributions = new ArrayList<ContributionOutput>(contributions);
        }

        return LedgerOutput;
    }
}
