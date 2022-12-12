package com.cardview.demo.outputModels;

import java.util.ArrayList;
import java.util.List;

public class LedgerOutput {
    public long empCode;
    public String name;
    public java.sql.Date doj;
    public String pr_exp;
    public double ob_ee_contri;
    public double ob_er_contri;
    public String roi;
    public double ob_vpf;
    public List<WithdrawalOutput> withdrawals = new ArrayList<>();
    public List<ContributionOutput> dbcontributions = new ArrayList<>();
    public List<ContributionOutput> lstContributions = new ArrayList<>();
}
