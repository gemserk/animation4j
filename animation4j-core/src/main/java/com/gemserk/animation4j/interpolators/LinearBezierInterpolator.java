package com.gemserk.animation4j.interpolators;

public class LinearBezierInterpolator {

	private final float p0, p1;

	public LinearBezierInterpolator(float p0, float p1) {
		this.p0 = p0;
		this.p1 = p1;
	}

	/**
	 * @param t
	 *            - A real number in the interval [0,1]
	 * @return The interpolated value.
	 */
	public Float interpolate(float t) {
		return (1 - t) * p0 + t * p1;
	}

}
