package com.gemserk.animation4j.transitions.sync;

/**
 * Defines a way to synchronize a transition value with an object.
 * 
 * @author acoppes
 */
public interface TransitionObjectSynchronizer {

	/**
	 * Synchronizes the transition value with an object using the custom logic of each implementation.
	 */
	void synchronize();

	/**
	 * Returns true if the transition has finished.
	 */
	boolean isFinished();

}