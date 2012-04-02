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
public class TimelineValueImpl<T> implements TimelineValue<T> {

	/**
	 * The object to be modified with the type converter when processing the timeline value.
	 */
	private T object;
	private InternalTimelineValue<T> internalTimelineValue;

	public void setObject(T object) {
		this.object = object;
	}

	public T getObject() {
		return object;
	}
	
	public TimelineValueImpl(TypeConverter<T> typeConverter) {
		internalTimelineValue = new InternalTimelineValue<T>(typeConverter);
	}

	/**
	 * Creates a new instance which works over the specified mutable object.
	 * 
	 * @param object
	 *            The mutable object to be modified on setTime(time).
	 * @param typeConverter
	 *            The TypeConverter to be used when modifying the object.
	 */
	public TimelineValueImpl(T object, TypeConverter<T> typeConverter) {
		this.object = object;
		internalTimelineValue = new InternalTimelineValue<T>(typeConverter);
	}

	public void addKeyFrame(KeyFrame keyFrame) {
		internalTimelineValue.addKeyFrame(keyFrame);
	}

	public void setTime(float time) {
		internalTimelineValue.setTime(object, time);
	}

	public void setTime(T object, float time) {
		internalTimelineValue.setTime(object, time);
	}
	
	@Override
	public String toString() {
		return internalTimelineValue.toString();
	}

	@Override
	public int getKeyFramesCount() {
		return internalTimelineValue.getKeyFramesCount();
	}

	@Override
	public KeyFrame getKeyFrame(int i) {
		return internalTimelineValue.getKeyFrame(i);
	}

}