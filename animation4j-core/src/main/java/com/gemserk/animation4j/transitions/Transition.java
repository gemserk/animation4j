package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.Interpolator;

/**
 * Implementation of a transition of a float based on <a href="http://www.w3.org/TR/2009/WD-css3-transitions-20090320/">CSS3 transitions spec</a>.
 * @author acoppes
 */
public class Transition<T> {

	private T startValue;

	private T desiredValue;

	private int totalTime;

	private int currentTime;

	private T currentValue;

	private Interpolator<T> floatInterpolator;

	public Transition(T startValue, Interpolator<T> floatInterpolator) {
		this.startValue = startValue;
		this.currentValue = startValue;
		this.floatInterpolator = floatInterpolator;
	}

	public T getValue() {
		return currentValue;
	}

	/**
	 * Start an interpolation from a to b in the specified time.
	 * @param desiredValue - The wanted new value.
	 * @param time - The time to set the new value.
	 */
	public void set(T desiredValue, int time) {
		this.desiredValue = desiredValue;
		this.totalTime = time;
		this.startValue = currentValue;
		this.currentTime = 0;
	}

	/**
	 * Updates the current value to the new value.
	 * @param time
	 */
	public void update(int time) {
		
		if (currentTime == totalTime) 
			return;
		
		currentTime += time;
		
		if (currentTime > totalTime) 
			currentTime = totalTime;
		
		float alpha = (float) currentTime / (float) totalTime;
		
		currentValue = floatInterpolator.interpolate(startValue, desiredValue, alpha);
		
	}

}
