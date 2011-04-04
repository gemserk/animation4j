package com.gemserk.animation4j.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

	// Could be optimized?

	static class ClassCache {

		Map<String, Method> cachedMethods = new HashMap<String, Method>();

	}

	private static Map<String, ClassCache> classCache = new HashMap<String, ReflectionUtils.ClassCache>();

	public static String getSetterName(String fieldName) {
		return "set" + capitalize(fieldName);
	}

	public static String getGetterName(String fieldName) {
		return "get" + capitalize(fieldName);
	}

	public static String capitalize(String name) {
		return name.toUpperCase().substring(0, 1) + name.substring(1);
	}

	public static Method findMethod(Class clazz, String methodName) {

		ClassCache classCacheEntry = getClassCache(clazz);

		Method method = classCacheEntry.cachedMethods.get(methodName);

		if (method != null)
			return method;

		method = internalFindMethod(clazz, methodName);

		if (method == null)
			return null;

		classCacheEntry.cachedMethods.put(methodName, method);

		return method;
	}

	protected static Method internalFindMethod(Class clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods)
			if (method.getName().equals(methodName))
				return method;
		return null;
	}

	public static Method getSetter(Class clazz, String fieldName) {
		return findMethod(clazz, getSetterName(fieldName));
	}
	
	public static Method getGetter(Class clazz, String fieldName) {
		return findMethod(clazz, getGetterName(fieldName));
	}

	private static ClassCache getClassCache(Class clazz) {
		if (!classCache.containsKey(clazz.getName()))
			classCache.put(clazz.getName(), new ClassCache());
		return classCache.get(clazz.getName());
	}

}
