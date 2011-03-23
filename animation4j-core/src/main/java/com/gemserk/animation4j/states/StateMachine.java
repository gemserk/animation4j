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
	 * Returns the current state of the state machine.
	 */
	public T getCurrentState() {
		return currentState;
	}
	
	/**
	 * Creates a new state machine starting in the specified initial state.
	 * @param initialState The state to start the state machine.
	 */
	public StateMachine(T initialState) {
		this.currentState = initialState;
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

	/**
	 * Adds a new state transition to the state machine.
	 * 
	 * @param stateTransition
	 *            The state transition to be added.
	 */
	public void addTransition(StateTransition<T> stateTransition) {
		stateTransitions.add(stateTransition);
	}
}
