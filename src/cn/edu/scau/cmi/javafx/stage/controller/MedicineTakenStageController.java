package cn.edu.scau.cmi.javafx.stage.controller;

import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import cn.edu.scau.cmi.javafx.stage.action.MedicineTakenStageAction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

/*
 *药品领取的控制器 
 * */
public class MedicineTakenStageController {

	/*
	 * 药品领取‘提交’按钮的Click事件
	 */
	public void MedicineTakenSubmit() {
		MedicineTakenStageAction.submit();
	}

	/*
	 * 药品领取‘清空’按钮的Click事件
	 */
	public void cleanList() {
		MedicineTakenStageAction.clear();
	}
	

	public void backToMenu() {
		MedicineTakenStageAction.backToMenu();
	}

	public void dataInput(KeyEvent event) {
		MedicineTakenStageAction.dataScanner(event);
	}


}
