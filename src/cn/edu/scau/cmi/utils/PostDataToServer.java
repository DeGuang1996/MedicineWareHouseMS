package cn.edu.scau.cmi.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import cn.edu.scau.cmi.domain.InternetMedicineInfo;
import cn.edu.scau.cmi.domain.MedicineBean;
import cn.edu.scau.cmi.domain.MedicineListOfSupplierBean;
import cn.edu.scau.cmi.domain.MedicineSupplierBean;
import cn.edu.scau.cmi.domain.MedicineListOfEmployeeTaskBean;
import cn.edu.scau.cmi.domain.SupplierBean;
import cn.edu.scau.cmi.javafx.stage.action.LoginStageAction;

public class PostDataToServer {
	static HttpClient httpClient = GetDataFromServer.httpClient;
	static String host = GetDataFromServer.host;

	public static  String PostDataToServers(String url, String data) {
		String result = "failed";
		HttpPost httpPost = new HttpPost(host + "/pigRs/" + url);
		httpPost.setHeader("Content-Type", "application/json");
		HttpEntity httpEntity = new StringEntity(data, "UTF-8");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(httpEntity);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean postPutawayData(String jsonData) {
		if(PostDataToServers("savePutawayData/"+LoginStageAction.pharmacistId, jsonData).equals("success")) {
			return true;
		}else {
			return false;
		}
	}
	public static boolean postMedicineTokenData(String jsonData,int takerId) {
		if(PostDataToServers("saveMedicineTokenData/"+takerId+"/"+LoginStageAction.pharmacistId, jsonData).equals("success")) {
			return true;
		}else {
			return false;
		}
	}
	public static boolean postNewMedicineData(MedicineBean newMedicine) {
		String JsonData = JsonUtils.DataToJson(newMedicine);
		String result = PostDataToServer.PostDataToServers("saveNewMedicine", JsonData);
		if (result.equals("success"))
			return true;
		return false;
	}

	public static boolean postMedcineTakenData(ArrayList<MedicineListOfEmployeeTaskBean> MedicineTakenDataList) {
		String data = JsonUtils.DataListToJson(MedicineTakenDataList);
		String result = PostDataToServer.PostDataToServers("saveMedicineTakeAndTakenDetail", data);
		if (result.equals("success"))
			return true;
		return false;
	}

	
	public static boolean postMedicineStorageData(ArrayList<MedicineListOfSupplierBean> medicinePriceList) {
		String data = JsonUtils.DataListToJson(medicinePriceList);
		String result = PostDataToServer.PostDataToServers("saveMedicineStorageAndDetail", data);
		if (result.equals("success"))
			return true;
		return false;
	}

	public static boolean postMedicineSupplierData(int medicineId, int supplierId) {
		MedicineBean medicine = new MedicineBean();
		medicine.setId(medicineId);
		SupplierBean supplier = new SupplierBean();
		supplier.setId(supplierId);
		MedicineSupplierBean medicineSupplier = new MedicineSupplierBean();
		medicineSupplier.setMedicine(medicine);
		medicineSupplier.setSupplier(supplier);
		String JsonData = JsonUtils.DataToJson(medicineSupplier);
		String result = PostDataToServer.PostDataToServers("saveMedicineSupplier", JsonData);
		if (result.equals("success"))
			return true;
		return false;
	}

	public static boolean postNewSupplierData(String supplierName) {
		SupplierBean supplierBean = new SupplierBean();
		supplierBean.setName(supplierName);
		String jsonData = JsonUtils.DataToJson(supplierBean);
		String result = PostDataToServer.PostDataToServers("saveNewSupplier", jsonData);
		if (result.equals("success"))
			return true;
		return false;
	}

	public static boolean postUpdateMedicine(MedicineBean medicine) {
		String jsonData = JsonUtils.DataToJson(medicine);
		String result = PostDataToServer.PostDataToServers("updateMedicine", jsonData);
		if (result.equals("success")) {
			return true;
		}
		return false;
	}

	public static boolean postUpdateSupplier(SupplierBean suppplier) {
		String jsonData = JsonUtils.DataToJson(suppplier);
		String result = PostDataToServer.PostDataToServers("updateSupplier", jsonData);
		if (result.equals("success")) {
			return true;
		}
		return false;
	}

	public static MedicineBean postZSMtoIVDC(String zsm) {
		CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost post = new HttpPost("http://1.202.134.237:8081/SyZs/commons/public/getZsmInfo.do?isRes=true");
		post.setHeader("Cookies", cookieStore.getCookies().toString());
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("zsm", zsm.substring(0, 24)));
		try {
			post.setEntity(new UrlEncodedFormEntity(qparams, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean unConnected = true;
		HttpResponse response;
		String medicineInfo = "";
		String number = "";
		int count = 0;
		while (unConnected) {
			try {
				System.out.println(count);
				response = httpClient.execute(post);
				HttpEntity entity = response.getEntity();
				medicineInfo = EntityUtils.toString(entity);
				unConnected = false;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				count++;
				if (count < 1)
					continue;
				else
					break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				count++;
				if (count < 1)
					continue;
				else
					break;
			}
		}
		InternetMedicineInfo i = new Gson().fromJson(medicineInfo, InternetMedicineInfo.class);
		MedicineBean medicine = new MedicineBean();
		try {
			medicine.setName(i.getRows().get(0).getCpname());
			medicine.setCompany(i.getRows().get(0).getQyname());
			number = i.getRows().get(0).getPzwh();
			String[] s1 = number.split("\\D");
			number = "";
			for (String s2 : s1)
				number += s2;
			medicine.setNumber(number);
			String phone = zsm.substring(24 + number.length());
			medicine.setPhone(phone);
			medicine.setQuantityPerCase(0);
			medicine.setQuantityPerBox(0);
			medicine.setMemo("null");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return medicine;
	}
}
