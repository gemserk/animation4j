package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

/**
 * Calls a TransitionEventHandler whenever a monitored Transition changes its state.
 * 
 * @author acoppes
 */
public class TransitionMonitorProcessor {

	@SuppressWarnings("rawtypes")
	private TransitionEventHandler transitionEventHandler;

	private TransitionMonitor transitionMonitor;
	
	private boolean finished = false;

	@SuppressWarnings("rawtypes")
	public void setTransitionEventHandler(TransitionEventHandler transitionEventHandler) {
		this.transitionEventHandler = transitionEventHandler;
	}

	public void setTransitionMonitor(TransitionMonitor transitionMonitor) {
		this.transitionMonitor = transitionMonitor;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void process(Transition transition, TransitionEventHandler transitionEventHandler) {
		finished = false;
		this.transitionMonitor.monitor(transition);
		this.transitionEventHandler = transitionEventHandler;
	}

	@SuppressWarnings("unchecked")
	public void update() {
		transitionMonitor.update();
		if (transitionMonitor.wasStarted())
			transitionEventHandler.onTransitionStarted(transitionMonitor.getTransition());
		if (transitionMonitor.wasFinished()) {
			transitionEventHandler.onTransitionFinished(transitionMonitor.getTransition());
			finished = true;
		}
			
	}

}