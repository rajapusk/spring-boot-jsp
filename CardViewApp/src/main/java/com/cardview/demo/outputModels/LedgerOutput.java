package com.cardview.demo.outputModels;

import java.util.ArrayList;
import java.util.List;

public class LedgerOutput {
    public long empCode;
    public String name;
    public java.sql.Date doj;
    public String pr_exp;
    public String ob_ee_contri;
    public String ob_er_contri;
    public String roi;
    public String ob_vpf;
    public List<WithdrawalOutput> withdrawals = new ArrayList<>();
    public List<ContributionOutput> contributions = new ArrayList<>();
}
