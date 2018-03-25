package cn.edu.scau.cmi.javafx.stage.action;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hombio.domain.PigHouse;

import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.MedicineListOfSupplierBean;
import cn.edu.scau.cmi.domain.MedicinePutawayBean;
import cn.edu.scau.cmi.domain.MedicineStoreTableViewBean;
import cn.edu.scau.cmi.javafx.stage.controller.MenuStageController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/*
 * 上架
 * */
public class MedicinePutawayStageAction {
	public static Stage medicineLPutawayStage = new StageInitShow("MedicinePutawayStage");
	static TableView<MedicinePutawayBean> tableView;
	static ObservableList<MedicinePutawayBean> putawayMedicineList; 
	static TextField putawayEmployee_TF = (TextField) StageInitShow.medicinePutawayParent.lookup("#employeeName_TF");
	static TextField dataInput_TF = (TextField) StageInitShow.medicinePutawayParent.lookup("#dataInput_TF");
	static Timeline animaction_inputRequestFocus;
	static List<List<MedicinePutawayBean>> caseList ;

	public static void initStageAction() {
		medicineLPutawayStage.setTitle("药品上架");
		LoginStageAction.menuStage.close();
		medicineLPutawayStage.show();
		putawayMedicineList = FXCollections.observableArrayList();
		caseList =  new ArrayList<>();
		putawayEmployee_TF.setText(LoginStageAction.pharmacistName);
		DataObject.initDataList("medicineBeanList", 0);
//		DataObject.initDataList("caseTraceCodeList", 0);
		initTableView();
		dataInputFocus();
		setStageCloseListener();
	}

	@SuppressWarnings("unchecked")
	public static void initTableView() {
		tableView = (TableView<MedicinePutawayBean>) StageInitShow.medicinePutawayParent.lookup("#tableView");
		tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("subId"));
		tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("traceCode"));
		tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("medicine"));
		tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("quantity"));
		tableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("rest"));
		((TableColumn<MedicinePutawayBean,String>)(tableView.getColumns().get(6))).setCellFactory((col) -> {
            TableCell<MedicinePutawayBean, String> cell = new TableCell<MedicinePutawayBean, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (!empty) {
                        Button delete = new Button("撤回");
                        this.setGraphic(delete);
                        delete.setOnMouseClicked((me) -> {
                        	MedicinePutawayBean mpb = putawayMedicineList.get(getIndex());
                        	deleteAndRefreshTableView(mpb);
                        });
                    }
                }
            };
            return cell;
        });
		tableView.setItems(putawayMedicineList);
		tableView.refresh();
	}
	private static void deleteAndRefreshTableView(MedicinePutawayBean mpb) {
		List<MedicinePutawayBean> mList = null;
		for(List<MedicinePutawayBean> list : caseList) {
			if(list.contains(mpb)) {
				mList = list;
				break;
			}
		}
		if(mList==null) {
			return ;
		}
		if(mList.indexOf(mpb)==0) {
			caseList.remove(mList);
			for(int i=0 ;i<caseList.size();i++) {
				caseList.get(i).get(0).setId(""+(i+1));
			}
		}else {
			mList.remove(mpb);
			mList.get(0).setRest(""+(Integer.valueOf(mList.get(0).getRest())+1));
			for(int i=1 ; i<mList.size();i++) {
				String[] str = mList.get(i).getSubId().split("-");
				String subId = str[0]+"-"+i;
				mList.get(i).setSubId(subId);
			}
		}
		refreshTableView();
	}
	private static  void showMessage(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.show();
	}
	public static boolean isPutawayed(String traceCode) {
		for( MedicinePutawayBean putawayedMedicine : putawayMedicineList) {
			if(putawayedMedicine.getTraceCode().equals(traceCode)) {
				return true;
			}
		}
		return false;
	}
	public static void addToCaseList(MedicinePutawayBean medicinePutawayBean) {
		for(List<MedicinePutawayBean> list : caseList) {
			if(list.get(0).getNumber().equals(medicinePutawayBean.getNumber())) {
				TipAction.showTip("不允许出现相同类型的两个箱子");
				return ;
			}
		}
		List<MedicinePutawayBean> list = new ArrayList<>();
		medicinePutawayBean.setId(""+(caseList.size()+1));
		list.add(medicinePutawayBean);
		caseList.add(list);
	}
	
	public static boolean addToCorrespondingCaseList(MedicinePutawayBean medicinePutawayBean) {
		List<MedicinePutawayBean> mCase = null;
		for(List<MedicinePutawayBean> list : caseList) {
			if(list.get(0).getNumber().equals(medicinePutawayBean.getNumber())) {
				mCase = list;
				break;
			}
		}
		if(mCase!=null) {
			if(mCase.get(0).getRest().equals("0")) {
				TipAction.showTip("该箱子余量已经为零");
				return true;
			}
			String subId = mCase.get(0).getId()+"-"+mCase.size();
			medicinePutawayBean.setSubId(subId);
			mCase.get(0).setRest(""+(Integer.valueOf(mCase.get(0).getRest())-1));
			mCase.add(medicinePutawayBean);
			return true;
		}else {
			return false;
		}
	}
	public static MedicineBean getMedicine(String numberAfter23) {
		for(MedicineBean medicineBean : DataObject.getMedicineBeanList()) {
			if(medicineBean.getNumber()==null) {
				continue;
			}
			int index = numberAfter23.indexOf(medicineBean.getNumber());
			if(index>=0&&index<=5) {
				return medicineBean;
			}
		}
		return null;
	}
	public static void refreshTableView() {
		putawayMedicineList.clear();
		for(List<MedicinePutawayBean> list : caseList) {
			putawayMedicineList.addAll(list);
		}
		tableView.setItems(putawayMedicineList);
		tableView.refresh();
	}
	
	public static void dataScanner(KeyEvent keyEvent) {
		if (keyEvent.getCode() != KeyCode.ENTER) {
			return ;
		}
		String data = dataInput_TF.getText();
		dataInput_TF.setText("");
		String formatData = data.replaceAll("\\D", "");
		
		if(formatData.length()<24) {
			TipAction.showTip("输入有误");
			return ;
		}
		String traceCode = formatData.substring(0, 24);
		String numberAfter23 = formatData.substring(24);
		//处理重复
		if(isPutawayed(traceCode)) {
			return ;
		}
		MedicinePutawayBean medicinePutawayBean = new MedicinePutawayBean();
		medicinePutawayBean.setTraceCode(traceCode);
		
		if(!GetDataFromServer.checkCase(traceCode)) {
			MedicineBean medicineBean = getMedicine(numberAfter23);
			if(medicineBean!=null) {
				medicinePutawayBean.setNumber(medicineBean.getNumber());
				medicinePutawayBean.setMedicine(medicineBean.getName());
				medicinePutawayBean.setQuantity(medicineBean.getQuantityPerBox()+"/...");
				if(!addToCorrespondingCaseList(medicinePutawayBean)) {
					TipAction.showTip("请先扫描箱子");
					return ;
				}
				refreshTableView();
			}else {
				TipAction.showTip("药品未进库");
				return ;
			}
		}else {
			MedicineBean medicineBean = getMedicine(numberAfter23);
			if(medicineBean!=null) {
				medicinePutawayBean.setNumber(medicineBean.getNumber());
				medicinePutawayBean.setMedicine(medicineBean.getName());
				medicinePutawayBean.setRest(""+(medicineBean.getQuantityPerCase()-GetDataFromServer.getTakenBoxNum(traceCode)));
				medicinePutawayBean.setQuantity(medicineBean.getQuantityPerCase()+"/箱");
				addToCaseList(medicinePutawayBean);
				refreshTableView();
			}else {
				TipAction.showTip("药品未进库");
				return ;
			}
		}
		
	}

	public static void dataInputFocus() {
		animaction_inputRequestFocus = new Timeline(
				new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						dataInput_TF.requestFocus();
					}
				}));
		animaction_inputRequestFocus.setCycleCount(Timeline.INDEFINITE);
		animaction_inputRequestFocus.play();
	}


	public static void backToMainMnu() {
		medicineLPutawayStage.close();
		caseList =null;
		putawayMedicineList =null;
		LoginStageAction.menuStage.show();
	}

	public static void clear() {
		caseList.clear();
		putawayMedicineList.clear();
		tableView.refresh();
	}
	
	public static void submit() {
		if(caseList.size()==0) {
			TipAction.showTip("暂无可提交数据");
			return;
		}
		for(List<MedicinePutawayBean> list : caseList) {
			if(list.size()==1) {
				String msg = "箱序号"+list.get(0).getId()+"为空";
				TipAction.showTip(msg);
				return;
			}
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonData = gson.toJson(caseList);
//		showMessage(jsonData);
		if(PostDataToServer.postPutawayData(jsonData)) {
			TipAction.showTip("提交成功");
			clear();
		}else {
			TipAction.showTip("提交失败");
		}
		
	}
	
	private static void setStageCloseListener() {
//		medicineLPutawayStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			@Override
//			public void handle(WindowEvent event) {
//				caseList =null;
//				putawayMedicineList =null;
//				LoginStageAction.menuStage.show();
//			}
//		});
	}
}
