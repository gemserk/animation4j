package com.gemserk.animation4j.interpolator.function;

public class InterpolationFunctions {

	public static final InterpolationFunction linear = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			return t;
		}
	});

	public static final InterpolationFunction cubicEaseIn = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			return t * t * t;
		}
	});

	public static final InterpolationFunction cubicEaseOut = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			return (t - 1) * (t - 1) * (t - 1) + 1;
		}
	});

	public static final InterpolationFunction cubicEaseInOut = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			float d = 1f;
			if ((t /= d / 2) < 1)
				return 1f / 2 * t * t * t;
			return 1f / 2 * ((t -= 2) * t * t + 2);
		}
	});

	public static final InterpolationFunction quadraticEaseIn = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			return t * t;
		}
	});

	public static final InterpolationFunction quadraticEaseOut = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			return -1f * t * (t - 2);
		}
	});

	public static final InterpolationFunction quadraticEaseInOut = new LimitedInterpolationFunction(new InterpolationFunction() {
		@Override
		public float interpolate(float t) {
			float d = 1f;
			float c = 1f;
			if ((t /= d / 2) < 1)
				return c / 2 * t * t;
			return -c / 2 * ((--t) * (t - 2) - 1);
		}
	});

	public static InterpolationFunction ease() {
		return linear;
	}

	public static InterpolationFunction linear() {
		return linear;
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

}
