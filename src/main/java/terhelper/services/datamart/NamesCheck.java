package terhelper.services.datamart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.json.bind.JsonbBuilder;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import terhelper.services.storages.Redis;

public class NamesCheck {
	private Redis redis = new Redis();
	public String getDateUpdate() {
		return redis.get("unit.date-upd");
	}
	
	public String getResult() {
//		HashMap<String, String> result1 = new HashMap<String, String>();
//		ArrayLi
//		JsonObject json1 = new JsonObject();
//		json1.addProperty("name", "Ivan");
//		result1.put("dateUpd", "19.05.2021");
//		result1.put("data", json1.toString());
//		System.out.println("result.toString()  " + Arrays.asList(result1).toString());
		
		String unitIdsSplit = redis.get("unit.ids");
		if (null == unitIdsSplit) {
			return "[]";
		}
		String[] unitIds = unitIdsSplit.split("_");
		if (0 == unitIds.length) {
			return "[]";
		}
//		HashMap<String, String> result = new HashMap<String, String>();
		ArrayList<String> jsonParts = new ArrayList<String>();
		for (String unitId : unitIds) {	
			String locId = redis.get("unit."+ unitId +".LocationId");
			if (null == locId) {
				continue;
			}
			JsonObject json = new JsonObject();
			json.addProperty("id", unitId);
			json.addProperty("name", redis.get("unit."+ unitId +".Number"));
			json.addProperty("address", redis.get("loc."+ locId +".Address"));
			String statusId = redis.get("unit."+ unitId +".StatusId");
			json.addProperty("status", redis.get("status."+statusId+".InternalName"));
			json.addProperty("address", redis.get("loc."+ locId +".Address"));
			jsonParts.add(json.toString());
		}
//		result.put("dateUpd", redis.get("unit.date-upd"));
//		result.put("data", jsonParts.toString());
//		System.out.println("result.toString()  " + result.toString());
		return jsonParts.toString();
	}
}
