package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

public class FloatInterpolator implements Interpolator<Float> {

	InterpolatorFunction interpolatorFunction;
	
	private FloatInterpolator(InterpolatorFunction interpolatorFunction) {
		this.interpolatorFunction = interpolatorFunction;
	}

	@Override
	public Float interpolate(Float a, Float b, float t) {
		float x = interpolatorFunction.interpolate(t);
		return (1 - x) * a + x * b;
	}

	public static Interpolator<Float> floatInterpolator(InterpolatorFunction function) {
		return new FloatInterpolator(function);
	}
}
