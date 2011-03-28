package com.gemserk.animation4j.interpolator.function;

public class InterpolationFunctions {

	private static InterpolationFunction linearInterpolatorFunction = new LinearBezierInterpolationFunction(0f, 1f);

	private static InterpolationFunction easeInInterpolatorFunction = new CubicBezierInterpolationFunction(0f, 0.42f, 1f, 1f);

	private static InterpolationFunction easeOutInterpolatorFunction = new CubicBezierInterpolationFunction(0f, 0f, 0.58f, 1.0f);

	private static InterpolationFunction easeInOutInterpolatorFunction = new CubicBezierInterpolationFunction(0f, 0.42f, 0.58f, 1.0f);

	private static InterpolationFunction easeInterpolatorFunction = new CubicBezierInterpolationFunction(0f, 0.25f, 0.25f, 1.0f);

	public static InterpolationFunction cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolationFunction(p0, p1, p2, p3);
	}

	public static InterpolationFunction quadratic(float p0, float p1, float p2) {
		return new QuadraticBezierInterpolationFunction(p0, p1, p2);
	}

	public static InterpolationFunction ease() {
		return easeInterpolatorFunction;
	}

	public static InterpolationFunction linear() {
		return linearInterpolatorFunction;
	}

	public static InterpolationFunction easeIn() {
		return easeInInterpolatorFunction;
	}

	public static InterpolationFunction easeOut() {
		return easeOutInterpolatorFunction;
	}

	public static InterpolationFunction easeInOut() {
		return easeInOutInterpolatorFunction;
	}

}
