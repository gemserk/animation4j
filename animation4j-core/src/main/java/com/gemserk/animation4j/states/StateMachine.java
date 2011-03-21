package com.gemserk.animation4j.states;

import java.util.ArrayList;

/**
 * Represents a generic state machine.
 * 
 * @param <T>
 *            The type of the states.
 * @author acoppes
 */
public class StateMachine<T> {

	ArrayList<StateTransition<T>> stateTransitions = new ArrayList<StateTransition<T>>();

	T currentState;

	/**
	 * Sets the current state of the state machine, should be used to set the initial state.
	 */
	public void setCurrentState(T state) {
		currentState = state;
	}

	public T getCurrentState() {
		return currentState;
	}

	/**
	 * Checks all transition conditions for the current state, if a condition returns true, then the related transition is performed.
	 */
	public void checkTransitionConditions() {

		for (int i = 0; i < stateTransitions.size(); i++) {
			StateTransition<T> transition = stateTransitions.get(i);
			if (transition.getSourceState() != getCurrentState())
				continue;
			StateTransitionCondition<T> stateCondition = transition.getCondition();
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
