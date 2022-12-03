package com.cardview.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="PF_ACCOUNT")
public class PFAccountEntity
{
	@Id
	@Column(name="EMPCODE")
	private Long _EMPCODE;
	
	
	public Long getEMPCODE()
	{
		return this._EMPCODE;
	}
	public void setEMPCODE(Long value)
	{
		this._EMPCODE = value;
	}

	@Column(name="NAME")
	private String _NAME;

	public String getNAME()
	{
		return this._NAME;
	}
	public void setNAME(String value)
	{
		this._NAME = value;
	}

	@Column(name="EMAIL")
	private String _email;

	public String getEmail()
	{
		return this._email;
	}
	public void setEmail(String value)
	{
		this._email = value;
	}

	@Column(name="DOJ")
	private java.sql.Date _DOJ;
	public java.sql.Date getDOJ()
	{
		return this._DOJ;
	}
	public void setDOJ(java.sql.Date value)
	{
		this._DOJ = value;
	}

	@Column(name="PF_DOJ")
	private java.sql.Date _pfDOJ;
	public java.sql.Date getPfDOJ()
	{
		return this._pfDOJ;
	}
	public void setPfDOJ(java.sql.Date value)
	{
		this._pfDOJ = value;
	}
	
	@Column(name="DOB")
	private java.sql.Date _DOB;
	public java.sql.Date getDOB()
	{
		return this._DOB;
	}
	public void setDOB(java.sql.Date value)
	{
		this._DOB = value;
	}

	@Column(name="PRESENT_EXPERIENCE")
	private int _PRESENT_EXPERIENCE;
	public int getPRESENT_EXPERIENCE()
	{
		return this._PRESENT_EXPERIENCE;
	}
	public void setPRESENT_EXPERIENCE(int value)
	{
		this._PRESENT_EXPERIENCE = value;
	}

	@Column(name="DOR")
	private java.sql.Date _DOR;
	public java.sql.Date getDOR()
	{
		return this._DOR;
	}
	public void setDOR(java.sql.Date value)
	{
		this._DOR = value;
	}

	@Column(name="SERVICE_LEFT")
	private int _SERVICE_LEFT;
	public int getSERVICE_LEFT()
	{
		return this._SERVICE_LEFT;
	}
	public void setSERVICE_LEFT(int value)
	{
		this._SERVICE_LEFT = value;
	}

	@Column(name="MONTHLY_SALARY")
	private int _MONTHLY_SALARY;
	public int getMONTHLY_SALARY()
	{
		return this._MONTHLY_SALARY;
	}
	public void setMONTHLY_SALARY(int value)
	{
		this._MONTHLY_SALARY = value;
	}

	@Column(name="PF_BALANCE")
	private long _PF_BALANCE;
	public long getPF_BALANCE()
	{
		return this._PF_BALANCE;
	}
	public void setPF_BALANCE(long value)
	{
		this._PF_BALANCE = value;
	}

	@Column(name="Interest")
	private int _Interest;
	public int getInterest()
	{
		return this._Interest;
	}
	public void setInterest(int value)
	{
		this._Interest = value;
	}

	@Column(name="DESIGNATION")
	private String _DESIGNATION;
	public String getDESIGNATION()
	{
		return this._DESIGNATION;
	}
	public void setDESIGNATION(String value)
	{
		this._DESIGNATION = value;
	}
	
	@Column(name="GRADE")
	private String _GRADE;
	public String getGRADE()
	{
		return this._GRADE;
	}
	public void setGRADE(String value)
	{
		this._GRADE = value;
	}

	@Column(name="BAND")
	private String _BAND;
	public String getBAND()
	{
		return this._BAND;
	}
	public void setBAND(String value)
	{
		this._BAND = value;
	}

	@Column(name="ROLE")
	private String _ROLE;
	public String getROLE()
	{
		return this._ROLE;
	}
	public void setROLE(String value)
	{
		this._ROLE = value;
	}

	@Column(name="BRANCH")
	private int _BRANCH;
	public int getBRANCH()
	{
		return this._BRANCH;
	}
	public void setBRANCH(int value)
	{
		this._BRANCH = value;
	}

	@Column(name="PREVNETSALARY")
	private int _PrevNetSalary;
	public int getPrevNetSalary()
	{
		return this._PrevNetSalary;
	}
	public void setPrevNetSalary(int value)
	{
		this._PrevNetSalary = value;
	}

	@Column(name="NETSALPER")
	private double _NetSalPer;
	public double getNetSalPer()
	{
		return this._NetSalPer;
	}
	public void setNetSalPer(double value)
	{
		this._NetSalPer = value;
	}
	
	
	@Column(name="WORKSITECODE")
	private String _worksiteCode;
	public String getWorksiteCode()
	{
		return this._worksiteCode;
	}
	public void setWorksiteCode(String value)
	{
		this._worksiteCode = value;
	}

	@Column(name="LOCATION")
	private String _Location;
	public String getLocation()
	{
		return this._Location;
	}
	public void setLocation(String value)
	{
		this._Location = value;
	}

	@Column(name="PRESENTVPF")
	private double _PresentVPF;
	public double getPresentVPF()
	{
		return this._PresentVPF;
	}
	public void setPresentVPF(double value)
	{
		this._PresentVPF = value;
	}

	@Column(name="RATEOFINTEREST")
	private int _RATEOFINTEREST;
	public int getRATEOFINTEREST()
	{
		return this._RATEOFINTEREST;
	}
	public void setRATEOFINTEREST(int value)
	{
		this._RATEOFINTEREST = value;
	}

	@Column(name="NAMEASPERAADHAAR")
	private String nameInAadhaar;
	public String getNameInAadhaar()
	{
		return this.nameInAadhaar;
	}
	public void setNameInAadhaar(String value)
	{
		this.nameInAadhaar = value;
	}

	@Column(name = "GENDER")
	private String gender;
	public String getGender() { return this.gender; }
	public void setGender(String value) {
		this.gender = value;
	}

	@Column(name="PANNO")
	private String panNo;
	public String getPanNo()
	{
		return this.panNo;
	}
	public void setPanNo(String value)
	{
		this.panNo = value;
	}

	@Column(name="PF_NPS_AC_NO")
	private String pf_nps_ac_no;
	public String getPf_nps()
	{
		return this.pf_nps_ac_no;
	}
	public void setPf_nps(String value)
	{
		this.pf_nps_ac_no = value;
	}

	/*
	 * public pf_account(int EMPCODE_,String NAME_,java.sql.Date DOJ_,int
	 * PRESENT_EXPERIENCE_,java.sql.Date DOR_,int SERVICE_LEFT_,int
	 * MONTHLY_SALARY_,long PF_BALANCE_,int Interest_,String DESIGNATION_,String
	 * GRADE_,String BAND_,String ROLE_,int BRANCH_,int PrevNetSalary_,int
	 * RATEOFINTEREST_) { this.EMPCODE = EMPCODE_; this.NAME = NAME_; this.DOJ =
	 * DOJ_; this.PRESENT_EXPERIENCE = PRESENT_EXPERIENCE_; this.DOR = DOR_;
	 * this.SERVICE_LEFT = SERVICE_LEFT_; this.MONTHLY_SALARY = MONTHLY_SALARY_;
	 * this.PF_BALANCE = PF_BALANCE_; this.Interest = Interest_; this.DESIGNATION =
	 * DESIGNATION_; this.GRADE = GRADE_; this.BAND = BAND_; this.ROLE = ROLE_;
	 * this.BRANCH = BRANCH_; this.PrevNetSalary = PrevNetSalary_;
	 * this.RATEOFINTEREST = RATEOFINTEREST_; }
	 */
}
