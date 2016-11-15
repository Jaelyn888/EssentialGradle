package net.tsz.afinal.json;


import java.io.Serializable;
import java.util.*;

/**
 * Created by Jaelyn on 2015/9/22 0022.
 */
public class JsonMapOrListJsonMap2JsonUtil<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;

	public JsonMapOrListJsonMap2JsonUtil() {
	}

	public String map2Json(Map<K, V> data) {
		if(data != null && data.size() != 0) {
			StringBuilder sb = new StringBuilder("{");
			Set ks = data.keySet();
			Iterator var5 = ks.iterator();

			while(var5.hasNext()) {
				Object k = var5.next();
				sb.append("\"" + k.toString() + "\":");
				Object v = data.get(k);
				if(v instanceof Number) {
					sb.append(v + ",");
				} else if(v.getClass().equals(ArrayList.class)) {
					sb.append(this.listJsonMap2Json((List)v) + ",");
				} else if(v.getClass().equals(HashMap.class)) {
					sb.append(this.map2Json((Map)v) + ",");
				} else if(v.getClass().equals(JsonMap.class)) {
					sb.append(this.map2Json((Map)v) + ",");
				} else {
					ArrayList<JsonMap<String,Object>> temp= (ArrayList<JsonMap<String, Object>>) JsonParseHelper.getList_JsonMap(v.toString());
					if(!temp.isEmpty()){
						sb.append(this.listJsonMap2Json((List) temp) + ",");
					} else {
						JsonMap<String,Object> tempMap=  JsonParseHelper.getJsonMap(v.toString());
						if(tempMap.isEmpty()) {
							sb.append("\"" + v + "\",");
						}else {
							sb.append(this.map2Json((Map)tempMap) + ",");
						}
					}
				}
			}

			sb.deleteCharAt(sb.length() - 1);
			sb.append("}");
			return sb.toString();
		} else {
			return "{}";
		}
	}

	public String listJsonMap2Json(List<? extends Map<K, V>> data) {
		if(data != null && data.size() != 0) {
			StringBuilder sb = new StringBuilder("[");
			Iterator var4 = data.iterator();

			while(var4.hasNext()) {
				Map map = (Map)var4.next();
				sb.append(this.map2Json(map) + ",");
			}

			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
			return sb.toString();
		} else {
			return "[]";
		}
	}
}
