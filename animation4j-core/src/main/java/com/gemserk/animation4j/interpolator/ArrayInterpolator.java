package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

// T should be mutable elements

public abstract class ArrayInterpolator<T> {

	private float[] a;

	private float[] b;

	private T value;

	private MultipleVariableInterpolator multipleVariableInterpolator;
	
	public static class MultipleVariableInterpolator {
		
		private final InterpolatorFunction function;
		
		private final float[] out;
		
		public MultipleVariableInterpolator(float[] out) {
			this(InterpolatorFunctionFactory.linear(), out);
		}
		
		public MultipleVariableInterpolator(InterpolatorFunction function, float[] out) {
			this.function = function;
			this.out = out;
		}
		
		protected float[] interpolate(float[] a, float[] b, float t) {
			t = function.interpolate(t);
			for (int i = 0; i < out.length; i++) 
				out[i] = a[i] * (1 - t) + b[i] * t;
			return out;
		}
		
	}

	public ArrayInterpolator(InterpolatorFunction function, int length) {
		multipleVariableInterpolator = new MultipleVariableInterpolator(function, new float[length]);
		a = new float[length];
		b = new float[length];
	}
	
	public abstract void copyFromObject(T object, float[] x);

	public abstract T copyToObject(float[] x);

	public T interpolate(T t1, T t2, float t) {

		copyFromObject(t1, a);
		copyFromObject(t2, b);

		value = copyToObject(multipleVariableInterpolator.interpolate(a, b, t));
		
		return value;
	}

}