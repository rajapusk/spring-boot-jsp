package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "services_master")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ips_id", nullable = false)
    private int _IPS_ID;

    public int getIPS_ID() {
        return this._IPS_ID;
    }

    public void setIPS_ID(int value) {
        this._IPS_ID = value;
    }
    @Column(name = "p_id")
    private int _P_ID;

    public int getP_ID() {
        return this._P_ID;
    }

    public void setP_ID(int value) {
        this._P_ID = value;
    }
    @Column(name = "ip_nr")
    private int _IP_NR;

    public int getIP_NR() {
        return this._IP_NR;
    }

    public void setIP_NR(int value) {
        this._IP_NR = value;
    }
    @Column(name = "service_type_id")
    private int _SERVICE_TYPE_ID;

    public int getSERVICE_TYPE_ID() {
        return this._SERVICE_TYPE_ID;
    }

    public void setSERVICE_Type_Id(int value) {
        this._SERVICE_TYPE_ID = value;
    }
    @Column(name = "service_type")
    private String _SERVICE_TYPE;

    public String getSERVICE_TYPE() {
        return this._SERVICE_TYPE;
    }

    public void setSERVICE_TYPE(String value) {
        this._SERVICE_TYPE = value;
    }
    @Column(name = "service_code")
    private String _SERVICE_CODE;

    public String getSERVICE_Code() {
        return this._SERVICE_CODE;
    }

    public void setSERVICE_Code(String value) {
        this._SERVICE_CODE = value;
    }
    @Column(name = "service_category")
    private String _SERVICE_CATEGORY;

    public String getSERVICE_Category() {
        return this._SERVICE_CATEGORY;
    }

    public void setSERVICE_CATEGORY(String value) {
        this._SERVICE_CATEGORY = value;
    }
    @Column(name = "service_billed_date")
    private String _SERVICE_BILLED_DATE;

    public String getSERVICE_Billed_Date() {
        return this._SERVICE_BILLED_DATE;
    }

    public void setSERVICE_Billed_Date(String value) {
        this._SERVICE_BILLED_DATE = value;
    }
    @Column(name = "service_investigation")
    private String _SERVICE_INVESTIGATION;

    public String getSERVICE_INVESTIGATION() {
        return this._SERVICE_INVESTIGATION;
    }

    public void setSERVICE_INVESTIGATION(String value) {
        this._SERVICE_INVESTIGATION = value;
    }
    @Column(name = "service_qty")
    private String _SERVICE_QTY;

    public String getSERVICE_QTY() {
        return this._SERVICE_QTY;
    }

    public void setSERVICE_QTY(String value) {
        this._SERVICE_QTY = value;
    }
    @Column(name = "service_rate")
    private double _SERVICE_RATE;

    public double getSERVICE_RATE() {
        return this._SERVICE_RATE;
    }

    public void setSERVICE_RATE(double value) {
        this._SERVICE_RATE = value;
    }
    @Column(name = "service_amount")
    private String _SERVICE_AMOUNT;

    public String getSERVICE_AMOUNT() {
        return this._SERVICE_AMOUNT;
    }

    public void setSERVICE_AMOUNT(String value) {
        this._SERVICE_AMOUNT = value;
    }
    @Column(name = "discount_amount")
    private double _DISCOUNT_AMOUNT;

    public double getDISCOUNT_AMOUNT() {
        return this._DISCOUNT_AMOUNT;
    }

    public void setDISCOUNT_AMOUNT(double value) {
        this._DISCOUNT_AMOUNT = value;
    }

    @Column(name = "discount_percentage")
    private String _DISCOUNT_PERCENTAGE;

    public String getDISCOUNT_PERCENTAGE() {
        return this._DISCOUNT_PERCENTAGE;
    }

    public void setDISCOUNT_PERCENTAGE(String value) {
        this._DISCOUNT_PERCENTAGE = value;
    }

    @Column(name = "created_by_name")
    private String _CREATED_BY_NAME;

    public String getCREATED_BY_NAME() {
        return this._CREATED_BY_NAME;
    }

    public void setCREATED_BY_NAME(String value) {
        this._CREATED_BY_NAME = value;
    }

    @Column(name = "created_by_id")
    private int _CREATED_BY_ID;

    public int getCREATED_BY_ID() {
        return this._CREATED_BY_ID;
    }

    public void setCREATED_BY_ID(int value) {
        this._CREATED_BY_ID = value;
    }

    @Column(name = "created_on")
    private java.sql.Date _CREATED_ON;

    public java.sql.Date getCREATED_ON() {
        return this._CREATED_ON;
    }

    public void setCREATED_ON(java.sql.Date value) {
        this._CREATED_ON = value;
    }
}
