package com.gemserk.animation4j.interpolators;

public class CubicBezierInterpolator implements BezierInterpolator {

	private final float p0, p1, p2, p3;

	public CubicBezierInterpolator(float p0, float p1, float p2, float p3) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	@Override
	public float interpolate(float t) {
		float a = (1 - t) * (1 - t) * (1 - t) * p0;
		float b = 3 * (1 - t) * (1 - t) * t * p1;
		float c = 3 * (1 - t) * t * t * p2;
		float d = t * t * t * p3;
		return a + b + c + d;
	}

}
