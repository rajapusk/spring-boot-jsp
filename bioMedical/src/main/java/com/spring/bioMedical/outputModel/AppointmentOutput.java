package com.spring.bioMedical.outputModel;


import java.util.ArrayList;

public class AppointmentOutput {
    public Long patientId;
    public String department;
    public String patientType;
    public String referral;
    public String visitType;
    public String payeeType;
    public ArrayList<ConsultationDoctorOutput> consultationDoctor;
    public ArrayList<DiagnosticOutput> diagnostics;
    public ArrayList<ServiceOutput> services;
    public double totalAmount;
    public double amountPaid;
    public String upiCard;
    public java.sql.Date dateOfOpVisit;
    public java.sql.Time timeOfOpVisit;
}