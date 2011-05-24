package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.FloatInterpolator;

public class FloatTransition {

	private float current;

	private float start;

	private float end;

	private boolean started;

	private TimeTransition timeTransition = new TimeTransition();

	public float get() {
		return current;
	}

	public void set(float t) {
		set(t, 0);
		this.current = end;
	}

	public void set(float t, int time) {
		this.start = current;
		this.end = t;
		this.started = false;
		timeTransition.start(time);
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isFinished() {
		return timeTransition.isFinished();
	}

	public void update(int delta) {
		this.started = true;
		if (timeTransition.isFinished())
			return;
		timeTransition.update(delta);
		this.current = FloatInterpolator.interpolate(start, end, timeTransition.get());
	}

}
