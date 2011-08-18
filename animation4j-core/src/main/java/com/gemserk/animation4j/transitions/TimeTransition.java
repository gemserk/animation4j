package com.gemserk.animation4j.transitions;

/**
 * Represents a transition over time, similar to a timer.
 * 
 * @author acoppes
 * 
 */
public class TimeTransition {

	private float transitionTime;
	private float currentTime;

	private boolean finished = true;

	public boolean isFinished() {
		return finished;
	}

	/**
	 * Returns the percentage of the current time over the total time.
	 * 
	 * @return
	 */
	public float get() {
		if (transitionTime == 0)
			return 1f;
		return (currentTime / transitionTime);
	}

	public void start(float time) {
		this.transitionTime = time;
		this.currentTime = 0;
		this.finished = false;
	}

	public void update(float time) {
		if (finished)
			return;
		this.currentTime += time;
		if (currentTime >= transitionTime) {
			currentTime = transitionTime;
			finished = true;
		}

	}

}