package com.cardview.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TBL_EMP_DOC")
public class EmpDocEntity
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
	
	@Column(name="PF_LOAN_ID")
	private long pfLoanId;
	public long getpfLoanId()
	{
		return this.pfLoanId;
	}
	public void setpfLoanId(long value)
	{
		this.pfLoanId = value;
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
}