package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * Default implementation of Transition interface which works over a mutable object by using a TypeConverter.
 * 
 * @author acoppes
 * 
 */
public class TransitionImpl<T> implements Transition<T> {

	T mutableObject;
	TypeConverter<T> typeConverter;

	TransitionFloatArrayImpl transition;

	float[] tmp;

	public void setFunctions(InterpolationFunction... functions) {
		transition.setFunctions(functions);
	}

	@Override
	public float getSpeed() {
		return transition.getSpeed();
	}

	public void setSpeed(float speed) {
		transition.setSpeed(speed);
	}

	public TransitionImpl(T mutableObject, TypeConverter<T> typeConverter) {
		this.mutableObject = mutableObject;
		this.typeConverter = typeConverter;
		transition = new TransitionFloatArrayImpl(typeConverter.variables());
		tmp = typeConverter.copyFromObject(mutableObject, tmp);
		transition.start(tmp);
	}

	/**
	 * Sets the object to be modified by the transition.
	 * 
	 * @param object
	 *            The mutable object to be modified.
	 */
	public void setObject(T object) {
		this.mutableObject = object;
	}

	@Override
	public T get() {
		return mutableObject;
	}

	public float[] getValue() {
		return transition.get();
	}

	@Override
	public void start(T t) {
		typeConverter.copyFromObject(t, tmp);
		start(tmp);
	}

	@Override
	public void start(float time, T t) {
		typeConverter.copyFromObject(t, tmp);
		transition.start(time, tmp);
	}

	public void start(float... value) {
		transition.start(value);
		typeConverter.copyToObject(mutableObject, transition.get());
	}

	public void start(float time, float... value) {
		transition.start(time, value);
	}

	@Override
	public boolean isStarted() {
		return transition.isStarted();
	}

	@Override
	public boolean isFinished() {
		return transition.isFinished();
	}

	@Override
	public void update(float delta) {
		if (!isStarted() || isFinished())
			return;
		transition.update(delta);
		typeConverter.copyToObject(mutableObject, transition.get());
	}

	@Override
	public void setStartingValue(T t) {
		transition.setStartingValue(typeConverter.copyFromObject(t, tmp));
	}

	@Override
	public void setStartingValue(float[] value) {
		transition.setStartingValue(value);
	}

	@Override
	public void setEndingValue(T t) {
		transition.setEndingValue(typeConverter.copyFromObject(t, tmp));
	}

	@Override
	public void setEndingValue(float[] value) {
		transition.setEndingValue(value);
	}

}
