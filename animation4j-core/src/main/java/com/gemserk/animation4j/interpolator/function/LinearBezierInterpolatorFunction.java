package com.gemserk.animation4j.interpolator.function;

public class LinearBezierInterpolatorFunction implements InterpolatorFunction {

	private final float p0, p1;

	public LinearBezierInterpolatorFunction(float p0, float p1) {
		this.p0 = p0;
		this.p1 = p1;
	}

	@Override
	public float interpolate(float t) {
		return (1 - t) * p0 + t * p1;
	}

}
