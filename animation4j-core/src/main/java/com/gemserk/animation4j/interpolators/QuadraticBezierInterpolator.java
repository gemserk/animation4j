package com.gemserk.animation4j.interpolators;

public class QuadraticBezierInterpolator implements InterpolatorFunction {

	private final float p0, p1, p2;

	public QuadraticBezierInterpolator(float p0, float p1, float p2) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public float interpolate(float t) {
		float a = (1 - t) * (1 - t) * p0;
		float b = 2 * (1 - t) * t * p1;
		float c = t * t * p2;
		return a + b + c;
	}

}
