package com.cardview.demo.outputModels;

import java.sql.Date;

public class VehicleAllowanceOutput {

    public long id;
    public Date doj;
    public String glcode;
    public long empcode;
    public String remarks;
    public byte submitted;
    public byte approved;
    public byte hrApproved;
    public String name;
    public String serviceCentreName;
    public String invoiceNo;
    public Date invoiceDate;
    public double invoiceAmount;
    public int entitledAmount;
    public double claimAmount;
    public String managerRemarks;
    public String hrRemarks;
}
