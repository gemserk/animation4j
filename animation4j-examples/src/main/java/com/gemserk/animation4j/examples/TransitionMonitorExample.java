package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.event.TransitionMonitor;

public class TransitionMonitorExample {

	public static void main(String[] args) throws InterruptedException {
		// register a type converter for Vector2f class.
		Converters.register(Vector2f.class, new Vector2fConverter());
		testWithDefaultSystemProvider();
	}

	protected static void testWithDefaultSystemProvider() throws InterruptedException {
		TransitionMonitor transitionMonitor = new TransitionMonitor();
		Transition<Vector2f> transition = Transitions.transitionBuilder(new Vector2f(0f, 0f)).end(new Vector2f(100f, 100f)).time(500).build();
		transitionMonitor.monitor(transition);
		
		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());

		transitionMonitor.update();
		
		System.out.println("Transition was started: " + transitionMonitor.wasStarted());
		System.out.println("Transition was finished: " + transitionMonitor.wasFinished());
		
		transition.set(new Vector2f(100f, 100f), 500);
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
