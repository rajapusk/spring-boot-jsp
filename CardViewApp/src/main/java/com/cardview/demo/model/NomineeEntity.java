package com.cardview.demo.model;

import javax.persistence.*;

@Entity
@Table(name="TBL_NOMINEE")
public class NomineeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Column(name = "PF_NOMINEE_ID")
    private long pfNominee_id;

    public long getPfNomineeId() {
        return this.pfNominee_id;
    }

    public void setPfNomineeId(long value) {
        this.pfNominee_id = value;
    }

    @Column(name = "NAME")
    private String name;
    public String getName() { return this.name; }
    public void setName(String value) {
        this.name = value;
    }


    @Column(name = "RELATION")
    private String relation;
    public String getRelation() { return this.relation; }
    public void setRelation(String value) {
        this.relation = value;
    }

    @Column(name = "ISMINOR")
    private boolean isMinor;
    public boolean getIsMinor() { return this.isMinor; }
    public void setIsMinor(boolean value) {
        this.isMinor = value;
    }


    @Column(name = "MINOR_DOB")
    private java.sql.Date minorDOB;
    public java.sql.Date getMinorDOB() { return this.minorDOB; }
    public void setMinorDOB(java.sql.Date value) {
        this.minorDOB = value;
    }

    @Column(name = "GUARDIANS_NAME")
    private String guardiansName;
    public String getGuardiansName() { return this.guardiansName; }
    public void setGuardiansName(String value) {
        this.guardiansName = value;
    }

    @Column(name = "NOMINEE_AADHAAR_NO")
    private String nomineeAadhaarNo;
    public String getNomineeAadhaarNo() { return this.nomineeAadhaarNo; }
    public void setNomineeAadhaarNo(String value) {
        this.nomineeAadhaarNo = value;
    }

    @Column(name = "DOB")
    private java.sql.Date dob;
    public java.sql.Date getDOB() { return this.dob; }
    public void setDOB(java.sql.Date value) {
        this.dob = value;
    }

    @Column(name = "GENDER")
    private String gender;
    public String getGender() { return this.gender; }
    public void setGender(String value) {
        this.gender = value;
    }

    @Column(name = "Address")
    private String address;
    public String getAddress() { return this.address; }
    public void setAddress(String value) {
        this.address = value;
    }

    @Column(name = "Proportion")
    private double proportion;
    public double getProportion() { return this.proportion; }
    public void setProportion(double value) {
        this.proportion = value;
    }

    @Column(name = "IsDeleted")
    private boolean isdeleted;
    public boolean getIsDeleted() { return this.isdeleted; }
    public void setIsDeleted(boolean value) {
        this.isdeleted = value;
    }
}
