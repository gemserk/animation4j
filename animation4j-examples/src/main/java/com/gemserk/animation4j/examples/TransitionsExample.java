package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

public class TransitionsExample {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("using default system time provider");
		testWithDefaultSystemProvider();
		
		System.out.println("now using a custom user time provider");
		testWithUserTimeProvider();
	}

	protected static void testWithDefaultSystemProvider() throws InterruptedException {
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
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(0f, 0f), vector2fInterpolator, vector2fConverter);

		System.out.println("Transition value: " + transition.get());

		transition.set(new Vector2f(100f, 100f), 500);

		// could have a different value from (0,0) since some time could have passed.
		System.out.println("Transition value: " + transition.get());
		Thread.sleep(300);
		System.out.println("Transition value: " + transition.get());
		Thread.sleep(200);
		System.out.println("Transition value: " + transition.get());

		transition.set(new Vector2f(200f, 200f), 0);
		System.out.println("Transition value: " + transition.get());
	}

	protected static void testWithUserTimeProvider() {
		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();

		Transitions.timeProvider = timeProvider;

		// as converters are stateless, you could reuse it in several interpolators.
		Vector2fConverter vector2fConverter = new Vector2fConverter();

		// create an interpolator specifying a converter and the functions to be used for each variable (x and y in the example)
		Interpolator<Vector2f> vector2fInterpolator = Interpolators.interpolator(vector2fConverter, //
				InterpolatorFunctionFactory.easeOut(), //
				InterpolatorFunctionFactory.easeIn());

		// you could also create interpolators using default linear interpolation functions ->
		Interpolator<Vector2f> anotherInterpolator = Interpolators.interpolator(vector2fConverter);

		// After the interpolator has been created, you could create a transition using it
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(0f, 0f), vector2fInterpolator, vector2fConverter);

		System.out.println("Transition value: " + transition.get());

		transition.set(new Vector2f(100f, 100f), 500);

		System.out.println("Transition value: " + transition.get());
		timeProvider.update(300);
		System.out.println("Transition value: " + transition.get());
		timeProvider.update(200);
		System.out.println("Transition value: " + transition.get());

		transition.set(new Vector2f(200f, 200f), 0);
		System.out.println("Transition value: " + transition.get());
	}

}
