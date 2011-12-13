package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.converters.TypeConverter;

/**
 * Implementation of TimelineValueInterface which internally modifies a mutable object.
 * 
 * @author acoppes
 * 
 * @param <T>
 *            The type of the value.
 */
public class TimelineValue<T> implements TimelineValueInterface {

	private final TypeConverter<T> typeConverter;
	private final float[] x;

	private T mutableObject;

	private final TimelineValueFloatArray timelineValueFloatArray;

	/**
	 * Creates a new TimelineValue which works over the specified mutable object.
	 * 
	 * @param mutableObject
	 *            The object to be modified on setTime(time).
	 * @param typeConverter
	 *            The TypeConverter to be used when modifying the object.
	 */
	public TimelineValue(T mutableObject, TypeConverter<T> typeConverter) {
		this.mutableObject = mutableObject;
		this.typeConverter = typeConverter;
		this.x = new float[typeConverter.variables()];
		timelineValueFloatArray = new TimelineValueFloatArray(x);
	}

	public void addKeyFrame(KeyFrame keyFrame) {
		timelineValueFloatArray.addKeyFrame(keyFrame);
	}

	public void setTime(float time) {
		timelineValueFloatArray.setTime(time);
		typeConverter.copyToObject(mutableObject, x);
	}

	@Override
	public String toString() {
		return timelineValueFloatArray.toString();
	}

}