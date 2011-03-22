package com.gemserk.animation4j.examples;

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

		// You could create a transition using the interpolation functions you want for each variable of the class,
		// by default transitions will use a SystemTimeProvider.
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(0f, 0f), vector2fConverter, // 
				InterpolatorFunctionFactory.easeOut(), //
				InterpolatorFunctionFactory.easeIn());

		// you can build transitions using default linear interpolation functions.
		Transition<Vector2f> anotherTransition = Transitions.transition(new Vector2f(0f, 0f), vector2fConverter);

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

		// In this case, we are using a custom time provider.
		Transitions.timeProvider = timeProvider;

		// as converters are stateless, you could reuse it in several interpolators.
		Vector2fConverter vector2fConverter = new Vector2fConverter();

		Transition<Vector2f> transition = Transitions.transition(new Vector2f(0f, 0f), vector2fConverter, // 
				InterpolatorFunctionFactory.easeOut(), //
				InterpolatorFunctionFactory.easeIn());

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
