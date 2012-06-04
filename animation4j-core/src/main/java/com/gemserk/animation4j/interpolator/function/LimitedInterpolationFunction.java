package com.gemserk.animation4j.interpolator.function;

public class LimitedInterpolationFunction implements InterpolationFunction {

	private final InterpolationFunction function;

	public LimitedInterpolationFunction(InterpolationFunction interpolationFunction) {
		this.function = interpolationFunction;
	}

	@Override
	public float interpolate(float t) {
		if (t < 0f)
			return 0f;
		if (t > 1f)
			return 1f;
		return function.interpolate(t);
	}

}