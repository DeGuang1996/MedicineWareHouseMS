package cn.edu.scau.cmi.javafx.stage.controller;

import cn.edu.scau.cmi.javafx.stage.action.MedicineStoreStageAction;
import cn.edu.scau.cmi.javafx.stage.action.NewMedicineStageAction;

public class NewMedicineStageController {
	public void submit() {
		if (MedicineStoreStageAction.medicineStoreStage.isShowing())
			NewMedicineStageAction.storeStageSubmit();
		else {
			NewMedicineStageAction.managerStageSubmit();
		}
	}
}
