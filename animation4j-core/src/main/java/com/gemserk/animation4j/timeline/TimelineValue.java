package com.gemserk.animation4j.timeline;

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

	private T mutableObject;

	private final TimelineValueFloatArray timelineValueFloatArray;

	public void setMutableObject(T mutableObject) {
		this.mutableObject = mutableObject;
	}

	public TimelineValue(T mutableObject, TypeConverter<T> typeConverter) {
		this.mutableObject = mutableObject;
		this.typeConverter = typeConverter;
		this.x = new float[typeConverter.variables()];
		timelineValueFloatArray = new TimelineValueFloatArray(x);
	}

	/**
	 * Adds a new KeyFrame to the TimelineValue.
	 * 
	 * @throws IllegalArgumentException
	 *             if the KeyFrame value is not of the expected size.
	 */
	public void addKeyFrame(KeyFrame keyFrame) {
		timelineValueFloatArray.addKeyFrame(keyFrame);
	}

	/**
	 * Modifies the mutable object by interpolating between corresponding key frames.
	 * 
	 * @param time
	 *            The time to use when calculating the value.
	 */
	public void setTime(float time) {
		timelineValueFloatArray.setTime(time);
		typeConverter.copyToObject(mutableObject, x);
	}

	@Override
	public String toString() {
		return timelineValueFloatArray.toString();
	}

}