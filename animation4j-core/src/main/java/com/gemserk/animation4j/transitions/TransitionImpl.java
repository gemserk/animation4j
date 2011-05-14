package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.time.TimeProvider;

/**
 * Provides default implementation of transition.
 * 
 * @author acoppes
 */
public class TransitionImpl<T> implements Transition<T> {

	private final float speed;

	private final TimeProvider timeProvider;

	private long lastTime;

	private InternalTransition<T> transition;

	private boolean started = false;

	private boolean finished = true;

	public TransitionImpl(InternalTransition<T> transition, float speed, TimeProvider timeProvider) {
		this.speed = speed;
		this.timeProvider = timeProvider;
		this.transition = transition;
	}

	public T get() {
		updateTransition();
		return transition.get();
	}

	public void set(T t) {
		this.set(t, 1000);
	}

	public void set(T t, int time) {
		lastTime = timeProvider.getTime();
		transition.set(t, time);

		started = false;
		finished = false;
	}

	protected void updateTransition() {
		if (finished)
			return;

		long currentTime = timeProvider.getTime();
		long delta = currentTime - lastTime;

		started = true;

		if (delta <= 0)
			return;

		float time = ((float) delta) * speed;
		transition.update((int) (time));
		lastTime = currentTime;

		finished = transition.isFinished();
	}

	@Override
	public boolean isStarted() {
		updateTransition();
		return started;
	}

	@Override
	public boolean isFinished() {
		updateTransition();
		return finished;
	}
}
