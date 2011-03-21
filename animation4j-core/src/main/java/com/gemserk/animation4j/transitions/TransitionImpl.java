package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;

public class TransitionImpl<T> implements Transition<T> {

	private int totalTime;

	private int currentTime;

	private int defaultTime;

	private Interpolator<float[]> interpolator;

	private float[] startValue;

	private float[] currentValue;

	private float[] desiredValue;

	private T currentObject;

	private final TypeConverter<T> typeConverter;

	public TransitionImpl(T startValue, TypeConverter<T> typeConverter) {
		this(startValue, typeConverter, 0);
	}

	public TransitionImpl(T startValue, TypeConverter<T> typeConverter, int defaultTime) {
		this(startValue, typeConverter, new FloatArrayInterpolator(typeConverter.variables()), defaultTime);
	}

	public TransitionImpl(T startValue, TypeConverter<T> typeConverter, Interpolator<float[]> interpolator) {
		this(startValue, typeConverter, interpolator, 0);
	}

	public TransitionImpl(T startValue, TypeConverter<T> typeConverter, Interpolator<float[]> interpolator, int defaultTime) {
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
		if (currentValue != null) {
			this.startValue = currentValue;
			this.currentValue = null;
		}
		this.currentTime = 0;

		if (time == 0)
			this.currentValue = interpolator.interpolate(startValue, desiredValue, 1f);

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
