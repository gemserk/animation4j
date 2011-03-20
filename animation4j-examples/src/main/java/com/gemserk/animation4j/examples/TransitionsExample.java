package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.transitions.Transition;

public class TransitionsExample {

	static class Vector2f {

		float x, y;

		public Vector2f(float x, float y) {
			set(x, y);
		}

		public void set(float x, float y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "vector(" + x + ", " + y + ")";
		}

	}

	static class Vector2fConverter implements TypeConverter<Vector2f> {

		@Override
		public float[] copyFromObject(Vector2f v, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = v.x;
			x[1] = v.y;
			return x;

		}

		@Override
		public Vector2f copyToObject(Vector2f v, float[] x) {
			if (v == null)
				v = new Vector2f(0, 0);
			v.x = x[0];
			v.y = x[1];
			return v;
		}

		@Override
		public int variables() {
			return 2;
		}

	}

	public static void main(String[] args) throws InterruptedException {

		// this one could be reused any time we want, should be stateless.
		Vector2fConverter vector2fConverter = new Vector2fConverter();

		Interpolator<Vector2f> vector2fInterpolator = Interpolators.interpolator(2, vector2fConverter, //
				InterpolatorFunctionFactory.easeOut(), InterpolatorFunctionFactory.easeIn());

		// By default, transitions use system time provider.
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(0f, 0f), vector2fInterpolator);

		System.out.println("Transition value: " + transition.get());

		transition.set(new Vector2f(100f, 100f), 500);

		System.out.println("Transition value: " + transition.get());
		Thread.sleep(300);
		System.out.println("Transition value: " + transition.get());
		Thread.sleep(200);
		System.out.println("Transition value: " + transition.get());

	}

}
