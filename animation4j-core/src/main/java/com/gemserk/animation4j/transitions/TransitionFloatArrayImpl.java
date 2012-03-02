package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * Implementation of transition which works over a fixed size float array.
 * 
 * @author acoppes
 * 
 */
public class TransitionFloatArrayImpl implements Transition<float[]> {

	private final TimeTransition timeTransition = new TimeTransition();

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

	public TransitionFloatArrayImpl(int variables) {
		this.a = new float[variables];
		this.b = new float[variables];
		this.x = new float[variables];
	}
	
	public TransitionFloatArrayImpl(float[] value) {
		this.a = new float[value.length];
		this.b = new float[value.length];
		this.x = new float[value.length];
		set(value);
	}

	@Override
	public float[] get() {
		return x;
	}

	public float[] getValue() {
		return x;
	}

	@Override
	public void set(float[] t) {
		System.arraycopy(t, 0, a, 0, Math.min(t.length, a.length));
		System.arraycopy(t, 0, x, 0, Math.min(t.length, x.length));
		finished = true;
	}

	@Override
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

		if (timeTransition.isFinished())
			finished = true;
	}

	@Override
	public void setStart(float[] value) {
		System.arraycopy(value, 0, a, 0, Math.min(value.length, a.length));
	}

	@Override
	public void setEnd(float[] value) {
		System.arraycopy(value, 0, b, 0, Math.min(value.length, b.length));
	}

}
