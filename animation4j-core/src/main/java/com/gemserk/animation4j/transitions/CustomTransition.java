package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

public class CustomTransition<T> {

	private final Transition<T> transition;

	private final float speed;

	private final TimeProvider timeProvider;

	private long lastTime;
	
	// make a provider of custom transitions which returns the transition already configured for a time provider.

	public CustomTransition(Transition<T> transition, float speed, TimeProvider timeProvider) {
		this.transition = transition;
		this.speed = speed;
		this.timeProvider = timeProvider;
		lastTime = timeProvider.getTime();
	}

	public CustomTransition(Transition<T> transition, float speed) {
		this(transition, speed, new SystemTimeProvider());
	}

	public T get() {
		long currentTime = timeProvider.getTime();
		float time = ((float) (currentTime - lastTime)) * speed;
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
