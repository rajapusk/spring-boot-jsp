package com.cardview.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TBL_EXPENSES")
public class ExpenseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="exp_type")
	private String expenseType;

	@Column(name="cust_type")
	private String customerType;

	@Column(name="purpose")
	private String purpose;

	@Column(name="vend_name")
	private String vendorName;

	@Column(name="inv_date")
	private Date invoiceDate;

	@Column(name="inv_no")
	private String invoiceNumber;

	@Column(name="inv_amt")
	private double invoiceAmount;

	@Column(name="sanc_amt")
	private double sanctionedAmount;

	@Column(name="clm_amt")
	private double claimedAmount;

	@Column(name="remarks")
	private String remarks;

	@Column(name="attachments")
	private String attachments;

	@Lob
	@Column(name="attach")
	private byte[] attach;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public double getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(double sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public double getClaimedAmount() {
		return claimedAmount;
	}

	public void setClaimedAmount(double claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public byte[] getAttach() {
		return attach;
	}

	public void setAttach(byte[] attach) {
		this.attach = attach;
	}

	@Override
	public String toString() {
		return "ExpenseEntity [id=" + id + ", expenseType=" + expenseType +
				", customerType=" + customerType + ", purpose=" + purpose   +
				", vendorName=" + vendorName   + ", invoiceDate=" + invoiceDate   +
				", invoiceNumber=" + invoiceNumber   + ", invoiceAmount=" + invoiceAmount   +
				", sanctionedAmount=" + sanctionedAmount   + ", claimedAmount=" + claimedAmount   +
				", remarks=" + remarks +", attachments=" + attachments +"]";
	}
}