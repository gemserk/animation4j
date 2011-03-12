package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.Interpolator;

/**
 * Implementation of a transition of a float based on <a href="http://www.w3.org/TR/2009/WD-css3-transitions-20090320/">CSS3 transitions spec</a>.
 * @author acoppes
 */
public class FloatTransition {

	private float startValue;

	private float desiredValue;

	private int totalTime;

	private int currentTime;

	private float currentValue;

	private Interpolator<Float> floatInterpolator;

	public FloatTransition(float startValue, Interpolator<Float> floatInterpolator) {
		this.startValue = startValue;
		this.currentValue = startValue;
		this.floatInterpolator = floatInterpolator;
	}

	public float getValue() {
		return currentValue;
	}

	/**
	 * Start an interpolation from a to b in the specified time.
	 * @param desiredValue - The wanted new value.
	 * @param time - The time to set the new value.
	 */
	public void set(float desiredValue, int time) {
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
