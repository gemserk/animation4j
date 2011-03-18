package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

// T should be mutable elements

public class ArrayInterpolator<T> {

	private float[] a;

	private float[] b;

	private T value;

	private FloatArrayInterpolator floatArrayInterpolator;

	private final TypeConverter<T> converter;
	
	/**
	 * Provides a way to convert an object in a float[] array and vice versa, for interpolation purposes. 
	 * @param <T> - The type to convert.
	 * @author acoppes
	 */
	public static interface TypeConverter<T> {
		
		float[] copyFromObject(T object, float[] x);

		T copyToObject(T object, float[] x);
		
	}

	public ArrayInterpolator(InterpolatorFunction function, TypeConverter<T> converter, int length) {
		this.converter = converter;
		floatArrayInterpolator = new FloatArrayInterpolator(new float[length], function);
		a = new float[length];
		b = new float[length];
	}
	
	public T interpolate(T t1, T t2, float t) {

		a = converter.copyFromObject(t1, a);
		b = converter.copyFromObject(t2, b);
		
		value = converter.copyToObject(value, floatArrayInterpolator.interpolate(a, b, t));
		
		return value;
	}

}