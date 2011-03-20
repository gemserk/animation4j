package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.transitions.Transition;

public class TransitionsExample {

	// For a given class, for example a vector2f mutable class
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

	// You will have to create a converter for your custom object, or use common converters provided by the library.
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
		// as converters are stateless, you could reuse it in several interpolators.
		Vector2fConverter vector2fConverter = new Vector2fConverter();

		// create an interpolator specifying a converter and the functions to be used for each variable (x and y in the example)
		Interpolator<Vector2f> vector2fInterpolator = Interpolators.interpolator(vector2fConverter, //
				InterpolatorFunctionFactory.easeOut(), //
				InterpolatorFunctionFactory.easeIn());

		// you could also create interpolators using default linear interpolation functions ->
		Interpolator<Vector2f> anotherInterpolator = Interpolators.interpolator(vector2fConverter);

		// After the interpolator has been created, you could create a transition using it
		// by default transitions will use a SystemTimeProvider.
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(0f, 0f), vector2fInterpolator);

		System.out.println("Transition value: " + transition.get());

		transition.set(new Vector2f(100f, 100f), 500);

		// could have a different value from (0,0) since some time could have passed.
		System.out.println("Transition value: " + transition.get());
		Thread.sleep(300);
		System.out.println("Transition value: " + transition.get());
		Thread.sleep(200);
		System.out.println("Transition value: " + transition.get());
		
		transition.set(new Vector2f(200f, 200f), 0);
		Thread.sleep(1);
		System.out.println("Transition value: " + transition.get());
	}

}
