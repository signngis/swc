package com.atguigu.util;

import java.util.HashMap;
import java.util.Map;

public class CacheUtils {
	
	private static Map<String, Object> cache = new HashMap<>();
	
	
	public static Object get(String key) {
		return cache.get(key);
	}
	
	public static void put(String key,Object value) {
		cache.put(key, value);
	}
	
	public static void remove(String key) {
		cache.remove(key);
	}
	
	public static void clear() {
		cache.clear();
	}

}
