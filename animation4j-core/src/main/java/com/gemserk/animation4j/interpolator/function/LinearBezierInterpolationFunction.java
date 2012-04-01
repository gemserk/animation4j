package com.gemserk.animation4j.interpolator.function;

/**
 * Linear Bézier curve implementation of an InterpolatorFunction.
 * 
 * @author acoppes
 */
public class LinearBezierInterpolationFunction implements InterpolationFunction {

	@Override
	public float interpolate(float t) {
		if (t < 0)
			return 0f;
		if (t > 1)
			return 1f;
		return t;
	}

}
