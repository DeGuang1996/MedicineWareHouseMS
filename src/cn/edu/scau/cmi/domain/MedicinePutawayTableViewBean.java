package cn.edu.scau.cmi.domain;

import java.util.ArrayList;

/*
 * 
 * 药品上架界面TableView的数据绑定类
 * */

public class MedicinePutawayTableViewBean {
//	要了解这样的一些问题：进库的箱box；箱中的瓶或者盒；上架的操作是扫描一下箱子的包装，然后依次扫描瓶或者盒子的包装，最后提交
//	业务流程：查找到箱后，后面扫描的所有的都是这一箱的药品
//	箱子数组，引用的是MedicineStorageDetail
	private  ArrayList<MedicineBean> medicineCaseArrayList; 
//	箱子里面的盒子或者瓶子
	private  ArrayList<MedicineBean> medicineBoxCaseArrayList;
	
	
}
