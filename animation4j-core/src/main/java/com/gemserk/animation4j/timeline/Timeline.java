package com.gemserk.animation4j.timeline;

import java.util.Map;

/**
 * Represents the concept of a time line with a group of values. Each time line value will handle its own key frames.
 * 
 * @author acoppes
 */
@SuppressWarnings("rawtypes")
public class Timeline {

	private Map<String, TimelineValue> values;

	private float duration;

	public float getDuration() {
		return duration;
	}

	public Timeline(float duration, Map<String, TimelineValue> values) {
		this.duration = duration;
		this.values = values;
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
	public Object getValue(float time, String name) {
		return values.get(name).getValue(time);
	}

	public TimelineIterator getIterator() {
		return new TimelineIterator(values);
	}
}