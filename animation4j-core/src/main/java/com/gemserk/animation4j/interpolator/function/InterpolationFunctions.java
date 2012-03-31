package com.gemserk.animation4j.interpolator.function;

public class InterpolationFunctions {

	public static final InterpolationFunction linear = new LinearBezierInterpolationFunction(0f, 1f);
	public static final InterpolationFunction easeIn = new CubicBezierInterpolationFunction(0f, 0.42f, 1f, 1f);
	public static final InterpolationFunction easeOut = new CubicBezierInterpolationFunction(0f, 0f, 0.58f, 1.0f);
	public static final InterpolationFunction easeInOut = new CubicBezierInterpolationFunction(0f, 0.42f, 0.58f, 1.0f);
	public static final InterpolationFunction ease = new CubicBezierInterpolationFunction(0f, 0.25f, 0.25f, 1.0f);

	public static InterpolationFunction cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolationFunction(p0, p1, p2, p3);
	}

	public static InterpolationFunction quadratic(float p0, float p1, float p2) {
		return new QuadraticBezierInterpolationFunction(p0, p1, p2);
	}

	public static InterpolationFunction ease() {
		return ease;
	}

	public static InterpolationFunction linear() {
		return linear;
	}

	public static InterpolationFunction easeIn() {
		return easeIn;
	}

	public static InterpolationFunction easeOut() {
		return easeOut;
	}

	public static InterpolationFunction easeInOut() {
		return easeInOut;
	}

}
