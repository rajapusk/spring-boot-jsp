package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "appointment_diagnostic_entity")
public class AppointmentDiagnosticEntity {

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

    @Column(name = "appointmentId")
    private Long appointmentId;

    public Long getAppointmentId() {
        return this.appointmentId;
    }

    public void setAppointmentId(Long value) {
        this.appointmentId = value;
    }

    @Column(name = "diagnosticId")
    private int diagnosticId;

    public int getDiagnosticId() {
        return this.diagnosticId;
    }

    public void setDiagnosticId(int value) {
        this.diagnosticId = value;
    }

    @Column(name = "fee")
    private double fee;

    public double getFee() {
        return this.fee;
    }

    public void setFee(double value) {
        this.fee = value;
    }
    @Column(name = "paymentTypeId")
    private int paymentTypeId;
    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    public int getPaymentTypeId() {
        return paymentTypeId;
    }



    @Column(name = "count")
    private int count;
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    @Column(name = "visitType")
    private byte visitType;
    public void setVisitType(byte visitType) {
        this.visitType = visitType;
    }
    public byte getVisitType() {
        return visitType;
    }

}
