package com.gemserk.animation4j.states;

/**
 * Determines whenever a transition should be performed. Implement your own conditions by making subclasses of this one.
 * 
 * @author acoppes
 */
public class StateTransitionCondition<T> {

	/**
	 * Checks whenever a condition is true to perform a state transition.
	 * 
	 * @param sourceState
	 *            The current state of the state machine.
	 * @param targetState
	 *            The target state of the related transition.
	 * @return true if the transition should be performed, false otherwise.
	 */
	public boolean matches(T sourceState, T targetState) {
		return false;
	}

}