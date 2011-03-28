package com.gemserk.animation4j.interpolator.function;

/**
 * Linear Bézier curve implementation of an InterpolatorFunction.
 * 
 * @author acoppes
 */
public class LinearBezierInterpolatorFunction implements InterpolationFunction {

	private final float p0, p1;

	public LinearBezierInterpolatorFunction(float p0, float p1) {
		this.p0 = p0;
		this.p1 = p1;
	}

	@Override
	public float interpolate(float t) {
		if (t < 0)
			return p0;
		if (t > 1)
			return p1;
		return (1 - t) * p0 + t * p1;
	}

}
