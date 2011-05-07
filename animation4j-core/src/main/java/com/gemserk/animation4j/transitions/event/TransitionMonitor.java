package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

@SuppressWarnings("rawtypes")
public class TransitionMonitor {

	private Transition transition;

	private boolean wasStarted = false;

	private boolean wasFinished = false;

	private boolean wasTransitioning = false;

	public void monitor(Transition transition) {
		this.transition = transition;
		this.wasStarted = false;
		this.wasFinished = false;
		this.wasTransitioning = false;
	}

	public boolean wasStarted() {
		return wasStarted;
	}

	public boolean wasFinished() {
		return wasFinished;
	}

	public void update() {
		boolean transitioning = transition.isTransitioning();

		wasStarted = !wasTransitioning && transitioning;
		wasFinished = wasTransitioning && !transitioning;

		wasTransitioning = transitioning;
	}

}