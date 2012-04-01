package com.gemserk.animation4j.easing;

public class EasingFunctions {

	public static final EasingFunction linear = new EasingFunction() {
		@Override
		public float calculate(float t, float b, float c, float d) {
			return c * t / d + b;
		}
	};

	public static class Cubic {

		public static final EasingFunction out = new EasingFunction() {
			@Override
			public float calculate(float t, float b, float c, float d) {
				return c * ((t = t / d - 1) * t * t + 1) + b;
			}
		};

	}

}
