package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;

public class Synchronizers {

	private static final Synchronizer synchronizer = new Synchronizer();

	public static Synchronizer getInstance() {
		return synchronizer;
	}

	/**
	 * Performs the synchronization of all the objects with the corresponding transitions registered by calling transition() method.
	 */
	public static void synchronize() {
		getInstance().synchronize();
	}

	/**
	 * Performs a synchronization of all internal objects with the corresponding registered transitions using the specified delta. It will only work (for now) for those transitions registered using a TransitionBuilder.
	 * 
	 * @param delta
	 *            The delta time in Seconds to use to synchronize.
	 */
	public static void synchronize(float delta) {
		getInstance().synchronize(delta);
	}

	/**
	 * Adds the transition to be updated by the synchronizer.
	 * 
	 * @param transition
	 *            The transition to be registered.
	 */
	@SuppressWarnings("rawtypes")
	public static void transition(Transition transition) {
		getInstance().transition(transition);
	}
	
	/**
	 * Convenient method which to avoid calling transition(transition) and then monitor(transition, transitionEventHandler).
	 * 
	 * @param transition
	 *            The Transition to be registered.
	 * @param transitionEventHandler
	 *            The TransitionEventHandler to be called when the Transition changes its state.
	 */
	@SuppressWarnings("rawtypes")
	public static void transition(Transition transition, TransitionEventHandler transitionEventHandler) {
		getInstance().transition(transition, transitionEventHandler);
	}

	/**
	 * Monitors the specified Transition by calling the specified TransitionEventHandler whenever the Transition changes its state.
	 * 
	 * @param transition
	 *            The Transition to be monitored.
	 * @param transitionEventHandler
	 *            The TransitionEventHandler to be called when the Transition changes its state.
	 */
	@SuppressWarnings("rawtypes")
	public static void monitor(Transition transition, TransitionEventHandler transitionEventHandler) {
		getInstance().monitor(transition, transitionEventHandler);
	}

}