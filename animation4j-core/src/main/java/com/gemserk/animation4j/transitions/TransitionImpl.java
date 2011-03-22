package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;

class TransitionImpl<T> implements Transition<T> {

	private int totalTime;

	private int currentTime;

	private int defaultTime;

	private Interpolator<float[]> interpolator;

	private float[] startValue;

	private float[] currentValue;

	private float[] desiredValue;

	private T currentObject;

	private final TypeConverter<T> typeConverter;

	TransitionImpl(T startValue, TypeConverter<T> typeConverter, Interpolator<float[]> interpolator) {
		this(startValue, typeConverter, interpolator, 0);
	}

	TransitionImpl(T startValue, TypeConverter<T> typeConverter, Interpolator<float[]> interpolator, int defaultTime) {
		this.typeConverter = typeConverter;
		this.startValue = typeConverter.copyFromObject(startValue, null);
		this.currentValue = null;
		this.interpolator = interpolator;
		this.defaultTime = defaultTime;
	}

	@Override
	public T get() {
		if (currentValue == null)
			currentObject = typeConverter.copyToObject(currentObject, startValue);
		else
			currentObject = typeConverter.copyToObject(currentObject, currentValue);
		return currentObject;
	}

	@Override
	public void set(T t) {
		this.set(t, defaultTime);
	}

	@Override
	public void set(T t, int time) {
		this.desiredValue = typeConverter.copyFromObject(t, desiredValue);
		this.totalTime = time;
		
		if (currentValue != null)
			copyArray(startValue, currentValue);
		
		this.currentTime = 0;
		
		if (time == 0)
			this.currentValue = interpolator.interpolate(startValue, desiredValue, 1f);
	}

	protected void copyArray(float[] a, float[] b) {
		for (int i = 0; i < a.length; i++) 
			a[i] = b[i];
	}

	public void update(int time) {
		if (currentTime == totalTime)
			return;

		currentTime += time;

		if (currentTime > totalTime)
			currentTime = totalTime;

		if (desiredValue == null)
			return;

		float alpha = (float) currentTime / (float) totalTime;

		currentValue = interpolator.interpolate(startValue, desiredValue, alpha);
	}

}
