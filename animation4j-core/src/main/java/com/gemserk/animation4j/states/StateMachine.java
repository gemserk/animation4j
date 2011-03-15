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
		
		private void enterState() {
			enterState(sourceState, targetState);
		}
		
		/**
		 * Called after entered targetState, after sourceState was left.
		 * @param sourceState
		 * @param targetState
		 */
		protected void enterState(T sourceState, T targetState) {
			
		}

		private void leaveState() {
			leaveState(sourceState, targetState);
		}

		/**
		 * Called before sourceState was left, and targetState will be the next state.
		 * @param sourceState
		 * @param targetState
		 */
		protected void leaveState(T sourceState, T targetState) {
			
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

			transition.leaveState();
			currentState = transition.getTargetState();
			transition.enterState();
			
			return;
		}
	}
	
	public void addTransition(StateTransition<T> transition) {
		stateTransitions.add(transition);
	}
}
