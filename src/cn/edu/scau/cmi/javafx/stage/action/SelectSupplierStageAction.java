package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.domain.SupplierBean;
import cn.edu.scau.cmi.utils.DataObject;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SelectSupplierStageAction {
	public static Stage selectSupplierStage = new StageInitShow("SelectSupplierStage");
	static ObservableList<SupplierBean> supplierList = FXCollections
			.observableArrayList(DataObject.getSupplierBeanList());
	@SuppressWarnings("unchecked")
	static TableView<SupplierBean> supplierTableView = (TableView<SupplierBean>) StageInitShow.selectSupplierStageParent
			.lookup("#tableView");
	static boolean isSelectSupplierStageOpen = false;

	public static void init() {
		selectSupplierStage.setTitle("供应商选择");
		selectSupplierStage.show();
		initTableView();
		isSelectSupplierStageOpen = true;
	}

	private static void initTableView() {
		supplierTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		supplierTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
		supplierTableView.setItems(supplierList);
	}

	public static void addSupplier() {
		NewSupplierAction.init();
	}

	public static void select() {
		SupplierBean supplier = supplierTableView.getSelectionModel().getSelectedItem();
		MedicineStoreStageAction.supplier_lb.setText(supplier.getName());
		MedicineStoreStageAction.supplierId = supplier.getId();
		DataObject.initDataList("medicinePriceList", supplier.getId());
		System.out.println(DataObject.getMedicinePriceList().size());
		selectSupplierStage.close();
		MedicineStoreStageAction.medicineStoreStage.requestFocus();
	}
}
