package com.gemserk.animation4j.timeline;

import java.util.ArrayList;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.timeline.TimelineValue.KeyFrame;

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

	TypeConverter<T> typeConverter;

	ArrayList<KeyFrame> keyFrames = new ArrayList<TimelineValue.KeyFrame>();

	public String getName() {
		return name;
	}

	public float getDuration() {
		return duration;
	}

	public TimelineValueBuilder() {

	}

	public TimelineValueBuilder(TypeConverter<T> typeConverter) {
		this.typeConverter = typeConverter;
	}

	/**
	 * Builds and returns the being specified time line value.
	 */
	public TimelineValue<T> build() {
		TimelineValue<T> timelineValue = new TimelineValue<T>(typeConverter);
		timelineValue.setName(name);
		for (int i = 0; i < keyFrames.size(); i++)
			timelineValue.addKeyFrame(keyFrames.get(i));
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

		// Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables());

		KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null));
		this.keyFrames.add(keyFrame);

		duration = Math.max(duration, time);
		return this;
	}

	/**
	 * Defines a new key frame in the time line value.
	 * 
	 * @param time
	 *            The time when the key frame starts (in milliseconds).
	 * @param value
	 *            The value the variable should have in the key frame.
	 * @param functions
	 *            The interpolation functions to be used when interpolating this keyframe.
	 */
	public TimelineValueBuilder<T> keyFrame(float time, T value, InterpolationFunction... functions) {
		if (typeConverter == null)
			typeConverter = (TypeConverter) Converters.converter(value.getClass());

		// Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables(), functions);

		KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null), functions);
		this.keyFrames.add(keyFrame);

		duration = Math.max(duration, time);

		return this;
	}

}