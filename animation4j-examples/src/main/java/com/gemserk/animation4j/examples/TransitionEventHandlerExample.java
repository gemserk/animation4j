package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;
import com.gemserk.animation4j.transitions.event.TransitionMonitor;
import com.gemserk.animation4j.transitions.event.TransitionMonitorProcessor;
import com.gemserk.animation4j.transitions.sync.Synchronizer;

public class TransitionEventHandlerExample {
	
	private static Vector2fConverter vector2fConverter = new Vector2fConverter();
	private static Synchronizer synchronizer = new Synchronizer();

	public static void main(String[] args) throws InterruptedException {
		// register a type converter for Vector2f class.
		testUsingTransitionMonitor();
		testUsingSynchronizers();
	}

	protected static void testUsingTransitionMonitor() throws InterruptedException {
		
		Vector2f object1 = new Vector2f(0f, 0f);

		Transition<Vector2f> transition = Transitions.mutableTransition(object1, vector2fConverter) //
				.end(0.5f, 100f, 100f) //
				.build();
		
		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);

		TransitionMonitorProcessor transitionMonitorProcessor = new TransitionMonitorProcessor();
		transitionMonitorProcessor.setTransitionMonitor(transitionMonitor);
		transitionMonitorProcessor.setTransitionEventHandler(new TransitionEventHandler<Vector2f>() {

			@Override
			public void onTransitionStarted(Transition<Vector2f> transition) {
				System.out.println("Transition started!!! - " + transition.get());
			}

			@Override
			public void onTransitionFinished(Transition<Vector2f> transition) {
				System.out.println("Transition finished!!! - " + transition.get());
			}

		});

		transitionMonitorProcessor.update();
		transition.update(300);
		transitionMonitorProcessor.update();
		transition.update(300);
		transitionMonitorProcessor.update();

	}

	protected static void testUsingSynchronizers() throws InterruptedException {

		TransitionEventHandler<Vector2f> eventHandler = new TransitionEventHandler<Vector2f>() {

			@Override
			public void onTransitionStarted(Transition<Vector2f> transition) {
				System.out.println("Transition started!!! - " + transition.get());
			}

			@Override
			public void onTransitionFinished(Transition<Vector2f> transition) {
				System.out.println("Transition finished!!! - " + transition.get());
			}

		};

		Vector2f object1 = new Vector2f(0f, 0f);

		Transition<Vector2f> transition = Transitions.mutableTransition(object1, vector2fConverter) //
				.end(0.5f, 100f, 100f) //
				.build();
		
		synchronizer.transition(transition, eventHandler);
		
		synchronizer.synchronize(0);
		synchronizer.synchronize(300);
		synchronizer.synchronize(300);
		synchronizer.synchronize();

	}

}
