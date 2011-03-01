package com.gemserk.animation4j.interpolators;

public class LinearBezierInterpolator implements BezierInterpolator {

	private final float p0, p1;

	public LinearBezierInterpolator(float p0, float p1) {
		this.p0 = p0;
		this.p1 = p1;
	}

	@Override
	public float interpolate(float t) {
		return (1 - t) * p0 + t * p1;
	}

}
