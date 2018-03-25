package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TipAction {
	static String serverNotLaunch = "无法连接服务器";
	static String getDataError = "获取数据异常";
	static String loginSuccess = "登陆成功";
	static String loginFailed = "登陆失败";
	static String submitSuccess = "提交成功";
	static String submitFailed = "提交失败";
	static String inputError = "规格只能为整数";
	static String connectWaiting = "正在从兽医网查询数据";
	static String overMaxQuantity = "超出最大领取值";
	static Stage tipStage = new StageInitShow("TipStage");
	static int seconde = 2;
	static Thread showTip;

	public static void setTipTextLabelText(String tipText) {
		Label tipText_Lb = (Label) StageInitShow.tipStageParent.lookup("#tipText_lb");
		switch (tipText) {
		case "loginFailed":
			tipText_Lb.setText(loginFailed);
			break;
		case "loginSuccess":
			tipText_Lb.setText(loginSuccess);
			break;
		case "submitSuccess":
			tipText_Lb.setText(submitSuccess);
			break;
		case "submitFailed":
			tipText_Lb.setText(submitFailed);
			break;
		case "getDataError":
			tipText_Lb.setText(getDataError);
			break;
		case "serverNotLaunch":
			tipText_Lb.setText(serverNotLaunch);
			break;
		case "inputError":
			tipText_Lb.setText(inputError);
			break;
		case "connectWaiting":
			tipText_Lb.setText(connectWaiting);
			break;
		case "overMaxQuantity":
			tipText_Lb.setText(overMaxQuantity);
			break;
		default:
			tipText_Lb.setText(tipText);
			break;
		}
	}

	public static void showTip(String tipText) {
		// TODO Auto-generated method stub
		seconde = 2;
		setTipTextLabelText(tipText);
		tipStage.show();
		closeTipAfterSecondSecond();
	}

	public static void closeTipAfterSecondSecond() {

		showTip = new Thread(new Runnable() {
			@SuppressWarnings({ "static-access", "deprecation" })
			@Override
			public void run() {
				Label tip2_Lb = (Label) StageInitShow.tipStageParent.lookup("#tip2_lb");
				// TODO Auto-generated method stub
				while (seconde != -1) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							tip2_Lb.setText("该窗口将在" + seconde + "秒后关闭!");
							seconde--;
							if (seconde == -1) {
								tipStage.close();
							}
						}
					});
					try {
						showTip.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (seconde == -1) {
						showTip.stop();
					}
				}
			}
		});
		showTip.start();
	}
}
