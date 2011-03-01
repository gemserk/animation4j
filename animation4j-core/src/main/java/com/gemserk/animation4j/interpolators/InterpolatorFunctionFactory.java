package com.gemserk.animation4j.interpolators;

public class InterpolatorFunctionFactory {
	
	public static InterpolatorFunction cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolatorFunction(p0, p1, p2, p3);
	}
	
	public static InterpolatorFunction ease() {
		return new CubicBezierInterpolatorFunction(0.25f, 0.1f, 0.25f, 1.0f);
	}

	public static InterpolatorFunction linear() {
		return new CubicBezierInterpolatorFunction(0f, 0f, 1f, 1f);
	}

	public static InterpolatorFunction easeIn() {
		return new CubicBezierInterpolatorFunction(0.42f, 0f, 1.0f, 1.0f);
	}

	public static InterpolatorFunction easeOut() {
		return new CubicBezierInterpolatorFunction(0f, 0f, 0.58f, 1.0f);
	}

	public static InterpolatorFunction easeInOut() {
		return new CubicBezierInterpolatorFunction(0.42f, 0f, 0.58f, 1.0f);
	}

}
