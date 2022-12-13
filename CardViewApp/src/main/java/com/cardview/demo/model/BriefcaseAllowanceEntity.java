package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_BriefcaseAllowance")
public class BriefcaseAllowanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getid()
    {
        return this.id;
    }

    @Column(name="VENDORNAME")
    private String vendorName;
    public String getVendorName()
    {
        return this.vendorName;
    }
    public void setVendorName(String value)
    {
        this.vendorName = value;
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
    @Column(name="INVOICEDATE")
    private java.sql.Date invoiceDate;
    public java.sql.Date getInvoiceDate()
    {
        return this.invoiceDate;
    }
    public void setInvoiceDate(java.sql.Date value)
    {
        this.invoiceDate = value;
    }
    @Column(name="INVOICENO")
    private String invoiceNo;
    public String getInvoiceNo()
    {
        return this.invoiceNo;
    }
    public void setInvoiceNo(String value)
    {
        this.invoiceNo = value;
    }

    @Column(name="INVOICEAMOUNT")
    private double invoiceAmount;
    public double getInvoiceAmount()
    {
        return this.invoiceAmount;
    }
    public void setInvoiceAmount(double value)
    {
        this.invoiceAmount = value;
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

    @Column(name="document")
    private String document;
    public String getDocument()
    {
        return this.document;
    }
    public void setDocument(String value)
    {
        this.document = value;
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
}
