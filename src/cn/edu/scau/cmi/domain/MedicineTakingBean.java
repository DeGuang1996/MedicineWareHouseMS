package cn.edu.scau.cmi.domain;

import com.google.gson.annotations.Expose;

/*
 * 
 * 药品领取界面的TableView数据绑定类
 * */
public class MedicineTakingBean {
	private String taskType;
	private Integer num;
	private String medicineName;
	private Integer requestQuantity;
	private Integer takenQuantity;
	private String unit;
	@Expose
	private String boxTraceCode;
	@Expose
	private String caseTraceCode;
	@Expose
	private int taskId;
	@Expose
	private int medicineId;
	@Expose
	private int putawayId;
	
	public int getPutawayId() {
		return putawayId;
	}
	public void setPutawayId(int putawayId) {
		this.putawayId = putawayId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public Integer getRequestQuantity() {
		return requestQuantity;
	}
	public void setRequestQuantity(Integer requestQuantity) {
		this.requestQuantity = requestQuantity;
	}
	public Integer getTakenQuantity() {
		return takenQuantity;
	}
	public void setTakenQuantity(Integer takenQuantity) {
		this.takenQuantity = takenQuantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBoxTraceCode() {
		return boxTraceCode;
	}
	public void setBoxTraceCode(String boxTraceCode) {
		this.boxTraceCode = boxTraceCode;
	}
	public String getCaseTraceCode() {
		return caseTraceCode;
	}
	public void setCaseTraceCode(String caseTraceCode) {
		this.caseTraceCode = caseTraceCode;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	
	
	
	
	

	
}
