package cn.edu.scau.cmi.utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageInitShow extends Stage {
	public static Parent loginStageParent;
	public static Parent storeHouseManagerStageParent;
	public static Parent selectSupplierStageParent;
	public static Parent tipStageParent;
	public static Parent checkMedicinePriceStageParent;
	public static Parent newMedicineStageParent;
	public static Parent medicineTakenStageParent;
	public static Parent medicineStoreStageParent;
//	新建功能界面：药品上架功能，20180202，by梁早清
	public static Parent medicinePutawayParent;

	/*
	 * 根据菜单等界面的情况，选择不同的Stage
	 */
	public StageInitShow(String stageType) {
		switch (stageType) {
		case "MedicinePutawayStage":
		{
			medicinePutawayParent = StageBindScene(stageType);
			break;
		}
		case "CheckMedicinePriceStage":
			checkMedicinePriceStageParent = StageBindScene(stageType);
			break;
		case "NewMedicineStage":
			newMedicineStageParent = StageBindScene(stageType);
			break;
		case "MedicineTakenStage":
			medicineTakenStageParent = StageBindScene(stageType);
			break;
		case "MedicineStoreStage":
			medicineStoreStageParent = StageBindScene(stageType);
			break;
		case "LoginStage":
			loginStageParent = StageBindScene(stageType);
			break;
		case "TipStage":
			tipStageParent = StageBindScene(stageType);
			break;
		case "NewSupplierStage":
			StageBindScene(stageType);
			break;
		case "SelectSupplierStage":
			selectSupplierStageParent = StageBindScene(stageType);
			break;
		case "StoreHouseManagerStage":
			storeHouseManagerStageParent = StageBindScene(stageType);
			break;
		case "MenuStage":
			StageBindScene(stageType);
			break;
		default:
			System.out.println("Stage命名错误！！！");
			break;
		}
	}

	/*
	 * 绑定Scence
	 */
	public Parent StageBindScene(String stageType) {
		String s = "/cn/edu/scau/cmi/javafx/stage/layout/" + stageType + ".fxml";
		URL url = getClass().getResource(s);
		Parent root = null;
		try {
			root = FXMLLoader.load(url);
			Scene scene = new Scene(root);
			this.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("FXML文件有错");
		}
		return root;

	}
}
