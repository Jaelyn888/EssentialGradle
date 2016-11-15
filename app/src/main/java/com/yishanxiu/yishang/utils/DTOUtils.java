package com.yishanxiu.yishang.utils;

import java.lang.reflect.Field;
import java.util.*;

import com.google.gson.Gson;


/**
 * 对象COPY
 */
public class DTOUtils {

	public static <S, T> T map(S source, Class<T> targetClass) {
		String jsonStr = new Gson().toJson(source);
		return new Gson().fromJson(jsonStr, targetClass);
	}


	/**
	 * 未实现
	 * @param source
	 * @param targetClass
	 * @param <S>
	 * @param <T>
	 * @return
	 */
	public static <S, T> List<T> map(List<S> source, Class<T> targetClass) {

//		List list = Collections..newArrayList(source.size());
//		for (Object obj : source) {
//			Object target = INSTANCE.map(obj, targetClass);
//			list.add(target);
//		}
//		return list;
		return null  ;
	}

	public static Map<String, Object> beanToMap(Object source) throws Exception {
		if (source == null) {
			throw new Exception("beanToMap source bean is null");
		}
		Map<String, Object> map = new HashMap<>();
		Field[] fds = source.getClass().getDeclaredFields();
		for (Field f : fds) {
			f.setAccessible(true);
			map.put(f.getName(), f.get(source));
		}
		return map;
	}

}
