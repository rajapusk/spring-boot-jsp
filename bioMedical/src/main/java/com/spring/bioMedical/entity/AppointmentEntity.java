package com.spring.bioMedical.entity;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "appointment_master")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "patientId")
    private Long patientId;
    @Column(name = "isDeleted")
    private boolean isDeleted;
    @Column(name = "amountPaid")
    private double amountPaid;
    @Column(name = "department")
    private String department;
    @Column(name = "payeeType")
    private String payeeType;
    @Column(name = "totalAmount")
    private double totalAmount;
    @Column(name = "referral")
    private String referral;
    @Column(name = "upiCard")
    private String upiCard;
    @Column(name = "visitType")
    private String visitType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "patientType")
    private String patientType;
    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setPayeeType(String payeeType) {
        this.payeeType = payeeType;
    }

    public String getPayeeType() {
        return payeeType;
    }


    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getReferral() {
        return referral;
    }

    public void setUpiCard(String upiCard) {
        this.upiCard = upiCard;
    }

    public String getUpiCard() {
        return upiCard;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Column(name = "CREATED_ON")
    private java.sql.Date createdOn;

    public java.sql.Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(java.sql.Date value) {
        this.createdOn = value;
    }

    @Column(name = "UPDATED_ON")
    private java.sql.Date updatedOn;

    public java.sql.Date getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(java.sql.Date value) {
        this.updatedOn = value;
    }
}
