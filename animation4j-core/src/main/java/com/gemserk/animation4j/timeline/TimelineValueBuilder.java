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
	public void keyFrame(float time, T value, Interpolator<T> interpolator) {

		if (interpolator == null)
			interpolator = LinearInterpolatorFactory.inferLinearInterpolator(value);

		timelineValue.addKeyFrame(time, value, interpolator);

		duration = Math.max(duration, time);

	}

	/**
	 * Sets an Interpolator for the TimelineValue, use LinearInterpolatorFactory for linearInterpolators.
	 * @param interpolator
	 * @deprecated
	 */
	public void interpolator(Interpolator<T> interpolator) {
		
	}

}