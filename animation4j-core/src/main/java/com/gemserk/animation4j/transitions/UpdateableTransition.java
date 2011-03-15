package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.Interpolator;

/**
 * Implementation of a transition of a float based on <a href="http://www.w3.org/TR/2009/WD-css3-transitions-20090320/">CSS3 transitions spec</a>.
 * @author acoppes
 */
public class UpdateableTransition<T> implements Transition<T> {

	private T startValue;

	private T currentValue;

	private T desiredValue;

	private int totalTime;
	
	private int currentTime;

	private Interpolator<T> interpolator;
	
	private int defaultTime;

	/**
	 * @param startValue - The starting value of the transition.
	 * @param interpolator - The interpolator to use when updating the value.
	 */
	public UpdateableTransition(T startValue, Interpolator<T> interpolator) {
		this(startValue, interpolator, 0);
	}

	/**
	 * @param startValue - The starting value of the transition.
	 * @param interpolator - The interpolator to use when updating the value.
	 * @param defaultTime - The default time to use when calling set without specifying the time.
	 */
	public UpdateableTransition(T startValue, Interpolator<T> interpolator, int defaultTime) {
		this.startValue = startValue;
		this.currentValue = startValue;
		this.interpolator = interpolator;
		this.defaultTime = defaultTime;
	}
	
	@Override
	public T get() {
		return currentValue;
	}
	
	@Override
	public void set(T t) {
		this.set(t, defaultTime);
	}

	@Override
	public void set(T t, int time) {
		this.desiredValue = t;
		this.totalTime = time;
		this.startValue = currentValue;
		this.currentTime = 0;
	}

	/**
	 * Updates the current value to the new value.
	 * @param time - The time to update the current value.
	 */
	public void update(int time) {
		
		if (currentTime == totalTime) 
			return;
		
		currentTime += time;
		
		if (currentTime > totalTime) 
			currentTime = totalTime;

		if (desiredValue == null)
			return;

		float alpha = (float) currentTime / (float) totalTime;
		
		currentValue = interpolator.interpolate(startValue, desiredValue, alpha);
		
	}

}
