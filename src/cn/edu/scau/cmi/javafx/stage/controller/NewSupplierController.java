package cn.edu.scau.cmi.javafx.stage.controller;

import cn.edu.scau.cmi.javafx.stage.action.MedicineStoreStageAction;
import cn.edu.scau.cmi.javafx.stage.action.NewSupplierAction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewSupplierController {
	@FXML
	private TextField supplierName_TF;

	public void submit() {
		if (MedicineStoreStageAction.medicineStoreStage.isShowing())
			NewSupplierAction.storeStageSubmit(supplierName_TF.getText());
		else
			NewSupplierAction.managerStageSubmit(supplierName_TF.getText());
	}
}
