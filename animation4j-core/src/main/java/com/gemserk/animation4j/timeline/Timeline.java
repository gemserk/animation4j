package com.gemserk.animation4j.timeline;

import java.util.ArrayList;

/**
 * Represents the concept of a time line with a group of values. Each time line value will handle its own key frames.
 * 
 * @author acoppes
 */
@SuppressWarnings("rawtypes")
public class Timeline {

	private ArrayList<TimelineValue> values;

	/**
	 * Build a new time line using the specified values.
	 */
	public Timeline(ArrayList<TimelineValue> values) {
		this.values = values;
	}

	/**
	 * Moves the time line to the specified time, internally modifies all values.
	 * 
	 * @param time
	 *            The time where you want the time line to be.
	 */
	public void move(float time) {
		for (int i = 0; i < values.size(); i++) 
			values.get(i).setTime(time);
	}

	/**
	 * Returns the value of an element identified by name in the specified time.
	 * 
	 * @param time
	 *            The time to use to get the value of the element.
	 * @param name
	 *            The identifier of the element in the time line.
	 * @return The value of the element for that given time.
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T> T getValue(float time, String name) {
		// return (T) values.get(name).getValue(time);
		throw new RuntimeException("deprecated");
	}

	/**
	 * Returns the value of an element identified by name in the specified time.
	 * 
	 * @param time
	 *            The time to use to get the value of the element.
	 * @param name
	 *            The identifier of the element in the time line.
	 * @return The value of the element for that given time.
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T> T getValue(int time, String name) {
		// return (T) values.get(name).getValue((float) time * 0.001f);
		throw new RuntimeException("deprecated");
	}

	/**
	 * Returns an iterator which lets you iterate between the time line values.
	 */
	@Deprecated
	public TimelineIterator getIterator() {
		// return new TimelineIterator(values);
		throw new RuntimeException("deprecated");
	}

}