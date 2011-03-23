package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.states.StateMachine;
import com.gemserk.animation4j.states.StateTransition;
import com.gemserk.animation4j.states.StateTransitionCondition;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

public class StateMachineExample {

	public static void main(String[] args) throws InterruptedException {
		exampleStateMachine();
	}

	protected static void exampleStateMachine() throws InterruptedException {
		Vector2fConverter vector2fConverter = new Vector2fConverter();

		Vector2f a = new Vector2f(100, 100);
		Vector2f b = new Vector2f(200, 200);

		StateMachine<Vector2f> stateMachine = new StateMachine<Vector2f>(a);
		
		final Transition<Vector2f> transition = Transitions.transition(a, vector2fConverter);
		transition.set(b, 2000);

		stateMachine.addTransition(new StateTransition<Vector2f>(new StateTransitionCondition<Vector2f>() {
			
			@Override
			public boolean matches(Vector2f sourceState, Vector2f targetState) {
				boolean shouldPerform = transition.get().distanceSq(targetState) < 1f;
				if (shouldPerform)
					System.out.println("performing state transition");
				return shouldPerform;
			}

		}, a, b) {
			
			@Override
			protected void afterTransition(Vector2f sourceState, Vector2f currentState) {
				transition.set(sourceState, 2000);
			}
			
		});
		
		stateMachine.addTransition(new StateTransition<Vector2f>(new StateTransitionCondition<Vector2f>() {
			
			@Override
			public boolean matches(Vector2f sourceState, Vector2f targetState) {
				boolean shouldPerform = transition.get().distanceSq(targetState) < 1f;
				if (shouldPerform)
					System.out.println("performing state transition");
				return shouldPerform;
			}

		}, b, a) {

			@Override
			protected void afterTransition(Vector2f sourceState, Vector2f currentState) {
				transition.set(sourceState, 2000);
			}
			
		});
		
		int time = 0;
		
		while (time < 10000) {
			System.out.println("currentState: " + stateMachine.getCurrentState());
			System.out.println("transition: " + transition.get());
			
			Thread.sleep(100);
			stateMachine.checkTransitionConditions();
			time+=100;
		}
		
	}

}
