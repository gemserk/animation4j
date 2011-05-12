package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

public class TransitionsExample {

	public static void main(String[] args) throws InterruptedException {
		// register a type converter for Vector2f class.
		Converters.register(Vector2f.class, new Vector2fConverter());

		System.out.println("using default system time provider");
		testWithDefaultSystemProvider();

		System.out.println("now using a custom user time provider");
		testWithUserTimeProvider();
	}

	protected static void testWithDefaultSystemProvider() throws InterruptedException {

		// You could create a transition using the interpolation functions you want for each variable of the class,
		// by default transitions will use a SystemTimeProvider.
		InterpolationFunction[] functions = { InterpolationFunctions.easeOut(), InterpolationFunctions.easeIn() };
		Transition<Vector2f> transition = Transitions.transitionBuilder(new Vector2f(0f, 0f)).functions(functions).build();

		// you can build transitions using default linear interpolation functions.
		Transition<Vector2f> anotherTransition = Transitions.transitionBuilder(new Vector2f(0f, 0f)).build();

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

		InterpolationFunction[] functions = { InterpolationFunctions.easeOut(), InterpolationFunctions.easeIn() };

		// In this case, we are using a custom time provider.
		Transition<Vector2f> transition = Transitions.transitionBuilder(new Vector2f(0f, 0f)).functions(functions).timeProvider(timeProvider).build();

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
