package com.gemserk.animation4j.interpolators;

public class FloatStateInterpolator implements StateInterpolator<Float> {

	float from;

	float to;

	int time;

	float value;

	float speed;
	
	public FloatStateInterpolator(int time, Float from, Float to) {
		this.from = from;
		this.to = to;
		this.time = time;
		reset();
	}

	public void update(int delta) {

		float currentSpeed = speed * delta;

		value += currentSpeed;

		if (from > to) {
			if (value < to)
				value = to;
		} else {
			if (value > to)
				value = to;
		}

	}

	public Float getInterpolatedValue() {
		return value;
	}

	public void reset() {
		speed = ((to - from) / time);
		value = from;
	}

	@Override
	public boolean isFinished() {
		return value == to;
	}

}