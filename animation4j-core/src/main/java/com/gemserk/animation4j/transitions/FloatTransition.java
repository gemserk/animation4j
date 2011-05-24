package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.FloatInterpolator;


public class FloatTransition {

	private float current;
	
	private float start;

	private float end;
	
	private int transitionTime;
	
	private int currentTime;

	private boolean started;
	
	private boolean finished;
	
	public float get() {
		return current;
	}

	public void set(float t) {
		this.current = t;
	}

	public void set(float t, int time) {
		this.start = current;
		this.end = t;
		this.transitionTime = time;
		this.currentTime = 0;
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isFinished() {
		return finished;
	}

	public void update(int delta) {
		this.started = true;
		
		currentTime += delta;
		
		if (currentTime >= transitionTime) { 
			this.current = end;
			this.finished = true;
			return;
		}

		float t = (float) currentTime / (float) transitionTime;
		this.current = FloatInterpolator.interpolate(start, end, t);
	}
	
}
