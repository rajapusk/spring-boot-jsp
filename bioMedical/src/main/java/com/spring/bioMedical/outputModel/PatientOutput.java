package com.spring.bioMedical.outputModel;

import java.util.ArrayList;

public class PatientOutput {
    public long mrNo;
    public String firstName;
    public String lastName;
    public String mobileNumber;
    public java.sql.Date dob;
    public String motherName;
    public String emailId;
    public String photo;
    public AddressOutput address;
    public ArrayList<NextOfKin> nextOfKin;
    public int age;
}
