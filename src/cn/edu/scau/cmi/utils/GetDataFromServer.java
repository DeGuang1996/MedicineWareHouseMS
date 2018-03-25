package cn.edu.scau.cmi.utils;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.edu.scau.cmi.javafx.stage.action.TipAction;

public class GetDataFromServer {
	static HttpClient httpClient = HttpClients.createDefault();
	static String host = "http://120.78.190.240:8080";
//	static String host = "http://127.0.0.1:8080";

	/*
	 * 向服务器发送Get请求，返回请求响应结果
	 */
	public static String getDataFromServer(String url) {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			return EntityUtils.toString(httpEntity, "UTF-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			TipAction.showTip("serverNotLaunch");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TipAction.showTip("serverNotLaunch");
		}
		return null;
	}

	/*
	 * 从后台获取Json数据，转成java对象，返回List
	 */
	public static <E> ArrayList<E> getDataListFromServer(String dataListName, Class<E> cls) {
		dataListName = host + "/pigRs/" + dataListName;
		String dataJsonString = getDataFromServer(dataListName);
		try {
			ArrayList<E> dataList = JsonUtils.JsonToObejctList(dataJsonString, cls);
			return dataList;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static int getTakenBoxNum(String caseTraceCode) {
		return Integer.valueOf(getDataFromServer(host+"/pigRs/getTakenBoxNum/"+caseTraceCode));
	}
	public static int getPutawayBoxId(String boxTraceCode) {
		return Integer.valueOf(getDataFromServer(host+"/pigRs/getPutawayBoxId/"+boxTraceCode));
	}
	public static boolean checkCase(String caseTraceCode) {
		return Boolean.valueOf(getDataFromServer(host+"/pigRs/checkCase/"+caseTraceCode));
	}
}
