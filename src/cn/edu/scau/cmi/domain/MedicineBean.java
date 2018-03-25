package cn.edu.scau.cmi.domain;

/*
 * 药品类，对应药品表
 * */
public class MedicineBean {
	private int id;
	private String name;
	private String company;
	private String phone;
	//药品的编号
	private String number;
	private String memo;
//	每箱药品的盒子或者瓶的数量
	private int quantityPerCase;//箱
	private int quantityPerBox;//盒或者瓶

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

}
