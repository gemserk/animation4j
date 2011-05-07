package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

/**
 * Abstract class to handle Transition changes.
 * 
 * @author acoppes
 * 
 */
public class TransitionEventHandler<T> {

	/**
	 * Called whenever the monitored Transition was started.
	 * 
	 * @param transition
	 *            The transition being monitored.
	 */
	public void onTransitionStarted(Transition<T> transition) {

	}

	/**
	 * Called whenever the monitored Transition was finished.
	 * 
	 * @param transition
	 *            The transition being monitored.
	 */
	public void onTransitionFinished(Transition<T> transition) {

	}

}