package cn.edu.scau.cmi.javafx.stage.action;

import java.util.ArrayList;
import java.util.Stack;
import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.MedicineListOfSupplierBean;
import cn.edu.scau.cmi.domain.MedicineStoreTableViewBean;
import cn.edu.scau.cmi.utils.DataObject;
import cn.edu.scau.cmi.utils.GetDataFromServer;
import cn.edu.scau.cmi.utils.PostDataToServer;
import cn.edu.scau.cmi.utils.StageInitShow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/*
 * 
 * */
public class MedicineStoreStageAction {
	
	public static Stage medicineStoreStage = new StageInitShow("MedicineStoreStage");
	
	//药品入库视图表
	static TableView<MedicineStoreTableViewBean> tableView;
	//药品入库视图表的数据
	static ObservableList<MedicineStoreTableViewBean> medicineList = FXCollections.observableArrayList();
	
	static int supplierId = -1;
	static int num = 1;
	
	//界面节点元素，供应商标签、入库操作员标签、数据输入文本框
	static Label supplier_lb = (Label) StageInitShow.medicineStoreStageParent.lookup("#supplier_lb");
	static Label pharmacist_lb = (Label) StageInitShow.medicineStoreStageParent.lookup("#pharmacist_lb");
	static TextField dataInput_TF = (TextField) StageInitShow.medicineStoreStageParent.lookup("#dataInput_TF");
	
	//没什么用处，注释掉
//	static String pharmacistName = LoginStageAction.pharmacistName;
	
	static Timeline animaction_inputRequestFocus;
	static Stack<String> dataStack = new Stack<String>();
	//入库的数据
	static ArrayList<MedicineListOfSupplierBean> storeList = new ArrayList<>();

	public static void initStageAction() {
		LoginStageAction.menuStage.close();
		medicineStoreStage.setTitle("药品进库");
		medicineStoreStage.show();
		pharmacist_lb.setText(LoginStageAction.pharmacistName);
		//缓存药品和供应商数据
		DataObject.initDataList("supplierBeanList", 0);
		DataObject.initDataList("medicineBeanList", 0);
		
		//确保光标一直处在输入框
		dataInputFocus();
		//初始化进库表格
		initTableView();
		//显示选择供应商的界面
		SelectSupplierStageAction.init();
		
		setStageCloseListener();
	}

	@SuppressWarnings("unchecked")
	public static void initTableView() {
		tableView = (TableView<MedicineStoreTableViewBean>) StageInitShow.medicineStoreStageParent.lookup("#tableView");
		tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("num"));
		tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("medicineName"));
		tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("company"));
		tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
		tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unit"));
		tableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("price"));
		tableView.setItems(medicineList);
		tableView.refresh();
	}

	public static void dataScanner(KeyEvent keyEvent) {
		// 如果还没有确定供应商，就弹出供应商选择界面，否则就药品进库
		if (keyEvent.getCode() == KeyCode.ENTER) {
			if (supplierId == -1) {
				// 没有供应商，选择供应商
				SelectSupplierStageAction.init();
			} else {
				findMedicineAndAddQuantityToList();
			}
			dataInput_TF.setText("");
		}
	}

	public static void dataInputFocus() {
		animaction_inputRequestFocus = new Timeline(
				new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						dataInput_TF.requestFocus();
					}
				}));
		animaction_inputRequestFocus.setCycleCount(Timeline.INDEFINITE);
		animaction_inputRequestFocus.play();
	}

	public static boolean isFindOutMedicineInTableView(String caseTraceCode, String after24Number) {
		for (MedicineStoreTableViewBean m : medicineList) {
			String number = m.getMedicineNum();
			int index = after24Number.indexOf(number);
			if (index < 5 && index >= 0) {
				m.setQuantity(m.getQuantity() + 1);
				addToStoreList(caseTraceCode, m.getMedicineId(), m.getPrice());
				return true;
			}
		}
		return false;
	}

	public static boolean isFindInMedicinePriceBeanList(String caseTraceCode, String after24Number) {
		for (MedicineListOfSupplierBean medicinePriceBean : DataObject.getMedicinePriceList()) {
			String number = medicinePriceBean.getMedicineNum();
			if (number != null) {
				int index = after24Number.indexOf(number);
				if (index < 5 && index >= 0) {
					MedicineStoreTableViewBean m = new MedicineStoreTableViewBean();
					m.setMedicineId(medicinePriceBean.getMedicineId());
					m.setCompany(medicinePriceBean.getCompany());
					m.setMedicineName(medicinePriceBean.getMedicineName());
					m.setMedicineNum(medicinePriceBean.getMedicineNum());
					m.setPrice(medicinePriceBean.getPrice());
					m.setUnit(medicinePriceBean.getUnit());
					m.setQuantity(1);
					m.setMedicineId(medicinePriceBean.getMedicineId());
					m.setNum(num);
					medicineList.add(m);
					addToStoreList(caseTraceCode, medicinePriceBean.getMedicineId(), m.getPrice());
					num++;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isFindInMedicineList(String caseTraceCode, String after24Number) {
		for (MedicineBean medicine : DataObject.getMedicineBeanList()) {
			String number = medicine.getNumber();
			if (number != null) {
				int index = after24Number.indexOf(number);
				if (index < 5 && index >= 0) {
					// 閸氾拷
					PostDataToServer.postMedicineSupplierData(medicine.getId(), supplierId);
					MedicineStoreTableViewBean m = new MedicineStoreTableViewBean();
					m.setMedicineId(medicine.getId());
					m.setCompany(medicine.getCompany());
					m.setMedicineId(medicine.getId());
					m.setMedicineName(medicine.getName());
					m.setMedicineNum(medicine.getNumber());
					m.setNum(num);
					m.setPrice(0);
					m.setQuantity(1);
					m.setUnit("箱");
					medicineList.add(m);
					num++;
					addToStoreList(caseTraceCode, medicine.getId(), m.getPrice());
					initMedicinePriceList();
					return true;
				}
			}
		}
		return false;
	}

	// 
	public static void findAndSetSupplier() {
		SelectSupplierStageAction.init();
	}

	public static void submit() {
		if(storeList!=null&&storeList.size()==0) {
			TipAction.showTip("暂无可提交数据");
			return ;
		}
		CheckMedicinePriceStageAction.initStage();
	}

	// 
	public static void initMedicinePriceList() {
		DataObject.initDataList("medicinePriceList", supplierId);
	}

	public static void cleanList() {
		supplierId = -1;
		supplier_lb.setText("");
		medicineList.clear();
		tableView.refresh();
		num = 1;
		storeList.clear();
		dataInput_TF.setText("");
	}

	/*
	 *
	 */

	public static void close() {
		animaction_inputRequestFocus.stop();
		if (SelectSupplierStageAction.isSelectSupplierStageOpen) {
			SelectSupplierStageAction.selectSupplierStage.close();
			SelectSupplierStageAction.isSelectSupplierStageOpen = false;
		}
		medicineStoreStage.close();
	}

	public static void findMedicineAndAddQuantityToList() {
		String[] data2 = dataInput_TF.getText().split("\\D");
		String data3 = "";
		for (String s : data2)
			data3 += s;
		// 以防扫描数据丢失，将扫描数据入栈
		dataStack.add(data3);
		// 当栈不空时，依次处理栈里面的数据
		while (!dataStack.isEmpty()) {
			try {
				String after24Number = "";
				String caseTraceCode;
				String data = dataStack.pop();

				if (data.length() < 24) {
					after24Number = data;
					caseTraceCode = "null";
				} else {
					caseTraceCode = data.substring(0, 24);
					after24Number = data.substring(24);
				}
				if(GetDataFromServer.checkCase(caseTraceCode)) {
					TipAction.showTip("药品已经进库");
					return ;
				}
				for(MedicineListOfSupplierBean s:storeList) {
					if(s.getCaseTraceCode().equals(caseTraceCode)) {
						return ;
					}
				}
				boolean isFind = isFindOutMedicineInTableView(caseTraceCode, after24Number);
				if (!isFind)
					isFind = isFindInMedicinePriceBeanList(caseTraceCode, after24Number);
				if (!isFind)
					isFind = isFindInMedicineList(caseTraceCode, after24Number);
				if (!isFind) {
					dataInput_TF.setText("");
					NewMedicineStageAction.initStage(data);
				}
			} catch (Exception e) {
				// TODO: handle exception
				dataInput_TF.setText("");
			}
			tableView.refresh();

		}
	}

	public static void addToStoreList(String caseTraceCode, int medicineId, float price) {
		MedicineListOfSupplierBean m = new MedicineListOfSupplierBean();
		m.setCaseTraceCode(caseTraceCode);
		m.setIsBox(1);
		m.setPharmaciseId(LoginStageAction.pharmacistId);
		m.setMedicineId(medicineId);
		m.setSupplierId(supplierId);
		m.setPrice(price);
		storeList.add(m);
	}

	private static void setStageCloseListener() {
		// 关闭入库界面时，处理善后工作
		medicineStoreStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (NewMedicineStageAction.newMedicineStageIsOpen) {
					NewMedicineStageAction.newMedicineStage.close();
					NewMedicineStageAction.newMedicineStageIsOpen = false;
				}
				if (CheckMedicinePriceStageAction.checkMedicinePriceStageIsOpen) {
					CheckMedicinePriceStageAction.checkMedicinePriceStage.close();
					CheckMedicinePriceStageAction.checkMedicinePriceStageIsOpen = false;
				}
				if (SelectSupplierStageAction.isSelectSupplierStageOpen) {
					SelectSupplierStageAction.selectSupplierStage.close();
					SelectSupplierStageAction.isSelectSupplierStageOpen = false;
				}
			}
		});
	}
}
