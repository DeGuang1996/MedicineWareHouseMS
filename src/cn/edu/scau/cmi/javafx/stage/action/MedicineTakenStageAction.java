package cn.edu.scau.cmi.javafx.stage.action;

/*
 * 药品领取界面操作
 * */
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.CachedResultSetMetaData;

import cn.edu.scau.cmi.domain.LoginStageBean;
import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.MedicineTakingBean;
import cn.edu.scau.cmi.domain.MedicineListOfEmployeeTaskBean;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MedicineTakenStageAction {
	public static Stage medicineTakenStage = new StageInitShow("MedicineTakenStage");
	static int employeeId = -1;// 作用有二，一是等于-1时，扫描药品无效，二是提交数据时使用。

	// 当前登录的管理员id
	static int pharmacistId = LoginStageAction.pharmacistId;// 药品管理员id
	// 界面的操作员显示框;
	static TextField employeeName_TF = (TextField) StageInitShow.medicineTakenStageParent.lookup("#employeeName_TF");
	static TextField dataInput_TF = (TextField) StageInitShow.medicineTakenStageParent.lookup("#dataInput_TF");// 界面的数据输入框
	@SuppressWarnings("unchecked")
	// 界面的TableView
	static TableView<MedicineTakingBean> tableView = (TableView<MedicineTakingBean>) StageInitShow.medicineTakenStageParent
			.lookup("#tableView");
	// 绑定TableView的ObservableList
	static ObservableList<MedicineTakingBean> taskMedicineList ;
	static Timeline animaction_inputRequestFocus;
	 static List<List<MedicineTakingBean>> medicineList;
	 static boolean isFirstTime;
	 //缓存扫描过的箱子
	 static List<MedicineCase> medicineCaseList;
	/*
	 * 初始化领取药品界面操作
	 */
	public static void initStageAction() {
		isFirstTime = true;
		medicineCaseList = new ArrayList<>();
		medicineList = new ArrayList<>();
		taskMedicineList = FXCollections.observableArrayList();
		medicineTakenStage.setTitle("药品领取");
		// 箱子还在，有可能使用了一些，箱子里面的药品还没有和箱子建立联系
//		DataObject.initDataList("caseTraceCodeList", 0);
		LoginStageAction.menuStage.close();// 关闭登陆界面
		initTableView();
		medicineTakenStage.show();
		dataInputFocus();// 固定焦点在数据输入框
		medicineTakenStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				animaction_inputRequestFocus.stop();
			}
		});
	}

	private static void removeMedicineCase(String traceCode) {
		MedicineCase mc = null;
		for(MedicineCase medicineCase: medicineCaseList) {
			if(medicineCase.getCaseTracecode().equals(traceCode)) {
				mc = medicineCase;
				break;
			}
		}
		medicineCaseList.remove(mc);
	}
	private static void addToMedicineCase(MedicineCase medicineCase) {
		for(MedicineCase mc: medicineCaseList) {
			if(mc.getNumber().equals(medicineCase.getNumber())) {
				TipAction.showTip("不允许出现相同类型的两个箱子");
				return ;
			}
		}
		medicineCaseList.add(medicineCase);
		
	}
	private static String lastTraceCode = null;
	/* 处理这个stage界面的tf的输入，扫描枪、键盘，当tx回车后处理 */
	public static void dataScanner(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String data = dataInput_TF.getText().replaceAll("\\D", "");
			dataInput_TF.setText("");
			//输入的是手机号
			if (data.length()==11) {
				if(employeeName_TF.getText().length()!=0) {
					TipAction.showTip("已有领取人");
					return ;
				}
				if(findAndSetEmployeeId(data)){
					initTaskMedicineToTableView();
					return ;
				}else {
					TipAction.showTip("员工不存在");
					return ;
				}
			} 
			if(data.length()<24){
				TipAction.showTip("输入有误");
				return ;
			}
			if(employeeName_TF.getText().length()==0) {
				TipAction.showTip("请先扫描员工");
				return ;
			}
			String traceCode = data.substring(0, 24);
			String numberAfter23 = data.substring(24);
			
			MedicineBean medicineBean = getMedicine(numberAfter23);
			if(medicineBean==null) {
				TipAction.showTip("药品未进库");
				return ;
			}
			if(GetDataFromServer.checkCase(traceCode)) {
				//两次扫描箱子
				if(traceCode.equals(lastTraceCode)) {
					MedicineTakingBean mtb = new MedicineTakingBean();
					mtb.setCaseTraceCode(traceCode);
					mtb.setMedicineName(medicineBean.getName());
					mtb.setMedicineId(medicineBean.getId());
					mtb.setBoxTraceCode(traceCode);
					mtb.setUnit("箱");
					addToMedicineList(mtb);
					refreshTableView();
					removeMedicineCase(traceCode);
					lastTraceCode=null;
				}else {
					lastTraceCode = traceCode;
					if(isCaseTraceCodeDuplicated(traceCode)) {
						return ;
					}
					MedicineCase medicineCase = new MedicineCase();
					medicineCase.setCaseTracecode(traceCode);
					medicineCase.setNumber(medicineBean.getNumber());
					addToMedicineCase(medicineCase);
				}
			}else {
				if(isBoxTraceCodeDuplicated(traceCode)) {
					return ;
				}
				MedicineCase medicineCase = new MedicineCase();
				medicineCase.setCaseTracecode(traceCode);
				
				MedicineTakingBean mtb = new MedicineTakingBean();
				int id = GetDataFromServer.getPutawayBoxId(traceCode);
				if(id==-1) {
					String caseTraceCode = findScannedCaseTraceCode(medicineBean.getNumber());
					if(caseTraceCode==null) {
						TipAction.showTip("请先扫描箱子/箱子未进库");
						return ;
					}
					mtb.setCaseTraceCode(caseTraceCode);
					mtb.setBoxTraceCode(traceCode);
					mtb.setMedicineId(medicineBean.getId());
					mtb.setMedicineName(medicineBean.getName());
				}else if(id==0) {
					TipAction.showTip("药品已被领取");
					return ;
				}else{
					mtb.setPutawayId(id);
					mtb.setBoxTraceCode(traceCode);
					mtb.setMedicineName(medicineBean.getName());
				}
				addToMedicineList(mtb);
				refreshTableView();
			}
		}
	}
	
	public static String findScannedCaseTraceCode(String number) {
		for(MedicineCase mc : medicineCaseList) {
			if(mc.getNumber().equals(number)) {
				return mc.getCaseTracecode();
			}
		}
		return null;
	}
	
	public static void addToMedicineList(MedicineTakingBean mtb) {
		List<MedicineTakingBean> mList = null;
		for(List<MedicineTakingBean> list : medicineList) {
			if(list.get(0).getMedicineName().equals(mtb.getMedicineName())) {
				mList = list;
			}
		}
		if(mList!=null) {
			if(mList.get(0).getTakenQuantity()==mList.get(0).getRequestQuantity()&&isFirstTime) {
				TipAction.showTip("药品领取已超额");
				isFirstTime = false;
				return ;
			}
			mtb.setNum(mList.size());
			if(mtb.getUnit()==null) {
				mtb.setUnit(mList.get(0).getUnit());
			}
			mList.add(mtb);
			mList.get(0).setTakenQuantity(mList.get(0).getTakenQuantity()+1);
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
	private static boolean isBoxTraceCodeDuplicated(String boxTracecode) {
		for(MedicineTakingBean mtb : taskMedicineList) {
			if(boxTracecode.equals(mtb.getBoxTraceCode())) {
				return true;
			}
		}
		return false;
	}
	private static boolean isCaseTraceCodeDuplicated(String caseTracecode) {
		for(MedicineCase mc : medicineCaseList) {
			if(caseTracecode.equals(mc.getCaseTracecode())) {
				return true;
			}
		}
		return false;
	}
	/*
	 * 焦点固定方法
	 */
	private static void dataInputFocus() {
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

	/*
	 * 第一步：查找和设置employeeId 设置了员工id之后才可以初始化任务列表
	 */
	private static boolean findAndSetEmployeeId(String data) {
		for (LoginStageBean employeeBean : DataObject.getLoginStageBeanList()) {
			String number = employeeBean.getEmployeeNumber();
			if (data.contains(number)) {
				employeeId = employeeBean.getEmployeeId();
				employeeName_TF.setText(employeeBean.getEmployeeName());
				return true;
			}
		}
		return false;
	}

	/*
	 * 初始化员工的任务列表
	 */
	private static void initTaskMedicineToTableView() {
		DataObject.initDataList("medicineTakenStageTaskList", employeeId);// 根据employeeId初始化员工任务的药品列表
		if(DataObject.getMedicineTakenStageTaskList().size()==0) {
			TipAction.showTip("该员工无需领取药品");
			employeeName_TF.setText("");
			employeeId = -1;
			return ;
		}
		for(MedicineTakingBean mtb : DataObject.getMedicineTakenStageTaskList()) {
			List<MedicineTakingBean> mtbList = new ArrayList<>();
			mtbList.add(mtb);
			medicineList.add(mtbList);
		}
		refreshTableView();
	}
	
	private static void refreshTableView() {
		taskMedicineList.clear();
		for(List<MedicineTakingBean> mtbList : medicineList) {
			taskMedicineList.addAll(mtbList);
		}
		tableView.setItems(taskMedicineList);
		tableView.refresh();
	}



	/*
	 * 初始化TableView的数据
	 */
	
	@SuppressWarnings("unchecked")
	private static void initTableView() {
		tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("taskType"));
		tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("num"));
		tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("medicineName"));
		tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("requestQuantity"));
		tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("takenQuantity"));
		tableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unit"));
		tableView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("boxTraceCode"));
		((TableColumn<MedicineTakingBean,String>)(tableView.getColumns().get(7))).setCellFactory((col) -> {
            TableCell<MedicineTakingBean, String> cell = new TableCell<MedicineTakingBean, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (!empty) {
                        Button delete = new Button("撤回");
                        this.setGraphic(delete);
                        delete.setOnMouseClicked((me) -> {
                        	MedicineTakingBean mtb = taskMedicineList.get(this.getIndex());
                        	remove(mtb);
                        });
                    }
                }
            };
            return cell;
        });
		tableView.setItems(taskMedicineList);
		tableView.refresh();
	}

	private static void  remove(MedicineTakingBean mtb) {
		if(mtb.getTaskType()!=null) {
			return;
		}
		List<MedicineTakingBean> mList = null;
		for(List<MedicineTakingBean> list : medicineList) {
			list.get(0).getMedicineName().equals(mtb.getMedicineName());
			mList = list;
			break;
		}
		if(mList != null) {
			mList.remove(mtb);
			mList.get(0).setTakenQuantity(mList.get(0).getTakenQuantity()-1);
			for(int i=1 ; i<mList.size();i++) {
				mList.get(i).setNum(i);
			}
			refreshTableView();
		}
	}


	/*
	 * 数据提交
	 */
	public static void submit() {
		if(taskMedicineList.size()==medicineList.size()) {
			TipAction.showTip("暂无可提交数据");
			return ;
		}
		List<List<MedicineTakingBean>> submitList = new ArrayList<>();
		List<MedicineTakingBean> mList = new ArrayList<>();
		for(List<MedicineTakingBean> list : medicineList) {
			if(mList.size()==0) {
				mList.addAll(list);
			}else {
				if(mList.get(mList.size()-1).getTaskId() == list.get(0).getTaskId()) {
					List<MedicineTakingBean> tList = new ArrayList<>(list);
					tList.remove(0);
					mList.addAll(tList);
				}else {
					submitList.add(new ArrayList<MedicineTakingBean>(mList));
					mList.clear();
					mList.addAll(list);
				}
			}
		}
		if(mList.size()!=0) {
			submitList.add(mList);
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String data = gson.toJson(submitList);
		if(PostDataToServer.postMedicineTokenData(data, employeeId)) {
			TipAction.showTip("提交成功");
			clear();
		}else {
			TipAction.showTip("提交失败");
		}
	}

	public static void backToMenu() {
		clear();
		animaction_inputRequestFocus.stop();
		medicineTakenStage.close();
		
		LoginStageAction.menuStage.show();
	}
	/*
	 * 清空界面数据
	 */
	public static void clear() {
		medicineCaseList.clear();
		taskMedicineList.clear();
		medicineList.clear();
		tableView.refresh();
		employeeId =  -1;
		employeeName_TF.setText("");
	}


}
class MedicineCase{
	private String caseTracecode;
	private String number;
	public String getCaseTracecode() {
		return caseTracecode;
	}
	public void setCaseTracecode(String caseTracecode) {
		this.caseTracecode = caseTracecode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}