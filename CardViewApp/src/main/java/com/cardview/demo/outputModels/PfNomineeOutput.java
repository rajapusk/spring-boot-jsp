package com.cardview.demo.outputModels;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PfNomineeOutput {
    public Long id;
    public long empCode;
    public byte approved;
    public byte hrApproved;
    public byte submitted;
    public List<NomineeInput> nominees = new ArrayList<>();
    public Date doj;
    public String name;
    public String nameInAadhaar;
    public String gender;
    public Date dor;
    public String panNo;
    public String pf_nps_AcNo;
    public Date pfDoj;
}

