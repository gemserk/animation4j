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
	 * As all transitions work over mutable objects now, this method allow the user to change the mutable object so the transition could be reused for multiple objects in an easy way.
	 * 
	 * @param t
	 *            The new object to use for the transition.
	 */
	void setObject(T t);

	/**
	 * Instantly modifies current value of the transition to the specified value and stops the interpolation.
	 * 
	 * @param t
	 *            The value to set.
	 */
	void start(T t);

	/**
	 * Instantly modifies current value of the transition to the specified value and stops the interpolation.
	 * 
	 * @param t
	 *            The value to set.
	 */
	void startWithFloatArray(float... t);

	/**
	 * Start an interpolation from a to b in the specified time.
	 * 
	 * @param time
	 *            The time in seconds to set the new value. If time is zero, then value is applied directly.
	 * @param t
	 *            The wanted new value.
	 */
	void start(float time, T t);

	/**
	 * Starts an interpolation using the current value as the interpolation starting value and the specified value as the interpolation ending value.
	 * 
	 * @param time
	 *            The time of the transition.
	 * @param value
	 *            The new interpolation ending value.
	 */
	void startWithFloatArray(float time, float... value);

	/**
	 * Modifies the starting value of the transition. If a transition is being performed, interpolation is calculated using the new value set.
	 * 
	 * @param t
	 *            The value to set to the starting transition value.
	 */
	void setStartingValue(T t);

	/**
	 * Modifies the starting value of the transition. If a transition is being performed, interpolation is calculated using the new value set.
	 * 
	 * @param value
	 *            The value to set to the starting transition value.
	 */
	void setStartingValue(float[] value);

	/**
	 * Modifies the ending value of the transition. If a transition is being performed, interpolation is calculated using the new value set.
	 * 
	 * @param t
	 *            The value to set to the ending transition value.
	 */
	void setEndingValue(T t);

	/**
	 * Modifies the ending value of the transition. If a transition is being performed, interpolation is calculated using the new value set.
	 * 
	 * @param value
	 *            The value to set to the ending transition value.
	 */
	void setEndingValue(float[] value);

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

	/**
	 * Returns the current speed of the Transition.
	 */
	float getSpeed();

	/**
	 * Sets the specified speed for the transition.
	 * 
	 * @param speed
	 *            The speed to be set.
	 */
	void setSpeed(float speed);

}