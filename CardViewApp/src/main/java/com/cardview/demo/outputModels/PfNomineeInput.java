package com.cardview.demo.outputModels;


import java.util.ArrayList;
import java.util.List;

public class PfNomineeInput {
    public Long id;
    public long empCode;
    public byte approved;
    public byte hrApproved;
    public byte submitted;
    public List<NomineeInput> nominees = new ArrayList<>();
}

