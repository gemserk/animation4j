package com.gemserk.animation4j.timeline;

import java.util.LinkedList;

import com.gemserk.animation4j.converters.TypeConverter;

/**
 * Represents all the progress of a value inside a time line.
 * 
 * @author acoppes
 * 
 * @param <T>
 *            The type of the value.
 */
public class TimelineValue<T> {

	LinkedList<KeyFrame> keyFrames = new LinkedList<KeyFrame>();

	String name;

	private final TypeConverter<T> typeConverter;

	private float[] x;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	TimelineValue(TypeConverter<T> typeConverter) {
		this.typeConverter = typeConverter;
	}

	public void addKeyFrame(KeyFrame keyFrame) {
		keyFrames.add(keyFrame);
		if (x == null)
			x = new float[keyFrame.getValue().length];
	}

	private T currentValue;

	/**
	 * Returns the value for the given time, interpolating between corresponding key frames.
	 * 
	 * @param time
	 *            The time to use when calculating the value.
	 * @return An interpolated value based on corresponding to specified time key frames.
	 */
	public T getValue(float time) {
		if (keyFrames.isEmpty())
			return null;
		float[] value = getFloatValue(time);
		currentValue = typeConverter.copyToObject(currentValue, value);
		return currentValue;
	}

	float[] getFloatValue(float time) {
		KeyFrame keyFrame = getKeyFrame(time);
		KeyFrame previousKeyFrame = getPreviousKeyFrame(keyFrame);

		if (previousKeyFrame == null)
			return keyFrame.getValue();

		float interval = keyFrame.getTime() - previousKeyFrame.getTime();
		float weight = (time - previousKeyFrame.getTime()) / interval;

		return previousKeyFrame.interpolate(keyFrame, x, weight);
	}

	KeyFrame getKeyFrame(float time) {
		if (keyFrames.size() == 1)
			return keyFrames.getFirst();

		KeyFrame firstKeyFrame = keyFrames.getFirst();

		if (time < firstKeyFrame.getTime())
			return firstKeyFrame;

		for (int i = 0; i < keyFrames.size(); i++) {
			KeyFrame currentKeyFrame = keyFrames.get(i);
			if (currentKeyFrame.getTime() > time)
				return currentKeyFrame;
		}

		return keyFrames.getLast();
	}

	KeyFrame getPreviousKeyFrame(KeyFrame keyFrame) {
		int currentKeyFrameIndex = keyFrames.indexOf(keyFrame);
		if (currentKeyFrameIndex - 1 < 0)
			return null;
		return keyFrames.get(currentKeyFrameIndex - 1);
	}

}