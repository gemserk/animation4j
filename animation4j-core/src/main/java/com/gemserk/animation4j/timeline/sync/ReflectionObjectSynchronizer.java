package com.gemserk.animation4j.timeline.sync;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gemserk.animation4j.reflection.ReflectionUtils;

/**
 * ObjectSynchronizer implementation using reflection to set the values to an object fields through setter methods.
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

		String setterName = ReflectionUtils.getSetterName(name);

		setterMethod = ReflectionUtils.findMethod(object, setterName);
		if (setterMethod != null) {
			cachedSettersMethods.put(name, setterMethod);
			return setterMethod;
		}

		missingMethods.add(name);
		return null;
	}

}
