package cn.edu.scau.cmi.javafx.stage;

import java.io.IOException;

import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;
import cn.edu.scau.cmi.javafx.stage.action.TipAction;
import cn.edu.scau.cmi.utils.DataObject;
import javafx.application.Application;
import javafx.stage.Stage;

public class ZhengAnApplication extends Application {
	/*
	 * 获取登陆界面数据
	 */
	static {
		DataObject.initDataList("loginStageBeanList", 0);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage = LoginStageAction.loginStage;
		primaryStage.setTitle("请输入手机号登录");// 设置标题
		primaryStage.setResizable(false);// 设置不可调节
		if (DataObject.getLoginStageBeanList() != null) // 获取到数据，弹出登陆界面
		{
			primaryStage.show();
		} else // 否则弹出提示框，提示获取数据错误。
			TipAction.showTip("没有获取登录初始化数据，请联系系统管理员！！！");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
