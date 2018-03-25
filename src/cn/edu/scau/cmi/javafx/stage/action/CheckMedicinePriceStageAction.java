package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.domain.MedicineListOfSupplierBean;
import cn.edu.scau.cmi.domain.MedicineStoreTableViewBean;
import cn.edu.scau.cmi.utils.PostDataToServer;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/*
 * 核对药品价格的界面操作
 * */
public class CheckMedicinePriceStageAction {

	public static Stage checkMedicinePriceStage = new StageInitShow("CheckMedicinePriceStage");
	@SuppressWarnings("unchecked")
	static TableView<MedicineStoreTableViewBean> tableView = (TableView<MedicineStoreTableViewBean>) StageInitShow.checkMedicinePriceStageParent
			.lookup("#tableView");; // 表格
	static boolean checkMedicinePriceStageIsOpen = false; // 默认界面未开启

	public static void initStage() {
		checkMedicinePriceStage.setTitle("核对价格");
		checkMedicinePriceStage.show(); // 显示核对药品界面
		initTableView(); // 初始化TableView，操作是绑定进库的药品列表
		checkMedicinePriceStageIsOpen = true; // 核对药品界面开启
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void initTableView() {
		tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("num"));
		tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("medicineName"));
		tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
		TableColumn priceColumn = tableView.getColumns().get(2);
		priceColumn.setCellFactory(callBack());
		tableView.setItems(MedicineStoreStageAction.medicineList);
		tableView.refresh();
	}

	/*
	 * 提交数据
	 */
	public static void submit() {
		boolean result = PostDataToServer.postMedicineStorageData(MedicineStoreStageAction.storeList);
		if (result) {
			TipAction.showTip("submitSuccess");
		} else
			TipAction.showTip("submitFailed");
		MedicineStoreStageAction.cleanList();
		checkMedicinePriceStage.close();
	}

	/*
	 * 单元格不能修改，通过该方法，修改数据，重写单元格？？？？
	 */
	@SuppressWarnings("rawtypes")
	public static Callback<TableColumn, TableCell> callBack() {
		return new Callback<TableColumn, TableCell>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell<MedicineStoreTableViewBean, Double> call(TableColumn param) {
				TextFieldTableCell tf = new TextFieldTableCell(new StringConverter<Float>() {
					@Override
					public String toString(Float object) {
						return object.toString();
					}

					@Override
					public Float fromString(String string) {
						tableView.getSelectionModel().getSelectedItem().setPrice(Float.valueOf(string));
						MedicineStoreTableViewBean m = tableView.getSelectionModel().getSelectedItem();
						setPrice(m.getMedicineId(), Float.valueOf(string));
						return Float.valueOf(string);
					}
				});
				return tf;
			}
		};
	}

	public static void setPrice(int medicineId, float price) {
		for (MedicineListOfSupplierBean m : MedicineStoreStageAction.storeList) {
			if (m.getMedicineId() == medicineId) {
				m.setPrice(price);
			}
		}
	}
}
