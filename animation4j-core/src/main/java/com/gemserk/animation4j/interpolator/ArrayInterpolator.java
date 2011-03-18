package com.gemserk.animation4j.interpolator;

public class ArrayInterpolator<T> {

	private float[] a;

	private float[] b;

	private final TypeConverter<T> converter;

	private FloatArrayInterpolator floatArrayInterpolator;
	
	public ArrayInterpolator(TypeConverter<T> converter, FloatArrayInterpolator floatArrayInterpolator) {
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