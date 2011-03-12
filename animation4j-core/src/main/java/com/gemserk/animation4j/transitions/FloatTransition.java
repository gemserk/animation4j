package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

/**
 * Implementation of a transition of a float based on <a href="http://www.w3.org/TR/2009/WD-css3-transitions-20090320/">CSS3 transitions spec</a>.
 * @author acoppes
 */
public class FloatTransition {

	private final InterpolatorFunction interpolatorFunction;
	
	private float startValue;

	private float desiredValue;

	private int totalTime;

	private int currentTime;

	private float currentValue;

	public FloatTransition(float startValue, InterpolatorFunction interpolatorFunction) {
		this.startValue = startValue;
		this.interpolatorFunction = interpolatorFunction;
		this.currentValue = startValue;
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
		
		currentValue = startValue + (desiredValue - startValue) * interpolatorFunction.interpolate(alpha);
		
	}

}
