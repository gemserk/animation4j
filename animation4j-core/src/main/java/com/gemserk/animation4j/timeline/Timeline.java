package com.gemserk.animation4j.timeline;

import java.util.Map;

/**
 * Represents the concept of a time line with a group of values.
 * 
 * @author acoppes
 */
@SuppressWarnings("rawtypes")
public class Timeline {

	private Map<String, TimelineValue> timelineValues;

	private float duration;

	public float getDuration() {
		return duration;
	}

	public Timeline(float duration, Map<String, TimelineValue> timelineValues) {
		this.duration = duration;
		this.timelineValues = timelineValues;
	}

	public Object getValue(float time, String name) {
		return timelineValues.get(name).getValue(time);
	}

	public Map<String, TimelineValue> getTimelineValues() {
		return timelineValues;
	}

	public TimelineIterator getIterator() {
		return new TimelineIterator(this);
	}

	public TimelineValue getTimelineValue(String key) {
		return timelineValues.get(key);
	}
}