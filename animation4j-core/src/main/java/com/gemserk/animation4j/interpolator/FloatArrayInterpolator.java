package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class FloatArrayInterpolator {
	
	private final InterpolatorFunction function;
	
	private final float[] out;
	
	public FloatArrayInterpolator(float[] out) {
		this(out, InterpolatorFunctionFactory.linear());
	}
	
	public FloatArrayInterpolator(float[] out, InterpolatorFunction function) {
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