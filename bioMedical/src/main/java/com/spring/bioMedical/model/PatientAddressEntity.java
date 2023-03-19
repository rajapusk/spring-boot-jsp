package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "patient_address_master")
public class PatientAddressEntity {
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

    @Column(name = "patientId")
    private Long patientId;

    public Long getPatientId() {
        return this.patientId;
    }

    public void setPatientId(Long value) {
        this.patientId = value;
    }

    @Column(name = "houseNo")
    private String houseNo;

    public String getHouseNo() {
        return this.houseNo;
    }

    public void setHouseNo(String value) {
        this.houseNo = value;
    }

    @Column(name = "building")
    private String building;

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String value) {
        this.building = value;
    }

    @Column(name = "street")
    private String street;

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String value) {
        this.street = value;
    }

    @Column(name = "colony")
    private String colony;

    public String getColony() {
        return this.colony;
    }

    public void setColony(String value) {
        this.colony = value;
    }

    @Column(name = "city")
    private String city;

    public String getCity() {
        return this.city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    @Column(name = "district")
    private String district;

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String value) {
        this.district = value;
    }

    @Column(name = "state")
    private String state;

    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
    }

    @Column(name = "pinCode")
    private String pinCode;

    public String getPinCode() {
        return this.pinCode;
    }

    public void setPinCode(String value) {
        this.pinCode = value;
    }
}