package com.gemserk.animation4j.transitions;

/**
 * Provides an interface of a transition of a value.
 * 
 * @param <T>
 *            The type of the transition value.
 * @author acoppes
 */
public interface Transition<T> {

	/**
	 * Returns the current value of the transition.
	 * 
	 * @return The current value of the transition.
	 */
	T get();

	/**
	 * Start an interpolation from a to b in the specified default time.
	 * 
	 * @param t
	 *            The wanted new value.
	 */
	void set(T t);

	/**
	 * Start an interpolation from a to b in the specified time.
	 * 
	 * @param t
	 *            The wanted new value.
	 * @param time
	 *            The time to set the new value.
	 */
	void set(T t, int time);

}