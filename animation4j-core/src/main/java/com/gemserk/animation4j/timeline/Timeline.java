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

	public Timeline(float duration, Map<String, TimelineValue> timelineValues) {
		this.duration = duration;
		this.values = timelineValues;
	}

	public Object getValue(float time, String name) {
		return values.get(name).getValue(time);
	}

	public TimelineValue getTimelineValue(String key) {
		return values.get(key);
	}
	
	public TimelineIterator getIterator() {
		return new TimelineIterator(values);
	}
}