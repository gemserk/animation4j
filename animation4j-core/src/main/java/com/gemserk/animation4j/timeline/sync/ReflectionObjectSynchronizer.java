package com.gemserk.animation4j.timeline.sync;

import java.lang.reflect.Method;

import com.gemserk.animation4j.reflection.ReflectionUtils;

/**
 * ObjectSynchronizer implementation using reflection to set the values to an object fields through setter methods.
 * 
 * @author acoppes
 */
public class ReflectionObjectSynchronizer implements ObjectSynchronizer {

	@Override
	public void setValue(Object object, String name, Object value) {

		try {
			Method setterMethod = ReflectionUtils.getSetter(object.getClass(), name);
			if (setterMethod == null)
				throw new RuntimeException("failed to get setter for " + name + " in " + object.getClass());
			setterMethod.invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException("failed to set value " + value.toString() + " to field " + name, e);
		}
	}

}
