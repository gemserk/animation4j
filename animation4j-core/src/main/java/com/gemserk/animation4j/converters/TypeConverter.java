package com.gemserk.animation4j.converters;

/**
 * Provides a way to convert an object in a float[] array and vice versa, for interpolation purposes.
 * 
 * @param <T>
 *            The type to convert.
 * @author acoppes
 */
public interface TypeConverter<T> {

	/**
	 * Copy the values of the object to the specified float array, if null it will create a new float array.
	 * 
	 * @param object
	 *            The object from where to get the values to fulfill the float array.
	 * @param x
	 *            The float array to copy the values of the object. If null it will create a new float array.
	 * @return The float array with the values of the object.
	 */
	float[] copyFromObject(T object, float[] x);

	/**
	 * Copy the values of the float array to the specified object.
	 * 
	 * @param object
	 *            The object which the float array values will be copied to. If null or object immutable, it will create a new object.
	 * @param x
	 *            The float array to get the values to fulfill the object.
	 * @return An object with the values of the float array.
	 */
	T copyToObject(T object, float[] x);

}