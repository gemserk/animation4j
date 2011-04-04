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

	private Map<String, Method> cachedSettersMethods = new HashMap<String, Method>();

	private ArrayList<String> missingMethods = new ArrayList<String>();
	
	/**
	 * This field is needed because we are using some caching stuff inside, if the cache stuff was in another class then this field would be not needed.
	 */
	private final Class<?> clazz;

	public ReflectionObjectSynchronizer(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void setValue(Object object, String name, Object value) {

		if (!object.getClass().isAssignableFrom(clazz))
			throw new RuntimeException("object should be instance from " + clazz);

		try {
			Method setterMethod = getSetter(object, name);
			if (setterMethod == null)
				return;
			setterMethod.invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException("failed to set value " + value.toString() + " to field " + name, e);
		}
	}

	protected Method getSetter(Object object, String name) {
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
