package com.gemserk.animation4j.interpolator.function;

public class InterpolatorFunctionFactory {

	private static InterpolationFunction linearInterpolatorFunction = new LinearBezierInterpolationFunction(0f, 1f);

	private static InterpolationFunction easeInInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0.42f, 1f, 1f);

	private static InterpolationFunction easeOutInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0f, 0.58f, 1.0f);

	private static InterpolationFunction easeInOutInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0.42f, 0.58f, 1.0f);

	private static InterpolationFunction easeInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0.25f, 0.25f, 1.0f);

	public static InterpolationFunction cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolatorFunction(p0, p1, p2, p3);
	}

	public static InterpolationFunction quadratic(float p0, float p1, float p2) {
		return new QuadraticBezierInterpolatorFunction(p0, p1, p2);
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
