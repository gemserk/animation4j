package com.gemserk.animation4j.interpolators;

public interface BezierInterpolator {

	/**
	 * @param t
	 *           - A real number in the interval [0,1]
	 * @return The interpolated value.
	 */
	float interpolate(float t);

}