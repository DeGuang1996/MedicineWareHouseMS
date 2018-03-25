package cn.edu.scau.cmi.domain;

/*
 * 新建药品时，该药品还么有供应商，
 * 入库的时候没有增供应商的时候用到
 * 药品供应商类
 * */
public class MedicineSupplierBean {
	private int id;
	private MedicineBean medicine;
	private SupplierBean supplier;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MedicineBean getMedicine() {
		return medicine;
	}

	public void setMedicine(MedicineBean medicine) {
		this.medicine = medicine;
	}

	public SupplierBean getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierBean supplier) {
		this.supplier = supplier;
	}

}
