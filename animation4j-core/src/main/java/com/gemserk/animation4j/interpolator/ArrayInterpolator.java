package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

// T should be mutable elements

public abstract class ArrayInterpolator<T> {

	private InterpolatorFunction function;

	private float[] a;

	private float[] b;

	private float[] out;
	
	private T value;

	public ArrayInterpolator(InterpolatorFunction function) {
		this.function = function;
		int length = getVariableQuantity();
		a = new float[length];
		b = new float[length];
		out = new float[length];
	}
	
	public abstract int getVariableQuantity();

	public abstract void copyFromObject(T object, float[] x);

	public abstract T copyToObject(T object, float[] x);

	public T interpolate(T t1, T t2, float t) {

		copyFromObject(t1, a);
		copyFromObject(t2, b);

		t = function.interpolate(t);

		for (int i = 0; i < out.length; i++) 
			out[i] = a[i] * (1 - t) + b[i] * t;
		
		value = copyToObject(value, out);
		
		return value;
	}

}