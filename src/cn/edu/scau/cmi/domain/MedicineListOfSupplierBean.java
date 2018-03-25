package cn.edu.scau.cmi.domain;

/*
 * 封装 供应商提供的所有药品类，实际是supplier，medicine, supplier，medicine_price价格的连接视图类
 * */
public class MedicineListOfSupplierBean {
	private String medicineName;
	private String company;
	private String unit;
	private float price;
	private int medicineId;
	private String medicineNum;
	private int supplierId;
	private int PharmaciseId;
	private int storeQuantity;
	private String memo;
	private String phone;
	private String caseTraceCode;
	private int isBox;

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getPharmaciseId() {
		return PharmaciseId;
	}

	public void setPharmaciseId(int pharmaciseId) {
		PharmaciseId = pharmaciseId;
	}

	public int getStoreQuantity() {
		return storeQuantity;
	}

	public void setStoreQuantity(int storeQuantity) {
		this.storeQuantity = storeQuantity;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCaseTraceCode() {
		return caseTraceCode;
	}

	public void setCaseTraceCode(String caseTraceCode) {
		this.caseTraceCode = caseTraceCode;
	}

	public int getIsBox() {
		return isBox;
	}

	public void setIsBox(int isBox) {
		this.isBox = isBox;
	}
}
