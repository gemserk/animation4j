package com.gemserk.animation4j.interpolator.function;

/**
 * Quadratic Bézier curve implementation of an InterpolatorFunction.
 * 
 * @author acoppes
 */
public class QuadraticBezierInterpolationFunction implements InterpolationFunction {

	private final float p0, p1, p2;

	public QuadraticBezierInterpolationFunction(float p0, float p1, float p2) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public float interpolate(float t) {
		if (t < 0)
			return p0;
		if (t > 1)
			return p2;
		float a = (1 - t) * (1 - t) * p0;
		float b = 2 * (1 - t) * t * p1;
		float c = t * t * p2;
		return a + b + c;
	}

}
