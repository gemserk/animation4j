package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class FloatArrayInterpolator {
	
	private final InterpolatorFunction[] functions;
	
	private final float[] out;
	
	public int getLength() {
		return out.length;
	}
	
	public FloatArrayInterpolator(int length) {
		this(new float[length]);
	}
	
	public FloatArrayInterpolator(int length, InterpolatorFunction ...functions) {
		this(new float[length], functions);
	}
	
	public FloatArrayInterpolator(float[] out, InterpolatorFunction ...functions) {
		this.out = out;
		this.functions = new InterpolatorFunction[out.length];
		
		int i = 0;
		for (i = 0; i < functions.length; i++) 
			this.functions[i] = functions[i];
		for (; i < this.functions.length; i++)
			this.functions[i] = InterpolatorFunctionFactory.linear();
	}
	
	protected float[] interpolate(float[] a, float[] b, float t) {
		for (int i = 0; i < out.length; i++) {
			t = functions[i].interpolate(t);	
			out[i] = a[i] * (1 - t) + b[i] * t;
		}
		return out;
	}
	
}