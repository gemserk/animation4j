package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

public class TransitionsExample {
	
	static Vector2fConverter vector2fConverter = new Vector2fConverter();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Tests how to update a transition");
		testUsingTransitionDirectly();
	}

	@SuppressWarnings("unchecked")
	protected static void testUsingTransitionDirectly() {
		InterpolationFunction[] functions = { InterpolationFunctions.easeOut(), InterpolationFunctions.easeIn() };

		Vector2f object1 = new Vector2f(0f, 0f);
		
		Transition<Vector2f> transition = Transitions.transition(object1, vector2fConverter) //
			.functions(functions) //
			.build();
		
		Transitions.transition(object1, vector2fConverter) //
			.build();
		
		System.out.println("Transition value: " + transition.get());

		transition.start(0.5f, new Vector2f(100f, 100f));

		System.out.println("Transition value: " + transition.get());
		transition.update(0.3f);
		System.out.println("Transition value: " + transition.get());
		transition.update(0.2f);
		System.out.println("Transition value: " + transition.get());

		transition.start(0f, new Vector2f(200f, 200f));
		System.out.println("Transition value: " + transition.get());
	}

}
