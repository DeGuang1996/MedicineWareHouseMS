package cn.edu.scau.cmi.javafx.stage.controller;

import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import cn.edu.scau.cmi.javafx.stage.action.NewMedicineStageAction;
import cn.edu.scau.cmi.javafx.stage.action.StoreHouseManagerStageAction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class StoreHouseManagerStageController {
	@FXML
	private TextField input_tf;

	public void add() {
		StoreHouseManagerStageAction.add(input_tf.getText());
		input_tf.setText("");
	}

	public void backToLogin() {
		StoreHouseManagerStageAction.storeHouseManagerStage.close();
		LoginStageAction.menuStage.show();
	}
	public void input(KeyEvent keyEvent){
		if (keyEvent.getCode() == KeyCode.ENTER) {
			String s = input_tf.getText().substring(0, 24);
			NewMedicineStageAction.initStage(s);
			input_tf.setText("");
		}
	}
}
