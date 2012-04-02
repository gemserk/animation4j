package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.converters.TypeConverter;

class InternalTimelineValue<T>  {

	private float[] x;
	private TimelineValueFloatArrayImpl timelineValueFloatArrayImpl;

	private TypeConverter<T> typeConverter;

	public InternalTimelineValue(TypeConverter<T> typeConverter) {
		this.typeConverter = typeConverter;
		this.x = new float[typeConverter.variables()];
		timelineValueFloatArrayImpl = new TimelineValueFloatArrayImpl(x);
	}

	public void addKeyFrame(KeyFrame keyFrame) {
		timelineValueFloatArrayImpl.addKeyFrame(keyFrame);
	}

	public void setTime(T object, float time) {
		timelineValueFloatArrayImpl.setTime(time);
		typeConverter.copyToObject(object, x);
	}
	
	@Override
	public String toString() {
		return timelineValueFloatArrayImpl.toString();
	}

	public int getKeyFramesCount() {
		return timelineValueFloatArrayImpl.getKeyFramesCount();
	}

	public KeyFrame getKeyFrame(int i) {
		return timelineValueFloatArrayImpl.getKeyFrameObject(i);
	}

}