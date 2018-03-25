package cn.edu.scau.cmi.javafx.stage.controller;

import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicinePutawayStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicineTakenStageAction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

/*
 *药品上架的控制器 
 * */
public class MedicinePutawayStageController {
	@FXML
	private TextField dataInput_TF;

	/*
	 * 药品上架‘提交’按钮的Click事件
	 */
	public void MedicinePutawaySubmit() {
		MedicinePutawayStageAction.submit();
	}

	/*
	 * 药品上架清空按钮的Click事件
	 */
	public void cleanList() {
		MedicinePutawayStageAction.clear();
	}
	/*
	 * 返回菜单按钮的Click事件
	 */
	public void backToMainMenu() {
		MedicinePutawayStageAction.backToMainMnu();
	}

	public void dataInput(KeyEvent event) {
		MedicinePutawayStageAction.dataScanner(event);
	}

}
