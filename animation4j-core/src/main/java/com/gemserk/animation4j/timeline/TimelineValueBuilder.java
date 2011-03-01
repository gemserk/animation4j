package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolator.Interpolator;

/**
 * Provides an easy way to create TimelineValues.
 * 
 * @param <T> - The type of the variable of the TimelineValue.
 *
 * @author acoppes
 */
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

	/**
	 * Defines a new key frame in the timeline value.
	 * @param time - The time when the key frame starts.
	 * @param value - The value the variable should have in the key frame.
	 * @param interpolator - The interpolator to use between the key frame and the next key frame.
	 */
	public void keyFrame(float time, T value, Interpolator<T> interpolator) {

		if (interpolator == null)
			interpolator = LinearInterpolatorFactory.inferLinearInterpolator(value);

		timelineValue.addKeyFrame(time, value, interpolator);

		duration = Math.max(duration, time);

	}

}