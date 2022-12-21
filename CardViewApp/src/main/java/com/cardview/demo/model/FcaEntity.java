package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_FCA")
public class FcaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getid()
    {
        return this.id;
    }

    @Column(name="QuarterType")
    private String quarterType;
    public String getQuarterType()
    {
        return this.quarterType;
    }
    public void setQuarterType(String value)
    {
        this.quarterType = value;
    }

    @Column(name="EMPCODE")
    private long empCode;
    public long getEmpCode()
    {
        return this.empCode;
    }
    public void setEmpCode(long value)
    {
        this.empCode = value;
    }
    @Column(name="MONTHS")
    private String months;
    public String getMonths()
    {
        return this.months;
    }
    public void setMonths(String value)
    {
        this.months = value;
    }

    @Column(name="ENTITLEDAMOUNT")
    private int entitledAmount;
    public int getEntitledAmount()
    {
        return this.entitledAmount;
    }
    public void setEntitledAmount(int value)
    {
        this.entitledAmount = value;
    }
    @Column(name="CLAIMAMOUNT")
    private double claimAmount;
    public double getClaimAmount()
    {
        return this.claimAmount;
    }
    public void setClaimAmount(double value)
    {
        this.claimAmount = value;
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
    @Column(name="hrRemarks")
    private String hrRemarks;
    public String getHrRemarks()
    {
        return this.hrRemarks;
    }
    public void setHrRemarkss(String value)
    {
        this.hrRemarks = value;
    }
    @Column(name="MANAGERREMARKS")
    private String managerRemarks;
    public String getManagerRemarks()
    {
        return this.managerRemarks;
    }
    public void setManagerRemarks(String value)
    {
        this.managerRemarks = value;
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



    @Column(name="CREATED_ON")
    private java.sql.Date createdOn;
    public java.sql.Date getCreatedOn()
    {
        return this.createdOn;
    }
    public void setCreatedOn(java.sql.Date value)
    {
        this.createdOn = value;
    }

    @Column(name="UPDATED_ON")
    private java.sql.Date updatedOn;
    public java.sql.Date getUpdatedOn()
    {
        return this.updatedOn;
    }
    public void setUpdatedOn(java.sql.Date value)
    {
        this.updatedOn = value;
    }
}
