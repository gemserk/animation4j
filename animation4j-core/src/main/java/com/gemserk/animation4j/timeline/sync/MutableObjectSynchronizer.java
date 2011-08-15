package com.gemserk.animation4j.timeline.sync;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.reflection.ReflectionUtils;

/**
 * ObjectSynchronizer implementation using reflection to set the values to an object fields through setter methods.
 * 
 * @author acoppes
 */
public class MutableObjectSynchronizer implements ObjectSynchronizer {

	// works only if objects are always from the same class
	
	private Map<String, float[]> cachedArrays = new HashMap<String, float[]>();
	private static final Object[] emptyObjectArray = new Object[] {};
	
	@Override
	public void setValue(Object object, String name, Object value) {

		Method method = ReflectionUtils.getGetter(object.getClass(), name);
		
		if (method == null) 
			throw new RuntimeException("missing getter for field " + name + " in " + object.getClass());

		TypeConverter typeConverter = Converters.converter(value.getClass());
		
		try {
			Object fieldObject = method.invoke(object, emptyObjectArray);
			cachedArrays.put(name, typeConverter.copyFromObject(value, cachedArrays.get(name)));
			typeConverter.copyToObject(fieldObject, cachedArrays.get(name));
		} catch (Exception e) {
			throw new RuntimeException("failed to set value " + value.toString() + " to field " + name + " of object " + object, e);
		}

	}

}
