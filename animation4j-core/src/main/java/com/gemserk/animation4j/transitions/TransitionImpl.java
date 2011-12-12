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
		typeConverter.copyFromObject(mutableObject, transition.get());
	}

	@Override
	public T get() {
		return mutableObject;
	}

	public float[] getValue() {
		return transition.get();
	}

	@Override
	public void set(T t) {
		typeConverter.copyFromObject(t, transition.get());
		transition.set(transition.get());
	}

	@Override
	public void set(T t, float time) {
		typeConverter.copyFromObject(t, transition.get());
		transition.set(transition.get(), time);
	}

	public void set(float[] t) {
		transition.set(t);
		typeConverter.copyToObject(mutableObject, transition.get());
	}

	public void set(float[] t, float time) {
		transition.set(t, time);
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

}
