package com.gemserk.animation4j.interpolator;

import java.util.HashMap;
import java.util.Map;


/**
 * Provides a way to infer previous registered interpolators for an object based on its type. 
 * 
 * @author acoppes
 */
public class InterpolatorProvider {
	
	@SuppressWarnings("rawtypes")
	Map<Class, Interpolator> interpolators = new HashMap<Class, Interpolator>();
	
	@SuppressWarnings("rawtypes")
	Interpolator defaultInterpolator = new NullInterpolator();
	
	/**
	 * Returns a previous registered interpolator for the object type.
	 * @param object - The object to use to get the needed type.
	 * @return A linear interpolator for the type of the value or a NullInterpolator if no interpolator was associated with the object type.
	 */
	public <T> Interpolator<T> inferInterpolator(T object) {
		return provide(object.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T> Interpolator<T> provide(Class<? extends Object> clazz) {
		if (!interpolators.containsKey(clazz))
			return (Interpolator<T>) defaultInterpolator;
		return interpolators.get(clazz);
	}

	public <T> void register(Class<T> clazz, Interpolator<T> interpolator) {
		interpolators.put(clazz, interpolator);
	}
}