package com.gemserk.animation4j.states;

import java.util.HashMap;
import java.util.Map;

public class StateMachine<K, T> {

	public static class StateTransition<T> {

		private final T sourceState;

		private final T targetState;

		public T getSourceState() {
			return sourceState;
		}

		public T getTargetState() {
			return targetState;
		}

		public StateTransition(T sourceState, T targetState) {
			this.sourceState = sourceState;
			this.targetState = targetState;

		}

	}

	Map<K, T> states = new HashMap<K, T>();

	Map<K, StateTransition<T>> stateTransitions = new HashMap<K, StateTransition<T>>();

	T currentState;

	public void addState(K id, T state) {
		states.put(id, state);
		if (currentState == null)
			currentState = state;
	}

	public T getCurrentState() {
		return currentState;
	}

	/**
	 * Given a transition condition, it tries to handle the transition, if it exists.
	 * @param transitionCondition - The transition condition to transit to another state.
	 */
	public void handleTransitionCondition(String transitionCondition) {
		StateTransition<T> transition = stateTransitions.get(transitionCondition);
		if (transition != null)
			currentState = transition.getTargetState();
	}

	public void addTransition(K transitionCondition, K sourceStateId, K targetStateId) {
		this.addTransition(transitionCondition, new StateTransition<T>(states.get(sourceStateId), states.get(targetStateId)));
	}

	public void addTransition(K transitionCondition, StateTransition<T> transition) {
		stateTransitions.put(transitionCondition, transition);
	}
}
