package cn.edu.scau.cmi.utils;

import java.util.ArrayList;
import cn.edu.scau.cmi.domain.LoginStageBean;
import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.MedicineListOfSupplierBean;
import cn.edu.scau.cmi.domain.MedicineTakingBean;
import cn.edu.scau.cmi.domain.MedicineListOfEmployeeTaskBean;
import cn.edu.scau.cmi.domain.SupplierBean;

public class DataObject {
	private static ArrayList<MedicineBean> medicineBeanList;
	private static ArrayList<SupplierBean> supplierBeanList;
	private static ArrayList<MedicineListOfSupplierBean> medicinePriceList;
	private static ArrayList<LoginStageBean> loginStageBeanList;
	private static ArrayList<MedicineTakingBean> MedicineTakenStageTaskList;
	private static ArrayList<String> caseTraceCodeList;
	private static ArrayList<MedicineListOfEmployeeTaskBean> displayBoxTraceCodeList;

	// 获取数据，相当于懒加载的方式，dataListName代表定义的数据列表名称，urlData代表数据列表？？？？
	public static void initDataList(String dataListName, int urlData) {
		switch (dataListName) {
		case "medicineBeanList":
			setMedicineBeanList(GetDataFromServer.getDataListFromServer("MedicineList", MedicineBean.class));
			break;
		case "supplierBeanList":
			setSupplierBeanList(GetDataFromServer.getDataListFromServer("SupplierList", SupplierBean.class));
			break;
		case "medicinePriceList":
			setMedicinePriceList(GetDataFromServer.getDataListFromServer("getMedicinePriceBeanList/" + urlData,
					MedicineListOfSupplierBean.class));
			break;
		case "loginStageBeanList":
			setLoginStageBeanList(
					GetDataFromServer.getDataListFromServer("getLoginStageBeanList", LoginStageBean.class));
			break;
		case "medicineTakenStageTaskList":
			setMedicineTakenStageTaskList(GetDataFromServer.getDataListFromServer("getTakingMedicine/" + urlData,
					MedicineTakingBean.class));
			break;
		case "caseTraceCodeList":
			setCaseTraceCodeList(GetDataFromServer.getDataListFromServer("getCaseTraceCode", String.class));
			break;
		case "displayBoxTraceCode":
			setDisplayBoxTraceCodeList(GetDataFromServer.getDataListFromServer("getDisplayMedicine",
					MedicineListOfEmployeeTaskBean.class));
			break;
		default:
			System.out.println("dataListName ERROR");
			break;
		}
	}

	public static ArrayList<MedicineListOfSupplierBean> getMedicinePriceList() {
		return medicinePriceList;
	}

	public static void setMedicinePriceList(ArrayList<MedicineListOfSupplierBean> arrayList) {
		DataObject.medicinePriceList = arrayList;
	}

	public static ArrayList<LoginStageBean> getLoginStageBeanList() {
		return loginStageBeanList;
	}

	public static void setLoginStageBeanList(ArrayList<LoginStageBean> loginStageBeanList) {
		DataObject.loginStageBeanList = loginStageBeanList;
	}

	public static ArrayList<MedicineBean> getMedicineBeanList() {
		return medicineBeanList;
	}

	public static void setMedicineBeanList(ArrayList<MedicineBean> medicineBeanList) {
		DataObject.medicineBeanList = medicineBeanList;
	}

	public static ArrayList<SupplierBean> getSupplierBeanList() {
		return supplierBeanList;
	}

	public static void setSupplierBeanList(ArrayList<SupplierBean> supplierBeanList) {
		DataObject.supplierBeanList = supplierBeanList;
	}

	

	public static ArrayList<MedicineTakingBean> getMedicineTakenStageTaskList() {
		return MedicineTakenStageTaskList;
	}

	public static void setMedicineTakenStageTaskList(ArrayList<MedicineTakingBean> medicineTakenStageTaskList) {
		MedicineTakenStageTaskList = medicineTakenStageTaskList;
	}

	public static ArrayList<String> getCaseTraceCodeList() {
		return caseTraceCodeList;
	}

	public static void setCaseTraceCodeList(ArrayList<String> caseTraceCodeList) {
		DataObject.caseTraceCodeList = caseTraceCodeList;
	}

	public static ArrayList<MedicineListOfEmployeeTaskBean> getDisplayBoxTraceCodeList() {
		return displayBoxTraceCodeList;
	}

	public static void setDisplayBoxTraceCodeList(ArrayList<MedicineListOfEmployeeTaskBean> displayBoxTraceCodeList) {
		DataObject.displayBoxTraceCodeList = displayBoxTraceCodeList;
	}
}
