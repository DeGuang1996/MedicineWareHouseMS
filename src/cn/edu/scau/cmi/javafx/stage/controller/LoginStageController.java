package cn.edu.scau.cmi.javafx.stage.controller;

/*
 * 登陆界面的控制器
*/
import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginStageController {
	@FXML
	private TextField userID_TF;

	/*
	 * 登陆界面‘登陆’按钮事件
	 */
	public void Login_OK() {
		LoginStageAction.Login(userID_TF.getText());
	}

	/* 登陆界面‘退出’按钮事件 */
	public void Quit() {
		LoginStageAction.loginStage.close();// 退出登陆界面
	}

	// 键盘回车事件处理方法，绑定到userID_TF输入框，事件注册在LoginStage.fxml中定义

	public void commit(KeyEvent key) {
		if (key.getCode() == KeyCode.ENTER)
			Login_OK();
	}
}
