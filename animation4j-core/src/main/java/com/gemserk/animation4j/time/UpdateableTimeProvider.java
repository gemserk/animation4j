package com.gemserk.animation4j.time;

/**
 * An implementation of the TimeProvider interface which returns a time updateable by the user.
 * 
 * @author acoppes
 */
public class UpdateableTimeProvider implements TimeProvider {

	float time = 0;

	public float getTime() {
		return time;
	}

	/**
	 * Updates the current time of the time provider by the specified value.
	 * 
	 * @param time
	 *            The time increment value.
	 */
	public void update(float time) {
		this.time += time;
	}

}