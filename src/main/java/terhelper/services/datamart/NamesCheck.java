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
		String unitIdsSplit = redis.get("unit.ids");
		if (null == unitIdsSplit) {
			return "[]";
		}
		String[] unitIds = unitIdsSplit.split("_");
		if (0 == unitIds.length) {
			return "[]";
		}
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
		return jsonParts.toString();
	}
}
