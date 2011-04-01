package com.gemserk.animation4j.reflection;

import java.lang.reflect.Method;

public class ReflectionUtils {
	
	// Could be optimized?

	public static String getSetterName(String fieldName) {
		return "set" + capitalize(fieldName);
	}

	public static String getGetterName(String fieldName) {
		return "get" + capitalize(fieldName);
	}
	
	private static String capitalize(String name) {
		return name.toUpperCase().substring(0, 1) + name.substring(1);
	}
	
	public static Method findMethod(Object object, String methodName) {
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) 
			if (method.getName().equals(methodName))
				return method;
		return null;
	}
}
