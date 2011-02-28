package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolators.Interpolator;

public class TimelineValueBuilder<T> {

	String name;
	
	float duration = 0f;

	public void setName(String name) {
		this.name = name;
	}

	TimelineValue<T> timelineValue = new TimelineValue<T>();

	public TimelineValue<T> build() {
		return timelineValue;
	}

	public String getName() {
		return name;
	}
	
	public float getDuration() {
		return duration;
	}

	@SuppressWarnings("unchecked")
	public void keyFrame(float time, T value) {
		timelineValue.addKeyFrame(time, value);
		
		duration = Math.max(duration, time);

		if (timelineValue.getInterpolator() == null) {
			interpolator(LinearInterpolatorFactory.inferLinearInterpolator(value));
		}
	}

	/**
	 * Sets an Interpolator for the TimelineValue, use LinearInterpolatorFactory for linearInterpolators.
	 * 
	 * @param interpolator
	 */
	public void interpolator(Interpolator<T> interpolator) {
		timelineValue.setInterpolator(interpolator);
	}

}