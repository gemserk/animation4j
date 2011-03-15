package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

public class AutoUpdateableTransition<T> extends UpdateableTransition<T> {

	private final float speed;

	private final TimeProvider timeProvider;

	private long lastTime;

	// make a provider of custom transitions which returns the transition already configured for a time provider?
	
	// use total time instead of speed? or some easier way to calculate speed. Maybe specifying speed in milliseconds instead of seconds?
	
	public AutoUpdateableTransition(T startValue, Interpolator<T> interpolator, float speed) {
		this(startValue, interpolator, speed, new SystemTimeProvider());
	}

	public AutoUpdateableTransition(T startValue, T endValue, Interpolator<T> interpolator, float speed) {
		this(startValue, endValue, interpolator, speed, new SystemTimeProvider());
	}
	
	public AutoUpdateableTransition(T startValue, Interpolator<T> interpolator, float speed, TimeProvider timeProvider) {
		super(startValue, interpolator);
		this.speed = speed;
		this.timeProvider = timeProvider;
	}
	
	public AutoUpdateableTransition(T startValue, T endValue, Interpolator<T> interpolator, float speed, TimeProvider timeProvider) {
		super(startValue, interpolator, 1000);
		this.speed = speed;
		this.timeProvider = timeProvider;
		set(endValue);
	}

	public T get() {
		long currentTime = timeProvider.getTime();
		long delta = currentTime - lastTime;
		
		if (delta <= 0)
			return super.get();
		
		float time = ((float) delta) * speed;
		super.update((int) (time * 1000f));
		lastTime = currentTime;
		return super.get();
	}

	public void set(T t) {
		this.set(t, 1000);
	}

	public void set(T t, int time) {
		lastTime = timeProvider.getTime();
		super.set(t, time);
	}
}
