package com.gemserk.animation4j.transitions;


/**
 * Provides default implementation of transition.
 * 
 * @author acoppes
 */
public class TransitionImpl<T> implements Transition<T> {

	/**
	 * Used internally to multiply delta when updating the transition, 2f implies 2x the interpolation speed.
	 */
	private float speed;

	private InternalTransition<T> transition;

	private boolean started = false;
	private boolean finished = true;

	public TransitionImpl(InternalTransition<T> transition) {
		this(transition, 1f);
	}

	public TransitionImpl(InternalTransition<T> transition, float speed) {
		this.speed = speed;
		this.transition = transition;
	}

	public T get() {
		return transition.get();
	}

	public void set(T t) {
		this.set(t, 1f);
	}

	public void set(T t, float time) {
		transition.set(t, time);

		started = true;
		finished = false;
	}

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void update(float delta) {
		if (finished)
			return;

		started = true;

		if (delta <= 0)
			return;

		float time = delta * speed;
		transition.update(time);

		finished = transition.isFinished();
	}
}
