package com.gemserk.animation4j.interpolator;

public class ArrayInterpolator<T> {

	private float[] a;

	private float[] b;

	private final TypeConverter<T> converter;

	private FloatArrayInterpolator floatArrayInterpolator;
	
	public ArrayInterpolator(TypeConverter<T> converter, int length) {
		this.converter = converter;
		floatArrayInterpolator = new FloatArrayInterpolator(new float[length]);
		a = new float[length];
		b = new float[length];
	}
	
	public T interpolate(T t1, T t2, float t) {
		a = converter.copyFromObject(t1, a);
		b = converter.copyFromObject(t2, b);
		return converter.copyToObject(null, floatArrayInterpolator.interpolate(a, b, t));
	}

}