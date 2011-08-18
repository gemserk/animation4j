package com.gemserk.animation4j.timeline;

import java.util.ArrayList;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * Provides an easy way to create TimelineValues.
 * 
 * @author acoppes
 */
public class TimelineValueBuilder {

	String name;

	float duration = 0f;

	public void setName(String name) {
		this.name = name;
	}

	TypeConverter typeConverter;

	ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

	public String getName() {
		return name;
	}

	public float getDuration() {
		return duration;
	}

	public TimelineValueBuilder() {

	}

	public TimelineValueBuilder(TypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	/**
	 * Builds and returns the being specified time line value.
	 */
	public TimelineValue build() {
		TimelineValue timelineValue = new TimelineValue(name, typeConverter);
		for (int i = 0; i < keyFrames.size(); i++)
			timelineValue.addKeyFrame(keyFrames.get(i));
		return timelineValue;
	}

	// /**
	// * Defines a new key frame in the time line value.
	// *
	// * @param time
	// * The time when the key frame starts (in milliseconds).
	// * @param value
	// * The value the variable should have in the key frame.
	// */
	// public <T> TimelineValueBuilder keyFrame(float time, T value) {
	// if (typeConverter == null)
	// typeConverter = (TypeConverter) Converters.converter(value.getClass());
	//
	// float timeInSeconds = time * 0.001f;
	//
	// // TODO: do not allow time less than last time to avoid defining key frames between other key frames.
	//
	// KeyFrame keyFrame = new KeyFrame(timeInSeconds, typeConverter.copyFromObject(value, null));
	// this.keyFrames.add(keyFrame);
	//
	// duration = Math.max(duration, timeInSeconds);
	// return this;
	// }

	/**
	 * Defines a new key frame in the time line value.
	 * 
	 * @param time
	 *            The time when the key frame starts in seconds.
	 * @param value
	 *            The value the variable should have in the key frame.
	 * @param functions
	 *            The interpolation functions to be used when interpolating this keyframe.
	 */
	public <T> TimelineValueBuilder keyFrame(float time, T value, InterpolationFunction... functions) {
		if (typeConverter == null)
			typeConverter = (TypeConverter) Converters.converter(value.getClass());

		KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null), functions);
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
	public <T> TimelineValueBuilder keyFrame(int time, T value, InterpolationFunction... functions) {
		return keyFrame((float) time * 0.001f, value, functions);
	}

}