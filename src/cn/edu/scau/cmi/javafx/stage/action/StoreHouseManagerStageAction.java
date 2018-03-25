package cn.edu.scau.cmi.javafx.stage.action;

import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.SupplierBean;
import cn.edu.scau.cmi.utils.DataObject;
import cn.edu.scau.cmi.utils.PostDataToServer;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class StoreHouseManagerStageAction {
	public static Stage storeHouseManagerStage = new StageInitShow("StoreHouseManagerStage");
	@SuppressWarnings("unchecked")
	static ChoiceBox<String> choice = (ChoiceBox<String>) StageInitShow.storeHouseManagerStageParent.lookup("#choice");
	@SuppressWarnings("unchecked")
	static TableView<MedicineBean> medicineTableView = (TableView<MedicineBean>) StageInitShow.storeHouseManagerStageParent
			.lookup("#medicineTableView");
	@SuppressWarnings("unchecked")
	static TableView<SupplierBean> supplierTableView = (TableView<SupplierBean>) StageInitShow.storeHouseManagerStageParent
			.lookup("#supplierTableView");
	static ObservableList<MedicineBean> medicineList = FXCollections
			.observableArrayList(DataObject.getMedicineBeanList());
	static ObservableList<SupplierBean> supplierList = FXCollections
			.observableArrayList(DataObject.getSupplierBeanList());
	static Label input_lb = (Label) StageInitShow.storeHouseManagerStageParent.lookup("#input_lb");
	static TextField input_tf = (TextField) StageInitShow.storeHouseManagerStageParent.lookup("#input_tf");

	public static void initStageAction() {
		LoginStageAction.menuStage.close();
		DataObject.initDataList("medicineBeanList", 0);
		medicineList = FXCollections.observableArrayList(DataObject.getMedicineBeanList());
		storeHouseManagerStage.show();
		choice.setItems(FXCollections.observableArrayList("药品管理", "供应商管理"));
		setListen();
	}

	public static void setListen() {
		choice.getSelectionModel().selectedIndexProperty().addListener((ov, oldov, newov) -> {
			initTableView(newov.intValue());
		});
	}

	public static void initTableView(int sel) {
		if (sel == 0) {
			medicineTableViewInit();
			medicineTableView.setVisible(true);
			supplierTableView.setVisible(false);
			input_tf.setVisible(true);
			input_lb.setVisible(true);
		} else {
			supplierTableViewInit();
			input_tf.setVisible(false);
			input_lb.setVisible(false);
			medicineTableView.setVisible(false);
			supplierTableView.setVisible(true);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void medicineTableViewInit() {
		for (int i = 2; i < medicineTableView.getColumns().size(); i++) {
			medicineTableView.getColumns().get(i).setVisible(true);
		}
		medicineTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		medicineTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
		medicineTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("number"));
		medicineTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("company"));
		medicineTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("phone"));
		medicineTableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("quantityPerCase"));
		medicineTableView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("quantityPerBox"));
		medicineTableView.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("memo"));
		for (int i = 1; i < medicineTableView.getColumns().size(); i++) {
			if (i != 5 && i != 6) {
				TableColumn column = medicineTableView.getColumns().get(i);
				column.setCellFactory(medicineCallBackString());
			}
		}
		TableColumn column = medicineTableView.getColumns().get(5);
		column.setCellFactory(medicineCallBackInteger());
		TableColumn column2 = medicineTableView.getColumns().get(6);
		column2.setCellFactory(medicineCallBackInteger());
		medicineTableView.setItems(medicineList);
		medicineTableView.refresh();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void supplierTableViewInit() {
		for (int i = 2; i < supplierTableView.getColumns().size(); i++)
			supplierTableView.getColumns().get(i).setVisible(false);
		supplierTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		supplierTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn column = supplierTableView.getColumns().get(1);
		column.setCellFactory(supplierCallBackSting());
		supplierTableView.setItems(supplierList);
		supplierTableView.refresh();
	}

	@SuppressWarnings("rawtypes")
	public static Callback<TableColumn, TableCell> medicineCallBackString() {
		return new Callback<TableColumn, TableCell>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell call(TableColumn param) {
				// TODO Auto-generated method stub
				TextFieldTableCell tf = new TextFieldTableCell(new StringConverter<String>() {

					@Override
					public String toString(String object) {
						// TODO Auto-generated method stub
						return object;
					}

					@Override
					public String fromString(String string) {
						// TODO Auto-generated method stub
						int row = medicineTableView.getSelectionModel().getFocusedIndex();
						int column = medicineTableView.getEditingCell().getColumn();
						switch (column) {
						case 0:
							DataObject.getMedicineBeanList().get(row).setName(string);
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
							break;
						case 1:
							DataObject.getMedicineBeanList().get(row).setNumber(string);
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
							break;
						case 2:
							DataObject.getMedicineBeanList().get(row).setCompany(string);
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
							break;
						case 3:
							DataObject.getMedicineBeanList().get(row).setPhone(string);
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
							break;
						case 6:
							DataObject.getMedicineBeanList().get(row).setMemo(string);
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
							break;
						default:
							break;
						}
						return string;
					}
				});
				return tf;
			}
		};
	}

	@SuppressWarnings("rawtypes")
	public static Callback<TableColumn, TableCell> medicineCallBackInteger() {
		return new Callback<TableColumn, TableCell>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell call(TableColumn param) {
				// TODO Auto-generated method stub
				TextFieldTableCell tf = new TextFieldTableCell(new StringConverter<Integer>() {

					@Override
					public String toString(Integer object) {
						// TODO Auto-generated method stub
						return object.toString();
					}

					@Override
					public Integer fromString(String string) {
						// TODO Auto-generated method stub
						int row = medicineTableView.getSelectionModel().getFocusedIndex();
						int column = medicineTableView.getEditingCell().getColumn();
						if (column == 4) {
							DataObject.getMedicineBeanList().get(row).setQuantityPerCase(Integer.valueOf(string));
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
						} else if (column == 5) {
							DataObject.getMedicineBeanList().get(row).setQuantityPerBox(Integer.valueOf(string));
							PostDataToServer.postUpdateMedicine(DataObject.getMedicineBeanList().get(row));
						}

						return Integer.valueOf(string);
					}
				});
				return tf;
			}
		};
	}

	@SuppressWarnings("rawtypes")
	public static Callback<TableColumn, TableCell> supplierCallBackSting() {
		return new Callback<TableColumn, TableCell>() {
			@SuppressWarnings("unchecked")
			@Override
			public TableCell call(TableColumn param) {
				// TODO Auto-generated method stub
				TextFieldTableCell tf = new TextFieldTableCell(new StringConverter<String>() {

					@Override
					public String toString(String object) {
						// TODO Auto-generated method stub
						return object;
					}

					@Override
					public String fromString(String string) {
						// TODO Auto-generated method stub
						int row = supplierTableView.getSelectionModel().getFocusedIndex();
						DataObject.getSupplierBeanList().get(row).setName(string);
						PostDataToServer.postUpdateSupplier(DataObject.getSupplierBeanList().get(row));
						return string;
					}
				});
				return tf;
			}
		};
	}

	public static void add(String data) {
		try {
			String s = data.substring(0, 24);
			if (medicineTableView.isVisible()) {
				NewMedicineStageAction.initStage(s);
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (medicineTableView.isVisible()) {
				NewMedicineStageAction.initStage("");
			} else {
				NewSupplierAction.init();
			}
		}

	}

	public static void back() {
		storeHouseManagerStage.close();
		LoginStageAction.menuStage.show();
	}
}
