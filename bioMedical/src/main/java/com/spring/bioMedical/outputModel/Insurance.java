package com.spring.bioMedical.outputModel;


public class Insurance {
	public Long id;
	public String opType;
	public String opTypeName;
	public String insurance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getOpTypeName() {
		return opTypeName;
	}

	public void setOpTypeName(String opTypeName) {
		opTypeName = opTypeName;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
}
