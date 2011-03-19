package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.converters.TypeConverter;

/**
 * Provides an implementation of the Interpolator interface using a TypeConverter to convert from the object to a float[] and vice versa, and a Interpolator<float[]> to interpolate float[] values.
 * 
 * @param <T>
 * @author acoppes
 */
public class GenericInterpolator<T> implements Interpolator<T> {

	private final TypeConverter<T> converter;

	private float[] a;

	private float[] b;

	private FloatArrayInterpolator floatArrayInterpolator;

	public GenericInterpolator(TypeConverter<T> converter, FloatArrayInterpolator floatArrayInterpolator) {
		this.converter = converter;
		this.floatArrayInterpolator = floatArrayInterpolator;
		a = new float[floatArrayInterpolator.getLength()];
		b = new float[floatArrayInterpolator.getLength()];
	}

	public T interpolate(T t1, T t2, float t) {
		a = converter.copyFromObject(t1, a);
		b = converter.copyFromObject(t2, b);
		return converter.copyToObject(null, floatArrayInterpolator.interpolate(a, b, t));
	}

}