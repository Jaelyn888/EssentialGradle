package net.tsz.afinal.json;

/**
 * Created by Jaelyn on 16/3/4.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonParseHelper {
    private static final String TAG = "JsonParseHelper";

    public JsonParseHelper() {
    }

    public static String getAttribute(String jsonStr, String[] attributeNames) {
        String attributeValue = null;

        try {
            JSONObject e = new JSONObject(jsonStr);
            int j = attributeNames.length - 1;

            for(int i = 0; i < j; ++i) {
                attributeValue = e.getString(attributeNames[i]);
                e = new JSONObject(attributeValue.toString());
            }

            attributeValue = e.getString(attributeNames[j]);
        } catch (JSONException var6) {
            attributeValue = "";
        }

        return attributeValue;
    }

    public static String getAttribute(String jsonStr, int[] arrayIndexs, String attributeName) {
        String attributeValue = null;

        try {
            JSONArray e = new JSONArray(jsonStr);
            int j = arrayIndexs.length - 1;

            for(int o = 0; o < j; ++o) {
                Object object = e.get(arrayIndexs[o]);
                e = new JSONArray(object.toString());
            }

            Object var9 = e.get(arrayIndexs[j]);
            JSONObject var10 = new JSONObject(var9.toString());
            attributeValue = var10.getString(attributeName);
        } catch (JSONException var8) {
            attributeValue = "";
        }

        return attributeValue;
    }

    public static Map<String, Object> getMap(String jsonString) {
        HashMap valueMap;
        try {
            JSONObject e = new JSONObject(jsonString);
            Iterator keyIter = e.keys();
            valueMap = new HashMap();

            while(keyIter.hasNext()) {
                String key = (String)keyIter.next();
                Object value = e.get(key);
                valueMap.put(key, value);
            }
        } catch (JSONException var6) {
            valueMap = new HashMap();
        }

        return valueMap;
    }

    public static List<Map<String, Object>> getList(String jsonString) {
        ArrayList list = null;

        try {
            JSONArray e = new JSONArray(jsonString);
            list = new ArrayList();

            for(int i = 0; i < e.length(); ++i) {
                JSONObject jsonObject = e.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        } catch (Exception var5) {
            list = new ArrayList();
        }

        return list;
    }

    public static Map<String, Object> getMap_Map(String jsonString, String key) {
        Map map = getMap(getAttribute(jsonString, new String[]{key}));
        return map;
    }

    public static List<Map<String, Object>> getMap_List(String jsonString, String key) {
        return getList(getAttribute(jsonString, new String[]{key}));
    }

    public static List<Map<String, Object>> getMap_Map_List(String jsonString, String key, String key_key) {
        return getList(getAttribute(jsonString, new String[]{key, key_key}));
    }

    public static JsonMap<String, Object> getJsonMap(String jsonString) {
        JsonMap valueMap;
        try {
            JSONObject e = new JSONObject(jsonString);
            Iterator keyIter = e.keys();
            valueMap = new JsonMap();

            while(keyIter.hasNext()) {
                String key = (String)keyIter.next();
                Object value = e.get(key);
                valueMap.put(key, value);
            }
        } catch (JSONException var6) {
            valueMap = new JsonMap();
        }

        return valueMap;
    }

    public static List<JsonMap<String, Object>> getList_JsonMap(String jsonString) {
        ArrayList list = null;

        try {
            JSONArray array = new JSONArray(jsonString);
            list = new ArrayList();

            for(int i = 0; i < array.length(); ++i) {
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                    list.add(getJsonMap(jsonObject.toString()));
                }catch (Exception e){
                    JsonMap<String,Object> map=new JsonMap<String,Object>();
                    map.put("arraykey",array.get(i).toString());
                    list.add(map);
                }
            }
        } catch (Exception var5) {
            list = new ArrayList();
        }

        return list;
    }

    public static JsonMap<String, Object> getJsonMap_JsonMap(String jsonString, String key) {
        JsonMap map = getJsonMap(getAttribute(jsonString, new String[]{key}));
        return map;
    }

    public static List<JsonMap<String, Object>> getJsonMap_List_JsonMap(String jsonString, String key) {
        return getList_JsonMap(getAttribute(jsonString, new String[]{key}));
    }

    public static List<JsonMap<String, Object>> getJsonMap_JsonMap_List_JsonMap(String jsonString, String key, String key_key) {
        return getList_JsonMap(getAttribute(jsonString, new String[]{key, key_key}));
    }
}
