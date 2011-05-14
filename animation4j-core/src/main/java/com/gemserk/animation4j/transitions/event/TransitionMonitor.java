package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

@SuppressWarnings("rawtypes")
public class TransitionMonitor {

	private Transition transition;

	private boolean wasStarted = false;

	private boolean wasFinished = false;

	private boolean callOnStart;

	private boolean callOnFinish;

	public void monitor(Transition transition) {
		this.transition = transition;
		this.wasStarted = false;
		this.wasFinished = false;
		this.callOnFinish = false;
		this.callOnStart = false;
	}

	public boolean wasStarted() {
		return callOnStart;
	}

	public boolean wasFinished() {
		return callOnFinish;
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
		
		callOnStart = !wasStarted && started;
		callOnFinish = !wasFinished && finished;
		
		wasStarted = started;
		wasFinished = finished;
	}

}