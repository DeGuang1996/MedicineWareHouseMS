package cn.edu.scau.cmi.javafx.stage.controller;

import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicineStoreStageAction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/*
 * 药品进库的控制器
 * */
public class MedicineStoreStageController {
	
	@FXML
	private TextField dataInput_TF;
	
	/*
	 * 药品领取‘提交’按钮的Click事件
	 */
	public void MedicineStoreSubmit() {
		MedicineStoreStageAction.submit();
	}

	/*
	 * 药品进库‘清空’按钮的Click事件
	 */
	public void cleanList() {
		MedicineStoreStageAction.cleanList();
	}

	/*
	 * 药品进库‘返回菜单’按钮的Click事件
	 */
	public void MedicineStore() {
		MedicineStoreStageAction.close();
		cleanList();
		LoginStageAction.menuStage.show();
	}

	public void input(KeyEvent keyEvent) {
		MedicineStoreStageAction.dataScanner(keyEvent);
	}

}
