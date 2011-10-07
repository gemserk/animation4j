package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

public class MutableObjectTransition implements Transition<float[]> {

	private final TimeTransition timeTransition = new TimeTransition();

	Object mutableObject;
	TypeConverter typeConverter;
	float[] a, b, x;
	InterpolationFunction[] functions;

	boolean started;
	boolean finished;
	
	public void setFunctions(InterpolationFunction... functions) {
		this.functions = functions;
	}

	@SuppressWarnings("rawtypes")
	public MutableObjectTransition(Object mutableObject, TypeConverter typeConverter) {
		this.mutableObject = mutableObject;
		this.typeConverter = typeConverter;

		this.a = new float[typeConverter.variables()];
		this.b = new float[typeConverter.variables()];
		this.x = new float[typeConverter.variables()];

		typeConverter.copyFromObject(mutableObject, this.x);
	}

	@Override
	public float[] get() {
		return null;
	}

	@Override
	public void set(float[] t) {
		System.arraycopy(t, 0, a, 0, a.length);
		System.arraycopy(t, 0, x, 0, x.length);
		typeConverter.copyToObject(mutableObject, x);
		finished = true;
	}

	@Override
	public void set(float[] t, float time) {
		started = true;
		finished = false;

		System.arraycopy(t, 0, b, 0, b.length);

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
		timeTransition.update(delta);
		FloatArrayInterpolator.interpolate(a, b, x, timeTransition.get(), functions);
		typeConverter.copyToObject(mutableObject, x);

		if (timeTransition.isFinished()) {
			started = false;
			finished = true;
		}
	}

}
