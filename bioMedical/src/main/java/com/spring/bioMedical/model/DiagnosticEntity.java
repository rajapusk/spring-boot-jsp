package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "diagnostic_master")
public class DiagnosticEntity {
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

    @Column(name = "diagnosticName")
    private String diagnosticName;
    public String getDiagnostic() {
        return this.diagnosticName;
    }
    public void setDiagnostic(String value) {
        this.diagnosticName = value;
    }
}
