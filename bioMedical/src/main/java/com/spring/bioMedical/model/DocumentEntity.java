package com.spring.bioMedical.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TBL_DOCUMENT")
public class DocumentEntity
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
	
	@Column(name="PAGE")
	private String page;
	public String getpage()
	{
		return this.page;
	}
	public void setpage(String value)
	{
		this.page = value;
	}
	
	@Column(name="pageID")
	private String pageId;
	public String getpageId()
	{
		return this.pageId;
	}
	public void setpageId(String value)
	{
		this.pageId = value;
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