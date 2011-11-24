package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

public class MutableObjectTransition<T> implements Transition<T> {

	private final TimeTransition timeTransition = new TimeTransition();

	T mutableObject;
	TypeConverter<T> typeConverter;
	float[] a, b, x;
	InterpolationFunction[] functions;
	float speed = 1f;

	boolean started;
	boolean finished;

	public void setFunctions(InterpolationFunction... functions) {
		this.functions = functions;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public MutableObjectTransition(T mutableObject, TypeConverter<T> typeConverter) {
		this.mutableObject = mutableObject;
		this.typeConverter = typeConverter;

		this.a = new float[typeConverter.variables()];
		this.b = new float[typeConverter.variables()];
		this.x = new float[typeConverter.variables()];

		typeConverter.copyFromObject(mutableObject, this.x);

		// System.arraycopy(x, 0, a, 0, x.length);
		// System.arraycopy(x, 0, b, 0, x.length);
	}

	@Override
	public T get() {
		return mutableObject;
	}

	public float[] getValue() {
		return x;
	}

	@Override
	public void set(T t) {
		typeConverter.copyFromObject(t, x);
		typeConverter.copyFromObject(t, a);
		typeConverter.copyToObject(mutableObject, x);
		finished = true;
	}

	@Override
	public void set(T t, float time) {
		started = true;
		finished = false;

		System.arraycopy(x, 0, a, 0, Math.min(x.length, a.length));
		typeConverter.copyFromObject(t, b);

		timeTransition.start(time);
	}

	public void set(float[] t) {
		System.arraycopy(t, 0, a, 0, Math.min(t.length, a.length));
		System.arraycopy(t, 0, x, 0, Math.min(t.length, x.length));
		typeConverter.copyToObject(mutableObject, x);
		finished = true;
	}

	public void set(float[] t, float time) {
		started = true;
		finished = false;

		System.arraycopy(x, 0, a, 0, Math.min(x.length, a.length));
		System.arraycopy(t, 0, b, 0, Math.min(t.length, b.length));

		timeTransition.start(time);
	}

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void update(float delta) {
		if (!isStarted() || isFinished())
			return;

		timeTransition.update(delta * speed);
		FloatArrayInterpolator.interpolate(a, b, x, timeTransition.get(), functions);
		typeConverter.copyToObject(mutableObject, x);

		if (timeTransition.isFinished()) {
//			started = false;
			finished = true;
		}
	}

}
