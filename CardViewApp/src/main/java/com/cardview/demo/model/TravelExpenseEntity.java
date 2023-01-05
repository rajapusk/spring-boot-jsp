package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_TravelExpense")
public class TravelExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EmpCode")
    private long empCode;

    public long getEmpCode() {
        return this.empCode;
    }

    public void setEmpCode(long value) {
        this.empCode = value;
    }

    @Column(name = "OriginBranchCode")
    private int originBranchCode;

    public int getOriginBranchCode() {
        return this.originBranchCode;
    }

    public void setOriginBranchCode(int value) {
        this.originBranchCode = value;
    }

    @Column(name = "DestinationBranchCode")
    private int destinationBranchCode;

    public int getDestinationBranchCode() {
        return this.destinationBranchCode;
    }

    public void setDestinationBranchCode(int value) {
        this.destinationBranchCode = value;
    }

    @Column(name = "TravelPurpose")
    private String travelPurpose;

    public String getTravelPurpose() {
        return this.travelPurpose;
    }

    public void setTravelPurpose(String value) {
        this.travelPurpose = value;
    }

    @Column(name = "PermittedBy")
    private long permittedBy;

    public long getPermittedBy() {
        return this.permittedBy;
    }

    public void setPermittedBy(long value) {
        this.permittedBy = value;
    }


    @Column(name = "PermittedName")
    private String permittedName;

    public String getPermittedName() {
        return this.permittedName;
    }

    public void setPermittedName(String value) {
        this.permittedName = value;
    }


    @Column(name = "PermittedDate")
    private java.sql.Date permittedDate;

    public java.sql.Date getPermittedDate() {
        return this.permittedDate;
    }

    public void setPermittedDate(java.sql.Date value) {
        this.permittedDate = value;
    }

    @Column(name = "PermittedTime")
    private java.sql.Time permittedTime;

    public java.sql.Time getPermittedTime() {
        return this.permittedTime;
    }

    public void setPermittedTime(java.sql.Time value) {
        this.permittedTime = value;
    }


    @Column(name = "PermissionMode")
    private String permissionMode;

    public String getPermissionMode() {
        return this.permissionMode;
    }

    public void setPermissionMode(String value) {
        this.permissionMode = value;
    }


    @Column(name = "AdvanceAmount")
    private int advanceAmount;

    public int getAdvanceAmount() {
        return this.advanceAmount;
    }

    public void setAdvanceAmount(int value) {
        this.advanceAmount = value;
    }

    @Column(name = "TotalAmount")
    private double totalAmount;

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double value) {
        this.totalAmount = value;
    }

    @Column(name="hrRemarks")
    private String hrRemarks;
    public String getHrRemarks()
    {
        return this.hrRemarks;
    }
    public void setHrRemarks(String value)
    {
        this.hrRemarks = value;
    }
    @Column(name="L1ManagerRemarks")
    private String l1ManagerRemarks;
    public String getL1ManagerRemarks()
    {
        return this.l1ManagerRemarks;
    }
    public void setL1ManagerRemarks(String value)
    {
        this.l1ManagerRemarks = value;
    }

    @Column(name="L2ManagerRemarks")
    private String l2ManagerRemarks;
    public String getL2ManagerRemarks()
    {
        return this.l2ManagerRemarks;
    }
    public void setL2ManagerRemarks(String value)
    {
        this.l2ManagerRemarks = value;
    }


    @Column(name="SUBMITTED")
    private byte submitted;
    public byte getSubmitted()
    {
        return this.submitted;
    }
    public void setSubmitted(byte value)
    {
        this.submitted = value;
    }

    @Column(name="L1Approved")
    private byte l1Approved;
    public byte getL1Approved()
    {
        return this.l1Approved;
    }
    public void setL1Approved(byte value)
    {
        this.l1Approved = value;
    }

    @Column(name="L2APPROVED")
    private byte l2Approved;
    public byte getL2Approved()
    {
        return this.l2Approved;
    }
    public void setL2Approved(byte value)
    {
        this.l2Approved = value;
    }


    @Column(name="HRApproved")
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
