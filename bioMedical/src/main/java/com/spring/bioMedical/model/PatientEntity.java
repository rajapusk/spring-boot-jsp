package com.spring.bioMedical.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(schema= "his", name = "patient_master")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "firstName")
    private String firstName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    @Column(name = "lastName")
    private String lastName;

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    @Column(name = "mobileNumber")
    private String mobileNumber;

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String value) {
        this.mobileNumber = value;
    }
    @Column(name = "motherName")
    private String motherName;

    public String getMotherName() {
        return this.motherName;
    }

    public void setMotherName(String value) {
        this.motherName = value;
    }
    
    @JsonProperty("dob")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "dob")
    private java.sql.Date dob;

    public java.sql.Date getDob() {
        return this.dob;
    }

    public void setDob(java.sql.Date value) {
        this.dob = value;
    }
    @Column(name = "emailId")
    private String emailId;

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String value) {
        this.emailId = value;
    }
    
    @JsonProperty("dateOfOpVisit")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "dateOfOpVisit")
    private java.sql.Date dateOfOpVisit;

    public java.sql.Date getDateOfOpVisit() {
        return this.dateOfOpVisit;
    }

    public void setDateOfOpVisit(java.sql.Date value) {
        this.dateOfOpVisit = value;
    }
    
    @JsonProperty("timeOfOpVisit")
    @JsonFormat(pattern="HH:mm:ss.SSS")
    @Column(name = "timeOfOpVisit")
    private java.sql.Time timeOfOpVisit;

    public java.sql.Time getTimeOfOpVisit() {
        return this.timeOfOpVisit;
    }

    public void setTimeOfOpVisit(java.sql.Time value) {
        this.timeOfOpVisit = value;
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
    @Column(name = "isDeleted")
    private boolean isDeleted;

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean value) {
        this.isDeleted = value;
    }
}
