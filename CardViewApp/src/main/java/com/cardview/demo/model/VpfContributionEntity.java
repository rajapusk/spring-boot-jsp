package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_VPF_CONTRIBUTION")
public class VpfContributionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getid()
    {
        return this.id;
    }
    public void setid(Long value)
    {
        this.id = value;
    }

    @Column(name="EMPCODE")
    private long _empcode;
    public long getempcode()
    {
        return this._empcode;
    }
    public void setempcode(long value)
    {
        this._empcode = value;
    }


    @Column(name="NEWNETSALARY")
    private int newNetSalary;
    public int getnewNetSalary()
    {
        return this.newNetSalary;
    }
    public void setnewNetSalary(int value)
    {
        this.newNetSalary = value;
    }

    @Column(name="NEWNETSALARYPER")
    private double newNetSalaryPer;
    public double getnewNetSalaryPer()
    {
        return this.newNetSalaryPer;
    }
    public void setnewNetSalaryPer(double value)
    {
        this.newNetSalaryPer = value;
    }


    @Column(name="REVISEDVPF")
    private double revisedVPF;
    public double getRevisedVPF()
    {
        return this.revisedVPF;
    }
    public void setRevisedVPF(double value)
    {
        this.revisedVPF = value;
    }


    @Column(name="PRESENTVPF")
    private double presentVPF;
    public double getPresentVPF()
    {
        return this.presentVPF;
    }
    public void setPresentVPF(double value)
    {
        this.presentVPF = value;
    }

    @Column(name="SUBMITTED")
    private byte submitted;
    public byte getsubmitted()
    {
        return this.submitted;
    }
    public void setsubmitted(byte value)
    {
        this.submitted = value;
    }

    @Column(name="APPROVED")
    private byte approved;
    public byte getapproved()
    {
        return this.approved;
    }
    public void setapproved(byte value)
    {
        this.approved = value;
    }

    @Column(name="HRAPPROVED")
    private byte hrApproved;
    public byte getHRApproved()
    {
        return this.hrApproved;
    }
    public void setHRApproved(byte value)
    {
        this.hrApproved = value;
    }

    @Column(name="REMARKS")
    private String remarks;
    public String getremarks()
    {
        return this.remarks;
    }
    public void setremarks(String value)
    {
        this.remarks = value;
    }
}
