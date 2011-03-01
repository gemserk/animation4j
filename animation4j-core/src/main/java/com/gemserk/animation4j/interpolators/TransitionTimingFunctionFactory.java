package com.gemserk.animation4j.interpolators;

public class TransitionTimingFunctionFactory {
	
	public static CubicBezierInterpolator cubicBezier(float p0, float p1, float p2, float p3) {
		return new CubicBezierInterpolator(p0, p1, p2, p3);
	}
	
	public static CubicBezierInterpolator easeIn() {
		return new CubicBezierInterpolator(0.42f, 0f, 1.0f, 1.0f);
	}

}
