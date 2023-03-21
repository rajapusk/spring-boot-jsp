package com.spring.bioMedical.outputModel;

import java.util.ArrayList;

public class patientOutput {
    public long mrNo;
    public java.sql.Date dateOfOpVisit;
    public java.sql.Time timeOfOpVisit;
    public String firstName;
    public String lastName;
    public String mobileNumber;
    public java.sql.Date dob;
    public String motherName;
    public String emailId;
    public AddressOutput address;
    public ArrayList<NextOfKin> nextOfKin;
}
