package com.gemserk.animation4j.states;

import java.util.HashMap;
import java.util.Map;

public class StateMachine<K, T> {

	static class Transition<T> {

		private final T sourceState;

		private final T targetState;

		public T getSourceState() {
			return sourceState;
		}

		public T getTargetState() {
			return targetState;
		}

		public Transition(T sourceState, T targetState) {
			this.sourceState = sourceState;
			this.targetState = targetState;

		}

	}

	Map<K, T> states = new HashMap<K, T>();

	Map<K, Transition<T>> transitions = new HashMap<K, Transition<T>>();

	T currentState;

	public void addState(K id, T state) {
		states.put(id, state);
		if (currentState == null)
			currentState = state;
	}

	public T getCurrentState() {
		return currentState;
	}

	public void handleTransitionCondition(String transitionId) {
		Transition<T> transition = transitions.get(transitionId);
		if (transition != null)
			currentState = transition.getTargetState();
	}

	public void addTransition(K transitionId, K sourceStateId, K targetStateId) {
		transitions.put(transitionId, new Transition<T>(states.get(sourceStateId), states.get(targetStateId)));
	}

}
