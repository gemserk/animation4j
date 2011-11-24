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

}