package net.tsz.afinal.json;

/**
 * Created by Jaelyn on 16/3/4.
 */

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMap<K, V> extends HashMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    public JsonMap() {
    }

    public String getString(Object key) {
        String s = "";

        try {
            s = this.get(key).toString().trim();
        } catch (Exception var4) {
            s = "";
        }

        return s;
    }

    public String getString(Object key, String defauleValue) {
        String s = "";

        try {
            s = this.get(key).toString().trim();
        } catch (Exception var5) {
            s = defauleValue;
        }

        return s;
    }

    public String getStringNoNull(String key) {
        return this.getStringNoNull(key, "");
    }

    public String getStringNoNull(String key, String defaultValue) {
        key = this.getString(key, defaultValue);
        return "null".equals(key)?defaultValue:key;
    }

    public String getStr(Object key) {
        String s = "";

        try {
            s = this.get(key).toString();
        } catch (Exception var4) {
            s = "";
        }

        return s;
    }

    public int getInt(Object key) {
        return this.getInt(key, 0);
    }

    public int getInt(Object key, int defauleValue) {
        boolean i = false;

        int i1;
        try {
            i1 = Integer.parseInt(this.getString(key));
        } catch (Exception var5) {
            i1 = defauleValue;
        }

        return i1;
    }

    public long getLong(Object key) {
        return this.getLong(key, 0);
    }

    public long getLong(Object key, int defauleValue) {
        boolean i = false;

        long i1;
        try {
            i1 = Long.parseLong(this.getString(key));
        } catch (Exception var5) {
            i1 = defauleValue;
        }

        return i1;
    }
    public float getFloat(Object key) {
        return this.getFloat(key, 0.0F);
    }

    public float getFloat(Object key, float defauleValue) {
        float f = 0.0F;

        try {
            f = Float.parseFloat(this.getString(key));
        } catch (Exception var5) {
            f = defauleValue;
        }

        return f;
    }

    public double getDouble(Object key) {
        return this.getDouble(key, 0.0D);
    }

    public double getDouble(Object key, double defauleValue) {
        double d = 0.0D;

        try {
            d = Double.parseDouble(this.getString(key));
        } catch (Exception var7) {
            d = defauleValue;
        }

        return d;
    }

    public boolean getBoolean(Object key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(Object key, boolean defauleValue) {
        boolean b = false;
        try {
           String value= this.getString(key);
            if(TextUtils.isEmpty(value)){
                  b=defauleValue;
            } else {
                b=!"0".equalsIgnoreCase(value);
            }

        } catch (Exception var5) {
            b = defauleValue;
        }

        return b;
    }

    public JsonMap<String, Object> getJsonMap(Object key) {
        JsonMap data = JsonParseHelper.getJsonMap(this.getString(key));
        return data;
    }

    public List<JsonMap<String, Object>> getList_JsonMap(Object key) {
        return JsonParseHelper.getList_JsonMap(this.getString(key));
    }

    public String toJson() {
        return this.keySet().size() == 0?"{}":(new JsonMapOrListJsonMap2JsonUtil()).map2Json(this);
    }

    public Object clone() {
        return super.clone();
    }
}
