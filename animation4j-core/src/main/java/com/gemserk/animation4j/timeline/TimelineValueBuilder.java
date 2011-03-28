package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;

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

	TimelineValue<T> timelineValue;

	TypeConverter<T> typeConverter;

	public String getName() {
		return name;
	}

	public float getDuration() {
		return duration;
	}

	/**
	 * Builds and returns the being specified time line value.
	 */
	public TimelineValue<T> build() {
		// timelineValue = new TimelineValue<T>(typeConverter);
		timelineValue.setName(name);

		// add keyframes

		return timelineValue;
	}
	
	/**
	 * Defines a new key frame in the time line value.
	 * 
	 * @param time
	 *            The time when the key frame starts (in milliseconds).
	 * @param value
	 *            The value the variable should have in the key frame.
	 */
	public TimelineValueBuilder<T> keyFrame(float time, T value) {
		if (typeConverter == null)
			typeConverter = (TypeConverter) Converters.converter(value.getClass());

		Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables());
		
		if (timelineValue == null)
			timelineValue = new TimelineValue<T>(typeConverter);

		timelineValue.addKeyFrame(time, value, interpolator);
		duration = Math.max(duration, time);
		return this;
	}

}