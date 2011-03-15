package com.gemserk.animation4j.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

	}
	
	public static class StateCondition<T> {
		
		public boolean matches(T sourceState, T targetState) {
			return false;
		}
		
		public void perform(T sourceState, T targetState) {
			
		}
		
	}

	Map<K, T> states = new HashMap<K, T>();

	ArrayList<StateTransition<T>> stateTransitions = new ArrayList<StateTransition<T>>();

	T currentState;

	public void addState(K id, T state) {
		states.put(id, state);
		if (currentState == null)
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
			transition.condition.perform(getCurrentState(), transition.getTargetState());
			currentState = transition.getTargetState();
			return;
		}
	}
	
	public void addTransition(StateTransition<T> transition) {
		stateTransitions.add(transition);
	}
}
