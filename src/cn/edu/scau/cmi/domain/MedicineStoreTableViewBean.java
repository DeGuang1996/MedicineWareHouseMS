package cn.edu.scau.cmi.domain;

/*
 * 
 * 药品进库界面TableView的数据绑定类
 * */

public class MedicineStoreTableViewBean {
	private int num;//数量还是药品编号？？？
	private String medicineName;
	private String company;
	private int quantity;//数量
	private String unit;
	private float price;
	private String medicineNum;
	private int medicineId;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getMedicineNum() {
		return medicineNum;
	}

	public void setMedicineNum(String medicineNum) {
		this.medicineNum = medicineNum;
	}

	public int getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
