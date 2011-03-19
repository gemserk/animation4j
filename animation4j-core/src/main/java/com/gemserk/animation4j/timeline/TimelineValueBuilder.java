package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.InterpolatorProvider;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

/**
 * Provides an easy way to create TimelineValues.
 * 
 * @param <T>
 *            The type of the variable of the TimelineValue.
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

	public String getName() {
		return name;
	}

	public float getDuration() {
		return duration;
	}

	private InterpolatorProvider interpolatorProvider = new InterpolatorProvider() {
		{
			register(Float.class, Interpolators.floatInterpolator(InterpolatorFunctionFactory.linear()));
		}
	};

	public void setInterpolatorProvider(InterpolatorProvider interpolatorProvider) {
		this.interpolatorProvider = interpolatorProvider;
	}

	public TimelineValue<T> build() {
		timelineValue.setName(name);
		return timelineValue;
	}

	/**
	 * Defines a new key frame in the timeline value.
	 * 
	 * @param time
	 *            The time when the key frame starts.
	 * @param value
	 *            The value the variable should have in the key frame.
	 * @param interpolator
	 *            The interpolator to use between the key frame and the next key frame.
	 */
	public TimelineValueBuilder<T> keyFrame(float time, T value, Interpolator<T> interpolator) {
		if (interpolator == null)
			throw new RuntimeException("Can't build a key frame with a null interpolator");
		timelineValue.addKeyFrame(time, value, interpolator);
		duration = Math.max(duration, time);
		return this;
	}

	/**
	 * Defines a new key frame in the timeline value, infering an interpolator based on the type of the value.
	 * 
	 * @param time
	 *            The time when the key frame starts.
	 * @param value
	 *            The value the variable should have in the key frame.
	 */
	public TimelineValueBuilder<T> keyFrame(float time, T value) {
		return keyFrame(time, value, interpolatorProvider.inferInterpolator(value));
	}

}