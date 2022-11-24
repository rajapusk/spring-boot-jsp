package com.cardview.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TBL_PF_LOAN")
public class PFLoanEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getid()
	{
		return this.id;
	}
	public void setid(Long value)
	{
		this.id = value;
	}

	@Column(name="EMPCODE")
	private long _empcode;
	public long getempcode()
	{
		return this._empcode;
	}
	public void setempcode(long value)
	{
		this._empcode = value;
	}

	@Column(name="ADVANCETYPE")
	private String advanceType;
	public String getadvanceType()
	{
		return this.advanceType;
	}
	public void setadvanceType(String value)
	{
		this.advanceType = value;
	}

	@Column(name="REQUIREDAMOUNT")
	private int requiredAmount;
	public int getrequiredAmount()
	{
		return this.requiredAmount;
	}
	public void setrequiredAmount(int value)
	{
		this.requiredAmount = value;
	}

	@Column(name="EMIAMOUNT")
	private int emiAmount;
	public int getemiAmount()
	{
		return this.emiAmount;
	}
	public void setemiAmount(int value)
	{
		this.emiAmount = value;
	}

	@Column(name="NOOFEMI")
	private int noOfEMI;
	public int getnoOfEMI()
	{
		return this.noOfEMI;
	}
	public void setnoOfEMI(int value)
	{
		this.noOfEMI = value;
	}

	@Column(name="NEWNETSALARY")
	private int newNetSalary;
	public int getnewNetSalary()
	{
		return this.newNetSalary;
	}
	public void setnewNetSalary(int value)
	{
		this.newNetSalary = value;
	}

	@Column(name="NEWNETSALARYPER")
	private double newNetSalaryPer;
	public double getnewNetSalaryPer()
	{
		return this.newNetSalaryPer;
	}
	public void setnewNetSalaryPer(double value)
	{
		this.newNetSalaryPer = value;
	}

	@Column(name="REMARKS")
	private String remarks;
	public String getremarks()
	{
		return this.remarks;
	}
	public void setremarks(String value)
	{
		this.remarks = value;
	}
	
	@Column(name="fileNAME")
	private String fileName;
	public String getfileName()
	{
		return this.fileName;
	}
	public void setfileName(String value)
	{
		this.fileName = value;
	}

	@Column(name="SUBMITTED")
	private byte submitted;
	public byte getsubmitted()
	{
		return this.submitted;
	}
	public void setsubmitted(byte value)
	{
		this.submitted = value;
	}

	@Column(name="APPROVED")
	private byte approved;
	public byte getapproved()
	{
		return this.approved;
	}
	public void setapproved(byte value)
	{
		this.approved = value;
	}
	
	@Column(name="TYPE")
	private byte type;
	public byte getType()
	{
		return this.type;
	}
	public void setType(byte value)
	{
		this.type = value;
	}


	/*
	 * public PFLoanEntity(int id_,int empcode_,String advanceType_,int
	 * requiredAmount_,int emiAmount_,int noOfEMI_,int newNetSalary_,double
	 * newNetSalaryPer_,String remarks_,byte[] submitted_,byte[] approved_) {
	 * this.id = id_; this.empcode = empcode_; this.advanceType = advanceType_;
	 * this.requiredAmount = requiredAmount_; this.emiAmount = emiAmount_;
	 * this.noOfEMI = noOfEMI_; this.newNetSalary = newNetSalary_;
	 * this.newNetSalaryPer = newNetSalaryPer_; this.remarks = remarks_;
	 * this.submitted = submitted_; this.approved = approved_; }
	 */
}