package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.MedicineStoreTableViewBean;
import cn.edu.scau.cmi.utils.DataObject;
import cn.edu.scau.cmi.utils.PostDataToServer;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewMedicineStageAction {
	static TextField medicineName_tf;
	static TextField company_tf;
	static TextField memo_tf;
	static TextField price_tf;
	static TextField quantityPerCase_tf;
	static TextField quantityPerBox_tf;
	static TextField number_tf;
	static TextField phone_tf;
	static MedicineBean newMedicine = new MedicineBean();
	static Stage newMedicineStage;
	static boolean newMedicineStageIsOpen = false;
	static String caseTraceCode;
	static Thread t;

	public static void initStage(String cTC) {
		newMedicineStage = new StageInitShow("NewMedicineStage");
		newMedicineStage.setTitle("新建药品");
		initTextField();
		if (cTC != "") {
			test(cTC);
			caseTraceCode = cTC.substring(0,24);
		} else
			newMedicineStage.show();
		newMedicineStageIsOpen = true;
	}

	public static void initTextField() {
		medicineName_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#medicineName");
		company_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#company");
		memo_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#memo");
		phone_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#phone");
		price_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#price");
		quantityPerCase_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#quantityPerCase");
		quantityPerBox_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#quantityPerBox");
		number_tf = (TextField) StageInitShow.newMedicineStageParent.lookup("#number");
	}

	public static void setTextFieldData() {
		medicineName_tf.setText(newMedicine.getName());
		company_tf.setText(newMedicine.getCompany());
		memo_tf.setText(newMedicine.getMemo());
		phone_tf.setText(newMedicine.getPhone());
		price_tf.setText("0");
		quantityPerCase_tf.setText(String.valueOf(newMedicine.getQuantityPerCase()));
		quantityPerBox_tf.setText(String.valueOf(newMedicine.getQuantityPerBox()));
		number_tf.setText(newMedicine.getNumber());
	}

	public static void setMedicineData() {
		newMedicine.setCompany(company_tf.getText());
		newMedicine.setMemo(memo_tf.getText());
		newMedicine.setPhone(phone_tf.getText());
		newMedicine.setName(medicineName_tf.getText());
		try {
			newMedicine.setQuantityPerCase(Integer.valueOf(quantityPerCase_tf.getText()));
			newMedicine.setQuantityPerBox(Integer.valueOf(quantityPerBox_tf.getText()));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("输入数值错误！");
		}
		newMedicine.setNumber(number_tf.getText());
	}

	public static void saveMedicineData() {
		setMedicineData();
		PostDataToServer.postNewMedicineData(newMedicine);
	}

	public static void flashMedicineList() {
		DataObject.initDataList("medicineBeanList", 0);
	}

	public static void findMedicineId() {
		int Id = DataObject.getMedicineBeanList().get(DataObject.getMedicineBeanList().size() - 1).getId();
		newMedicine.setId(Id);
	}

	public static void saveMedicineSupplierToServer() {
		boolean result = PostDataToServer.postMedicineSupplierData(newMedicine.getId(),
				MedicineStoreStageAction.supplierId);
		if (result) {
			TipAction.showTip("submitSuccess");
		} else {
			TipAction.showTip("submitFaild");
		}
	}

	public static void setTableViewData() {
		MedicineStoreTableViewBean m = new MedicineStoreTableViewBean();
		m.setCompany(company_tf.getText());
		m.setMedicineName(medicineName_tf.getText());
		m.setMedicineNum(number_tf.getText());
		m.setPrice(Float.valueOf(price_tf.getText()));
		m.setUnit("箱");
		m.setQuantity(1);
		m.setNum(MedicineStoreStageAction.num);
		MedicineStoreStageAction.num++;
		m.setMedicineId(newMedicine.getId());
		MedicineStoreStageAction.medicineList.add(m);
		MedicineStoreStageAction.addToStoreList(caseTraceCode, newMedicine.getId(), Float.valueOf(price_tf.getText()));
	}

	public static void storeStageSubmit() {
		saveMedicineData();
		flashMedicineList();
		findMedicineId();
		saveMedicineSupplierToServer();
		flashMedicineList();
		setTableViewData();
		newMedicineStage.close();
	}

	public static void managerStageSubmit() {
		saveMedicineData();
		flashMedicineList();
		MedicineBean medicine = DataObject.getMedicineBeanList().get(DataObject.getMedicineBeanList().size() - 1);
		StoreHouseManagerStageAction.medicineList.add(medicine);
		newMedicineStage.close();

	}

	public static void test(String zsm) {
		Stage tip = new StageInitShow("TipStage");
		Runnable run = new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Platform.runLater(new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						Label tipLB = (Label) StageInitShow.tipStageParent.lookup("#tipText_lb");
						Label tipLB2 = (Label) StageInitShow.tipStageParent.lookup("#tip2_lb");
						tipLB2.setText("时间可能较长，请耐心等待");
						tipLB.setText("正在获取数据");
						tip.show();
					}
				});
				try {
					newMedicine = PostDataToServer.postZSMtoIVDC(zsm);
				} finally {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							setTextFieldData();
							newMedicineStage.show();
							tip.close();
						}
					});
				}

				t.stop();
			}
		};
		t = new Thread(run);
		t.start();
	}
}
