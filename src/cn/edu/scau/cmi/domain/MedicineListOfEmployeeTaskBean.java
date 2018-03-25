package cn.edu.scau.cmi.domain;

/*
 * 员工任务数据类
 * */
/*
 * 准备改成通用的数据传输
 * */
public class MedicineListOfEmployeeTaskBean {
	private int num;// 序号
	private String taskType;// 任务类型
	private int taskId;// 任务id
	private String medicineName;// 药品名称
	private double requestQuantity;// 任务数量
	private String unit;// 单位
	private double todayTakenQuantity;// 今日已领取数量
	private int nowTakenSize = 0;// 当前已领取药品数量
	private int medicineId;// 药品id
	private int pharmacistId;// 药品管理员id
	private int employeeId;// 领取药品的员工id
	private String medicineNum;// 药品字号
	private int isBox;// 是否为大箱
	private double maxTakenSize;
	private int quantityPerCase;
	private int quantityPerBox;
	private String traceCode = "null";
	private String caseTraceCode = "null";
	private String boxTraceCode = "null";
	private int supplierId;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getIsBox() {
		return isBox;
	}

	public void setIsBox(int isBox) {
		this.isBox = isBox;
	}

	public int getQuantityPerCase() {
		return quantityPerCase;
	}

	public void setQuantityPerCase(int quantityPerCase) {
		this.quantityPerCase = quantityPerCase;
	}

	public int getQuantityPerBox() {
		return quantityPerBox;
	}

	public void setQuantityPerBox(int quantityPerBox) {
		this.quantityPerBox = quantityPerBox;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public String getCaseTraceCode() {
		return caseTraceCode;
	}

	public void setCaseTraceCode(String caseTraceCode) {
		this.caseTraceCode = caseTraceCode;
	}

	public String getBoxTraceCode() {
		return boxTraceCode;
	}

	public void setBoxTraceCode(String boxTraceCode) {
		this.boxTraceCode = boxTraceCode;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public double getRequestQuantity() {
		return requestQuantity;
	}

	public void setRequestQuantity(double requestQuantity) {
		this.requestQuantity = requestQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getTodayTakenQuantity() {
		return todayTakenQuantity;
	}

	public void setTodayTakenQuantity(double todayTakenQuantity) {
		this.todayTakenQuantity = todayTakenQuantity;
	}

	public double getNowTakenSize() {
		return nowTakenSize;
	}

	public void setNowTakenSize(int nowTakenSize) {
		this.nowTakenSize = nowTakenSize;
	}

	public int getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}

	public String getMedicineNum() {
		return medicineNum;
	}

	public void setMedicineNum(String medicineNum) {
		this.medicineNum = medicineNum;
	}

	public int getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(int pharmacistId) {
		this.pharmacistId = pharmacistId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public double getMaxTakenSize() {
		return maxTakenSize;
	}

	public void setMaxTakenSize(double maxTakenSize) {
		this.maxTakenSize = maxTakenSize;
	}

}
