package com.gemserk.commons.animation.timeline;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class Timeline {

	private Map<String, TimelineValue> timelineValues;

	private float duration;

	private float delay;

	public float getDuration() {
		return duration;
	}
	
	public float getDelay() {
		return delay;
	}

	public Timeline(float duration, float delay, Map<String, TimelineValue> timelineValues) {
		this.duration = duration;
		this.delay = delay;
		this.timelineValues = timelineValues;
	}

	public Object getValue(float time, String name) {
		return timelineValues.get(name).getValue(time - delay);
	}

	public Map<String, TimelineValue> getTimelineValues() {
		return timelineValues;
	}
}