package cn.edu.scau.cmi.utils;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/*
 * 将json对象转为java对象类。
 * */
public class JsonUtils {
	/*
	 * 将json对象转为java对象 泛型 jsonString为json对象
	 * cls为java对象的类，如EmployeeBean类，cls即为EmployeeBean.class
	 */
	public static <T> ArrayList<T> JsonToObejctList(String jsonString, Class<T> cls) {
		Gson mGson = new Gson();
		ArrayList<T> mList = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
		for (final JsonElement elem : array) {
			mList.add(mGson.fromJson(elem, cls));
		}
		return mList;
	}

	/*
	 * 将list转为json;
	 */
	public static <E> String DataListToJson(ArrayList<E> dataList) {
		Gson mGson = new Gson();
		return mGson.toJson(dataList);
	}

	public static String DataToJson(Object obj) {
		Gson mGson = new Gson();
		return mGson.toJson(obj);
	}
}
