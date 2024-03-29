package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "services_master")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "fee")
    private double fee;
    public double getFee() {
        return fee;
    }
    public void setFee(double value) {
        this.fee = value;
    }

    @Column(name = "serviceName")
    private String serviceName;
    public String getServiceName() {
        return this.serviceName;
    }
    public void setServiceName(String value) {
        this.serviceName = value;
    }

}
