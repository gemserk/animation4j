package com.gemserk.animation4j.interpolator.function;

public class InterpolationFunctions {

	public static final InterpolationFunction linear = new LinearInterpolationFunction();

	public static final InterpolationFunction cubicEaseIn = new CubicBezierInterpolationFunction(0f, 0.42f, 1f, 1f);
	public static final InterpolationFunction cubicEaseOut = new CubicBezierInterpolationFunction(0f, 0f, 0.58f, 1.0f);
	public static final InterpolationFunction cubicEaseInOut = new CubicBezierInterpolationFunction(0f, 0.42f, 0.58f, 1.0f);
	public static final InterpolationFunction cubicEase = new CubicBezierInterpolationFunction(0f, 0.25f, 0.25f, 1.0f);

	static final InterpolationFunction quadraticEaseIn = new QuadraticBezierInterpolationFunction(0f, 1f, 1f);
	static final InterpolationFunction quadraticEaseOut = new QuadraticBezierInterpolationFunction(0f, 0f, 1f);

	public static final InterpolationFunction step = new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			return Math.round(t);
		}
	};

	public static InterpolationFunction cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolationFunction(p0, p1, p2, p3);
	}

	public static InterpolationFunction quadratic(float p0, float p1, float p2) {
		return new QuadraticBezierInterpolationFunction(p0, p1, p2);
	}

	public static InterpolationFunction linear() {
		return linear;
	}

	public static InterpolationFunction ease() {
		return cubicEase;
	}

	public static InterpolationFunction easeIn() {
		return cubicEaseIn;
	}

	public static InterpolationFunction easeOut() {
		return cubicEaseOut;
	}

	public static InterpolationFunction easeInOut() {
		return cubicEaseInOut;
	}

	public static InterpolationFunction quadraticEaseIn() {
		return quadraticEaseIn;
	}

	public static InterpolationFunction quadraticEaseOut() {
		return quadraticEaseIn;
	}
}
