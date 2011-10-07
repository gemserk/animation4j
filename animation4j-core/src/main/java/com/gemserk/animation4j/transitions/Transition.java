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
	 *            The time in seconds to set the new value. If time is zero, then value is applied directly.
	 */
	void set(T t, float time);

	/**
	 * Returns true whenever the transition was started, false otherwise.
	 */
	boolean isStarted();

	/**
	 * Returns true whenever the transition was finished, false otherwise.
	 */
	boolean isFinished();

	/**
	 * Updates the transition interpolating from a to b using the specified delta time.
	 * 
	 * @param delta
	 *            The time to update the transition.
	 */
	void update(float delta);

}