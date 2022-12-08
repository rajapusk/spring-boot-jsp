package com.cardview.demo.service;

import com.cardview.demo.model.VpfContributionEntity;
import com.cardview.demo.outputModels.ContributionOutput;
import com.cardview.demo.outputModels.LedgerOutput;
import com.cardview.demo.outputModels.NomineeInput;
import com.cardview.demo.outputModels.WithdrawalOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
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
                input.ob_ee_contri = emplics.getString("ob_ee_contri");
                input.ob_vpf = emplics.getString("ob_vpf");
                input.ob_er_contri = emplics.getString("ob_er_contri");
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
                    .executeQuery(" SELECT wage_month,empcode,date_of_credit,basic_total,ee_contri,er_contri,vpf,ee_interest,ee_total,er_interest,er_total FROM vetan10.tbl_pf_ledger;");
            while (emplics.next()) {
                ContributionOutput input = new ContributionOutput();

                input.empCode = emplics.getLong("empcode");
                input.wage_month = emplics.getString("wage_month");
                input.date_of_credit = emplics.getDate("date_of_credit");
                input.basic_total = emplics.getDouble("basic_total");
                input.ee_contri = emplics.getDouble("ee_contri");
                input.er_contri = emplics.getDouble("er_contri");
                input.vpf = emplics.getDouble("vpf");
                input.ee_interest = emplics.getDouble("ee_interest");
                input.ee_total = emplics.getDouble("ee_total");
                input.er_interest = emplics.getDouble("er_interest");
                input.er_total = emplics.getDouble("er_total");

                contributionOutputs.add(input);

            }

            for (LedgerOutput entity : emplicList) {
                for (ContributionOutput  contribution: contributionOutputs) {
                    if(entity.empCode == contribution.empCode){
                        entity.contributions.add(contribution);
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

        return emplicList;
    }
}
