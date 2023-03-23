package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "appointment_doctor_entity")
public class AppointmentDoctorEntity {

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

    @Column(name = "doctorId")
    private int doctorId;

    public int getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(int value) {
        this.doctorId = value;
    }

    @Column(name = "fee")
    private double fee;

    public double getFee() {
        return this.fee;
    }

    public void setFee(double value) {
        this.fee = value;
    }
}
