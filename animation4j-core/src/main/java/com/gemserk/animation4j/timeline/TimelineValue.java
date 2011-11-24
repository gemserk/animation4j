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

	private final TypeConverter<T> typeConverter;

	private final float[] x;

	private final LinkedList<KeyFrame> keyFrames;

	private T mutableObject;
	
	public void setMutableObject(T mutableObject) {
		this.mutableObject = mutableObject;
	}

	public TimelineValue(T mutableObject, TypeConverter<T> typeConverter) {
		this.mutableObject = mutableObject;
		this.typeConverter = typeConverter;
		this.x = new float[typeConverter.variables()];
		keyFrames = new LinkedList<KeyFrame>();
	}

	public void addKeyFrame(KeyFrame keyFrame) {
		keyFrames.add(keyFrame);
	}

	/**
	 * Modifies the mutable object by interpolating between corresponding key frames.
	 * 
	 * @param time
	 *            The time to use when calculating the value.
	 */
	public void setTime(float time) {
		if (keyFrames.isEmpty())
			throw new RuntimeException("cant interpolate without keyframes");
		float[] value = getFloatValue(time);
		typeConverter.copyToObject(mutableObject, value);
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

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append(", keyframes:");
		for (KeyFrame keyFrame : keyFrames)
			stringBuffer.append(keyFrame.toString());
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

}