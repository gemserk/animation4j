package com.gemserk.animation4j.interpolator.function;

public class LinearInterpolationFunction implements InterpolationFunction {
	@Override
	public float interpolate(float t) {
		if (t < 0f)
			return 0f;
		if (t > 1f)
			return 1f;
		return t;
	}
}