package com.gemserk.animation4j.timeline;

/**
 * Provides an API to work with values of a Timeline.
 * 
 * @author acoppes
 * 
 */
public interface TimelineValue {

	/**
	 * Adds a new KeyFrame to the TimelineValue.
	 * 
	 * @throws IllegalArgumentException
	 *             If the KeyFrame internal value doesn't match the expected size.
	 */
	void addKeyFrame(KeyFrame keyFrame);

	/**
	 * Internally modifies the value to the specified time using the KeyFrames.
	 * 
	 * @param time
	 *            The time to use when calculating the value.
	 */
	void setTime(float time);

}