package com.gemserk.animation4j.states;

public class StateTransition<T> {

	private final T sourceState;

	private final T targetState;

	private final StateTransitionCondition<T> condition;

	public T getSourceState() {
		return sourceState;
	}

	public T getTargetState() {
		return targetState;
	}

	public StateTransitionCondition<T> getCondition() {
		return condition;
	}

	/**
	 * Builds a state transition from sourceState to targetState whenever the given condition matches.
	 * 
	 * @param condition
	 *            The condition to use to test if the transition should be performed.
	 * @param sourceState
	 *            The source state.
	 * @param targetState
	 *            The target state.
	 */
	public StateTransition(StateTransitionCondition<T> condition, T sourceState, T targetState) {
		this.condition = condition;
		this.sourceState = sourceState;
		this.targetState = targetState;
	}

	void afterTransition() {
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

	void beforeTransition() {
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