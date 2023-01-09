package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_TravelExpenseDetail")
public class TravelExpenseDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() {
        return this.id;
    }

    @Column(name = "TravelExpenseId")
    private long travelExpenseId;

    public long getTravelExpenseId() {
        return this.travelExpenseId;
    }

    public void setTravelExpenseId(long value) {
        this.travelExpenseId = value;
    }

    @Column(name = "ExpenseDescription")
    private String expenseDescription;

    public String getExpenseDescription() {
        return this.expenseDescription;
    }

    public void setExpenseDescription(String value) {
        this.expenseDescription = value;
    }

    @Column(name = "Origin")
    private String Origin;

    public String getOrigin() {
        return this.Origin;
    }

    public void setOrigin(String value) {
        this.Origin = value;
    }

    @Column(name = "Destination")
    private String destination;

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String value) {
        this.destination = value;
    }

    @Column(name = "PNRNo")
    private String pnrNo;

    public String getPNRNo() {
        return this.pnrNo;
    }

    public void setPNRNo(String value) {
        this.pnrNo = value;
    }


    @Column(name = "TravelStartDate")
    private java.sql.Date travelStartDate;

    public java.sql.Date getTravelStartDate() {
        return this.travelStartDate;
    }

    public void setTravelStartDate(java.sql.Date value) {
        this.travelStartDate = value;
    }


    @Column(name = "TravelEndDate")
    private java.sql.Date travelEndDate;

    public java.sql.Date getTravelEndDate() {
        return this.travelEndDate;
    }

    public void setTravelEndDate(java.sql.Date value) {
        this.travelEndDate = value;
    }

    @Column(name = "Distance")
    private int distance;

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int value) {
        this.distance = value;
    }


    @Column(name = "NoOfDays")
    private double noOfDays;

    public double getNoOfDays() {
        return this.noOfDays;
    }

    public void setNoOfDays(double value) {
        this.noOfDays = value;
    }


    @Column(name = "EntitledAmount")
    private int entitledAmount;

    public int getEntitledAmount() {
        return this.entitledAmount;
    }

    public void setEntitledAmount(int value) {
        this.entitledAmount = value;
    }



    @Column(name="BillAvailable")
    private byte billAvailable;
    public byte getBillAvailable()
    {
        return this.billAvailable;
    }
    public void setBillAvailable(byte value)
    {
        this.billAvailable = value;
    }

    @Column(name="NetAmount")
    private double netAmount;
    public double getNetAmount()
    {
        return this.netAmount;
    }
    public void setNetAmount(double value)
    {
        this.netAmount = value;
    }


    @Column(name="CGSTAmount")
    private double cgstAmount;
    public double getCGSTAmount()
    {
        return this.cgstAmount;
    }
    public void setCGSTAmount(double value)
    {
        this.cgstAmount = value;
    }

    @Column(name="SGSTAmount")
    private double sgstAmount;
    public double getSGSTAmount()
    {
        return this.sgstAmount;
    }
    public void setSGSTAmount(double value)
    {
        this.sgstAmount = value;
    }

    @Column(name="IGSTAmount")
    private double igstAmount;
    public double getIGSTAmount()
    {
        return this.igstAmount;
    }
    public void setIGSTAmount(double value)
    {
        this.igstAmount = value;
    }

    @Column(name = "TotalAmount")
    private double totalAmount;

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double value) {
        this.totalAmount = value;
    }

    @Column(name="ClaimAmount")
    private double claimAmount;
    public double getClaimAmount()
    {
        return this.claimAmount;
    }
    public void setClaimAmount(double value)
    {
        this.claimAmount = value;
    }

    @Column(name="DOCUMENT")
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
    public String getRemarks()
    {
        return this.remarks;
    }
    public void setRemarks(String value)
    {
        this.remarks = value;
    }

}
