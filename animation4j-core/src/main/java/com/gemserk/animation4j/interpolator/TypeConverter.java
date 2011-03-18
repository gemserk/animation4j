package com.gemserk.animation4j.interpolator;

/**
 * Provides a way to convert an object in a float[] array and vice versa, for interpolation purposes. 
 * @param <T> - The type to convert.
 * @author acoppes
 */
public interface TypeConverter<T> {
	
	float[] copyFromObject(T object, float[] x);

	T copyToObject(T object, float[] x);
	
}