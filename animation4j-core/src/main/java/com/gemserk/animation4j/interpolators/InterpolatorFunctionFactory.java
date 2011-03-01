package com.gemserk.animation4j.interpolators;

public class InterpolatorFunctionFactory {
	
	private static InterpolatorFunction linearInterpolatorFunction = new LinearBezierInterpolatorFunction(0f, 1f);
	
	private static InterpolatorFunction easeInInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0.42f, 1f, 1f);

	private static InterpolatorFunction easeOutInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0f, 0.58f, 1.0f);

	private static InterpolatorFunction easeInOutInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0.42f, 0.58f, 1.0f);

	private static InterpolatorFunction easeInterpolatorFunction = new CubicBezierInterpolatorFunction(0f, 0.25f, 0.25f, 1.0f);
	
	public static InterpolatorFunction cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolatorFunction(p0, p1, p2, p3);
	}
	
	public static InterpolatorFunction ease() {
		return easeInterpolatorFunction;
	}

	public static InterpolatorFunction linear() {
		return linearInterpolatorFunction;
	}

	public static InterpolatorFunction easeIn() {
		return easeInInterpolatorFunction;
	}

	public static InterpolatorFunction easeOut() {
		return easeOutInterpolatorFunction;
	}

	public static InterpolatorFunction easeInOut() {
		return easeInOutInterpolatorFunction;
	}

}
