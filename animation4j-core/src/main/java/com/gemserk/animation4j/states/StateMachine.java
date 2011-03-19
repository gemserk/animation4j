package com.gemserk.animation4j.states;

import java.util.ArrayList;

public class StateMachine<K, T> {

	public static class StateTransition<T> {

		private final T sourceState;

		private final T targetState;

		private final StateCondition<T> condition;

		public T getSourceState() {
			return sourceState;
		}

		public T getTargetState() {
			return targetState;
		}

		public StateCondition<T> getCondition() {
			return condition;
		}

		public StateTransition(StateCondition<T> condition, T sourceState, T targetState) {
			this.condition = condition;
			this.sourceState = sourceState;
			this.targetState = targetState;
		}

		private void afterTransition() {
			afterTransition(sourceState, targetState);
		}

		/**
		 * Called after transition was performed, after entered targetState and sourceState was left.
		 * 
		 * @param sourceState
		 *            The source state.
		 * @param currentState
		 *            The current state.
		 */
		protected void afterTransition(T sourceState, T currentState) {

		}

		private void beforeTransition() {
			beforeTransition(sourceState, targetState);
		}

		/**
		 * Called before the transition was performed, before sourceState was left.
		 * 
		 * @param currentState
		 *            The current state.
		 * @param targetState
		 *            The target state.
		 */
		protected void beforeTransition(T currentState, T targetState) {

		}

	}

	public static class StateCondition<T> {

		public boolean matches(T sourceState, T targetState) {
			return false;
		}

	}

	ArrayList<StateTransition<T>> stateTransitions = new ArrayList<StateTransition<T>>();

	T currentState;

	public void setCurrentState(T state) {
		currentState = state;
	}

	public T getCurrentState() {
		return currentState;
	}

	public void checkTransitionConditions() {

		for (int i = 0; i < stateTransitions.size(); i++) {
			StateTransition<T> transition = stateTransitions.get(i);
			if (transition.getSourceState() != getCurrentState())
				continue;
			StateCondition<T> stateCondition = transition.getCondition();
			if (!stateCondition.matches(getCurrentState(), transition.getTargetState()))
				continue;

			transition.beforeTransition();
			currentState = transition.getTargetState();
			transition.afterTransition();

			return;
		}
	}

	public void addTransition(StateTransition<T> transition) {
		stateTransitions.add(transition);
	}
}
