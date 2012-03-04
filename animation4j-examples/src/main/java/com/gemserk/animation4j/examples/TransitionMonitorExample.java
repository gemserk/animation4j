package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.event.TransitionMonitor;

public class TransitionMonitorExample {

	static Vector2fConverter vector2fConverter = new Vector2fConverter();

	public static void main(String[] args) throws InterruptedException {
		testWithDefaultSystemProvider();
	}

	protected static void testWithDefaultSystemProvider() throws InterruptedException {
		TransitionMonitor transitionMonitor = new TransitionMonitor();
		
		Vector2f object1 = new Vector2f(0f, 0f);

		Transition<Vector2f> transition = Transitions.transition(object1, vector2fConverter) //
				.end(0.5f, 100f, 100f) //
				.build();

		transitionMonitor.monitor(transition);

		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());

		transitionMonitor.update();

		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());

		transition.start(500, new Vector2f(100f, 100f));
		transition.update(300);

		transitionMonitor.update();

		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());

		transition.update(300);

		transitionMonitor.update();

		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());

		transition.update(200);

		transitionMonitor.update();

		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());
	}

}
