package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

public class AutoUpdateableTransition<T> implements Transition<T> {

	private final UpdateableTransition<T> updateableTransition;

	private final float speed;

	private final TimeProvider timeProvider;

	private long lastTime;

	// make a provider of custom transitions which returns the transition already configured for a time provider?
	
	// use total time instead of speed? or some easier way to calculate speed. Maybe specifying speed in milliseconds instead of seconds?

	public AutoUpdateableTransition(UpdateableTransition<T> transition, float speed, TimeProvider timeProvider) {
		this.updateableTransition = transition;
		this.speed = speed;
		this.timeProvider = timeProvider;
		lastTime = timeProvider.getTime();
	}

	public AutoUpdateableTransition(UpdateableTransition<T> transition, float speed) {
		this(transition, speed, new SystemTimeProvider());
	}

	public T get() {
		long currentTime = timeProvider.getTime();
		long delta = currentTime - lastTime;
		
		if (delta <= 0)
			return updateableTransition.get();
		
		float time = ((float) delta) * speed;
		updateableTransition.update((int) (time * 1000f));
		lastTime = currentTime;
		return updateableTransition.get();
	}

	public void set(T t) {
		this.set(t, 1000);
	}

	public void set(T t, int time) {
		lastTime = timeProvider.getTime();
		updateableTransition.set(t, time);
	}
}
