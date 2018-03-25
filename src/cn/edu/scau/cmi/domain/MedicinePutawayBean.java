package cn.edu.scau.cmi.domain;

import com.google.gson.annotations.Expose;

public class MedicinePutawayBean {
	
	
	private String id;
	
	private String subId;
	
	private String medicine;
	@Expose
	private String traceCode;
	
	private String quantity;
	@Expose
	private String number;
	
	private String rest;
	
	
	
	public String getRest() {
		return rest;
	}
	public void setRest(String rest) {
		this.rest = rest;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getTraceCode() {
		return traceCode;
	}
	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	

	
}
