package com.spring.bioMedical.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(schema= "his", name = "doctor_master")
public class Doctor {
@Id
@Column(name = "Id")
	private int    id;
@Column(name = "name")
	private String  name;
@Column(name = "qualification")
	private String    qualification;
@Column(name = "experience")
	private String    experience;
@Column(name = "experience_from")
	private String    experienceFrom;
@Column(name = "bloodgroup")
	private String    bloodgroup;
@Column(name = "mobile")
	private String    mobile;
@Column(name = "alternatemobile")
	private String    alternatemobile;
@Column(name = "email")
	private String    email;
@Column(name = "photo")
	private String    photo;
@Column(name = "pan")
	private String    pan;
@Column(name = "designation")
	private String    designation;
@Column(name = "gender")
	private String    gender;
@Column(name = "password")
	private String    password;
@Column(name = "specialization")
	private String    specialization;
@Column(name = "license_no")
	private String    licenseNo;
@Column(name = "create_id")
	private String    createId;
@Column(name = "create_date_time")
	private String    createDateTime;
@Column(name = "delete_flag")
	private String    deleteFlag;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getQualification() {
	return qualification;
}
public void setQualification(String qualification) {
	this.qualification = qualification;
}
public String getExperience() {
	return experience;
}
public void setExperience(String experience) {
	this.experience = experience;
}
public String getExperienceFrom() {
	return experienceFrom;
}
public void setExperienceFrom(String experienceFrom) {
	this.experienceFrom = experienceFrom;
}
public String getBloodgroup() {
	return bloodgroup;
}
public void setBloodgroup(String bloodgroup) {
	this.bloodgroup = bloodgroup;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getAlternatemobile() {
	return alternatemobile;
}
public void setAlternatemobile(String alternatemobile) {
	this.alternatemobile = alternatemobile;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getPan() {
	return pan;
}
public void setPan(String pan) {
	this.pan = pan;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getSpecialization() {
	return specialization;
}
public void setSpecialization(String specialization) {
	this.specialization = specialization;
}
public String getLicenseNo() {
	return licenseNo;
}
public void setLicenseNo(String licenseNo) {
	this.licenseNo = licenseNo;
}
public String getCreateId() {
	return createId;
}
public void setCreateId(String createId) {
	this.createId = createId;
}
public String getCreateDateTime() {
	return createDateTime;
}
public void setCreateDateTime(String createDateTime) {
	this.createDateTime = createDateTime;
}
public String getDeleteFlag() {
	return deleteFlag;
}
public void setDeleteFlag(String deleteFlag) {
	this.deleteFlag = deleteFlag;
}
@Override
public String toString() {
	return "Doctor [id=" + id + ", name=" + name + ", qualification=" + qualification + ", experience=" + experience
			+ ", experienceFrom=" + experienceFrom + ", bloodgroup=" + bloodgroup + ", mobile=" + mobile
			+ ", alternatemobile=" + alternatemobile + ", email=" + email + ", photo=" + photo + ", pan=" + pan
			+ ", designation=" + designation + ", gender=" + gender + ", password=" + password + ", specialization="
			+ specialization + ", licenseNo=" + licenseNo + ", createId=" + createId + ", createDateTime="
			+ createDateTime + ", deleteFlag=" + deleteFlag + "]";
}


	

	
}
