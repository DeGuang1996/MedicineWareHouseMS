package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.domain.SupplierBean;
import cn.edu.scau.cmi.utils.DataObject;
import cn.edu.scau.cmi.utils.PostDataToServer;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class NewSupplierAction {
	public static Stage newSupplierStage;

	public static void init() {
		newSupplierStage = new StageInitShow("NewSupplierStage");
		newSupplierStage.setTitle("新增供应商");
		newSupplierStage.show();
	}

	public static void storeStageSubmit(String supplierName) {
		PostDataToServer.postNewSupplierData(supplierName);
		DataObject.initDataList("supplierBeanList", 0);
		SelectSupplierStageAction.supplierList = FXCollections.observableArrayList(DataObject.getSupplierBeanList());
		SelectSupplierStageAction.supplierTableView.setItems(SelectSupplierStageAction.supplierList);
		SelectSupplierStageAction.supplierTableView.refresh();
		newSupplierStage.close();
	}

	public static void managerStageSubmit(String supplierName) {
		PostDataToServer.postNewSupplierData(supplierName);
		DataObject.initDataList("supplierBeanList", 0);
		SupplierBean supplier = DataObject.getSupplierBeanList().get(DataObject.getSupplierBeanList().size() - 1);
		StoreHouseManagerStageAction.supplierList.add(supplier);
		newSupplierStage.close();
	}
}
