package com.gemserk.animation4j.timeline;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.NullInterpolator;

/**
 * Provides a way to infer previous registered interpolators for an object based on its type. 
 * 
 * @author acoppes
 */
public class InterpolatorProvider {
	
	@SuppressWarnings("rawtypes")
	Map<Class, Interpolator> interpolators = new HashMap<Class, Interpolator>();
	
	/**
	 * Infer a default interpolator for the value.
	 * @param value - The value to use to infer the associated interpolator.
	 * @return A linear interpolator for the type of the value or a NullInterpolator if no interpolator was associated with the type of the value.
	 */
	@SuppressWarnings("unchecked")
	public <T> Interpolator<T> inferLinearInterpolator(T value) {
		if (!interpolators.containsKey(value.getClass()))
			return new NullInterpolator<T>();
		return interpolators.get(value.getClass());
	}

	public <T> void register(Class<T> clazz, Interpolator<T> interpolator) {
		interpolators.put(clazz, interpolator);
	}
}