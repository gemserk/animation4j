package com.gemserk.animation4j.converters;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a place to register default converters for each class.
 * 
 * @author acoppes
 */
public class Converters {

	private static Map<Class<?>, TypeConverter<?>> converters = new HashMap<Class<?>, TypeConverter<?>>();

	/**
	 * Returns the registered TypeConverter for the given class.
	 * 
	 * @param clazz
	 *            The class of the type to search for the corresponding TypeConverter.
	 * @return The corresponding TypeConverter.
	 * @throws RuntimeException
	 *             if no converter found for the given class.
	 */
	@SuppressWarnings("unchecked")
	public static <T> TypeConverter<T> converter(Class<T> clazz) {
		TypeConverter<?> converter = converters.get(clazz);
		if (converter == null)
			throw new RuntimeException("failed to get converter for type " + clazz.getName());
		return (TypeConverter<T>) converter;
	}

	/**
	 * Register a TypeConverter for a given class, used by Transitions to infer the converter type.
	 * 
	 * @param clazz
	 *            The class of the type of the converter to be registered.
	 * @param typeConverter
	 *            The TypeConverter to be registered for the given class.
	 */
	public static void register(Class<?> clazz, TypeConverter<?> typeConverter) {
		converters.put(clazz, typeConverter);
	}

	/**
	 * Remove the corresponding converter for specified class from the static converters map.
	 * 
	 * @param clazz
	 *            The class of the type of the converter to be unregistered.
	 */
	public static void unregister(Class<?> clazz) {
		converters.remove(clazz);
	}

}