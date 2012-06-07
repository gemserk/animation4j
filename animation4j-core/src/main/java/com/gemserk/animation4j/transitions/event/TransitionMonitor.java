package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

@SuppressWarnings("rawtypes")
public class TransitionMonitor {

	private Transition transition;

	private boolean previousStarted = false;
	private boolean previousFinished = false;

	private boolean wasStarted;
	private boolean wasFinished;

	public void monitor(Transition transition) {
		this.transition = transition;
		this.previousStarted = false;
		this.previousFinished = false;
		this.wasFinished = false;
		this.wasStarted = false;
	}

	public boolean wasStarted() {
		return wasStarted;
	}

	public boolean wasFinished() {
		return wasFinished;
	}

	/**
	 * Returns the Transition being monitored.
	 */
	public Transition getTransition() {
		return transition;
	}

	public void update() {
		boolean started = transition.isStarted();
		boolean finished = transition.isFinished();
		
		wasStarted = !previousStarted && started;
		wasFinished = !previousFinished && finished;
		
		previousStarted = started;
		previousFinished = finished;
	}

}