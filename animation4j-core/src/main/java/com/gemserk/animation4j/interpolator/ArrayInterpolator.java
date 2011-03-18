package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

// T should be mutable elements

public class ArrayInterpolator<T> {

	private float[] a;

	private float[] b;

	private T value;

	private MultipleVariableInterpolator multipleVariableInterpolator;

	private final TypeConverter<T> converter;
	
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
		multipleVariableInterpolator = new MultipleVariableInterpolator(function, new float[length]);
		a = new float[length];
		b = new float[length];
	}
	
	public T interpolate(T t1, T t2, float t) {

		a = converter.copyFromObject(t1, a);
		b = converter.copyFromObject(t2, b);
		
		value = converter.copyToObject(value, multipleVariableInterpolator.interpolate(a, b, t));
		
		return value;
	}

}