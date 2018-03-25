package cn.edu.scau.cmi.javafx.stage.controller;

import java.io.IOException;
import com.hombio.QRCode.App;
import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicinePutawayStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicineStoreStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicineTakenStageAction;
import cn.edu.scau.cmi.javafx.stage.action.StoreHouseManagerStageAction;
import cn.edu.scau.cmi.utils.DataObject;
import javafx.fxml.FXML;

/*登录后功能选择*/
public class MenuStageController {
//	取药
	public void medicineTaken() {
		LoginStageAction.menuStage.close();
		DataObject.initDataList("medicineBeanList", 0);// 初始化药品列表
		MedicineTakenStageAction.initStageAction();
	}
//	进库
	public void medicineStore() {
//		DataObject.initDataList("medicineBeanList", 0);// 初始化药品列表
		MedicineStoreStageAction.initStageAction();
	}
//	上架
	public void medicinePutaway() {
		MedicinePutawayStageAction.initStageAction();
	}

	public void sotreDataManager() {
		LoginStageAction.menuStage.close();
		DataObject.initDataList("medicineBeanList", 0);// 初始化药品列表
		DataObject.initDataList("supplierBeanList", 0);
		StoreHouseManagerStageAction.initStageAction();
	}
	
	
	
	

	public void QRcode() {
		try {
			LoginStageAction.menuStage.setAlwaysOnTop(false);
			App.getCoderStage().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML 
	public void backToLoading() {
		LoginStageAction.menuStage.close();
		LoginStageAction.loginStage.show();
	}
}
