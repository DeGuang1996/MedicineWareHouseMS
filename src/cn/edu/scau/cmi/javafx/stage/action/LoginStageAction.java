package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.domain.LoginStageBean;
import cn.edu.scau.cmi.utils.DataObject;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * 登陆界面处理类
 * */
public class LoginStageAction {
	public static boolean isLoginSuccess = true;
	public static int pharmacistId;
	public static String pharmacistName;
	public static int roleId = 20;
//	初始化登录界面和菜单界面，如果登录成功，显示菜单界面，登录界面关闭
	public static Stage loginStage = new StageInitShow("LoginStage");;
	public static Stage menuStage = new StageInitShow("MenuStage");

	static {
		menuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				loginStage.show();
			}
		});
	}
	//	响应登录界面的事件
	public static void Login(String data) {
		boolean isFind = findEmployeeFromLoginStageBeanList(data);
		if (isFind) {
			menuStage.setTitle("请选择操作");
			menuStage.setResizable(false);
			menuStage.show();//显示菜单界面
			loginStage.close();//关闭登录界面
		} else {
			TipAction.showTip("没有该用户，请重新输入！！！");
		}
	}
	
	// 检测电话号码登录，
	public static boolean findEmployeeFromLoginStageBeanList(String data) {
		String[] data2 = data.split("\\D");
		String number = "";
		for (String num : data2) {
			number += num;
		}
		for (LoginStageBean lsb : DataObject.getLoginStageBeanList()) {
			if (lsb.getEmployeeNumber().equals(number) && lsb.getRoleId() == roleId) {
				pharmacistId = lsb.getEmployeeId();
				pharmacistName = lsb.getEmployeeName();
				return true;
			}
		}
		return false;
	}
}