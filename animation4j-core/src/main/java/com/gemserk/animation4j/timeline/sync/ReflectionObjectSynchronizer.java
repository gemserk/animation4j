package com.gemserk.animation4j.timeline.sync;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ObjectSynchronizer implementation using reflection to set the values to an object fields.
 * 
 * @author acoppes
 */
public class ReflectionObjectSynchronizer implements ObjectSynchronizer {

	private final Object object;

	private Map<String, Method> cachedSettersMethods = new HashMap<String, Method>();

	private ArrayList<String> missingMethods = new ArrayList<String>();

	public ReflectionObjectSynchronizer(Object object) {
		this.object = object;
	}

	@Override
	public void setValue(String name, Object value) {
		try {
			Method setterMethod = getSetter(name);
			if (setterMethod == null)
				return;
			setterMethod.invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException("failed to set value " + value.toString() + " to field " + name, e);
		}
	}

	protected Method getSetter(String name) {
		if (missingMethods.contains(name))
			return null;
		Method setterMethod = cachedSettersMethods.get(name);
		if (setterMethod != null)
			return setterMethod;
		Method[] methods = object.getClass().getMethods();
		String setterName = getSetterName(name);
		for (Method method : methods) {
			if (!method.getName().equals(setterName))
				continue;
			cachedSettersMethods.put(name, method);
			return method;
		}
		missingMethods.add(name);
		return null;
	}

	protected String getSetterName(String name) {
		return "set" + name.toUpperCase().substring(0, 1) + name.substring(1);
	}

}
