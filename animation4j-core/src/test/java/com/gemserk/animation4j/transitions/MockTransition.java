package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.transitions.Transition;

public class MockTransition<T> implements Transition<T> {

	boolean transitioning;

	public void setTransitioning(boolean transitioning) {
		this.transitioning = transitioning;
	}

	@Override
	public T get() {
		return null;
	}

	@Override
	public void set(T t) {

	}

	@Override
	public void set(T t, int time) {

	}

	@Override
	public boolean isTransitioning() {
		return transitioning;
	}

}