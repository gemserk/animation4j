package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

/**
 * Provides a modified implementation of UpdateableTransition which calls update(..) each time get() method is called.
 * 
 * @author acoppes
 */
public class AutoUpdateableTransition<T> implements Transition<T> {

	private final float speed;

	private final TimeProvider timeProvider;

	private long lastTime;
	
	private UpdateableTransition<T> transition;

	// make a provider of custom transitions which returns the transition already configured for a time provider?

	// use total time instead of speed? or some easier way to calculate speed. Maybe specifying speed in milliseconds instead of seconds?

	public AutoUpdateableTransition(T startValue, Interpolator<T> interpolator, float speed) {
		this(startValue, interpolator, speed, new SystemTimeProvider());
	}

	public AutoUpdateableTransition(T startValue, T endValue, Interpolator<T> interpolator, float speed) {
		this(startValue, endValue, interpolator, speed, new SystemTimeProvider());
	}

	public AutoUpdateableTransition(T startValue, Interpolator<T> interpolator, float speed, TimeProvider timeProvider) {
		this.speed = speed;
		this.timeProvider = timeProvider;
		this.transition = new UpdateableTransition<T>(startValue, interpolator);
	}

	public AutoUpdateableTransition(T startValue, T endValue, Interpolator<T> interpolator, float speed, TimeProvider timeProvider) {
		this.speed = speed;
		this.timeProvider = timeProvider;
		this.transition = new UpdateableTransition<T>(startValue, interpolator, 1000);
		set(endValue);
	}

	public T get() {
		long currentTime = timeProvider.getTime();
		long delta = currentTime - lastTime;

		if (delta <= 0)
			return transition.get();

		float time = ((float) delta) * speed;
		transition.update((int) (time * 1000f));
		lastTime = currentTime;
		return transition.get();
	}

	public void set(T t) {
		this.set(t, 1000);
	}

	public void set(T t, int time) {
		lastTime = timeProvider.getTime();
		transition.set(t, time);
	}
}
