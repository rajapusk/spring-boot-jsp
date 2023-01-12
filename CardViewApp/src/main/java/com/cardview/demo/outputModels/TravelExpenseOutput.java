package com.cardview.demo.outputModels;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TravelExpenseOutput {

    public long id;
    public String name;
    public byte submitted;
    public byte hrApproved;
    public byte l1Approved;
    public byte l2Approved;
    public byte approved;
    public byte getL2Approved;
    public byte getHRApproved;
    public long empCode;
    public int advanceAmount;
    public String travelPurpose;
    public int destinationBranchCode;
    public int originBranchCode;
    public String permissionMode;
    public long permittedBy;
    public String permittedName;
    public Time permittedTime;
    public Date permittedDate;
    public double totalAmount;
    public String hrRemarks;
    public String l2ManagerRemarks;
    public String l1ManagerRemarks;
    public String grade;
    public ArrayList<TravelExpenseDetailOutput> travelExpenseDetailOutputs = new ArrayList<TravelExpenseDetailOutput>();
}
