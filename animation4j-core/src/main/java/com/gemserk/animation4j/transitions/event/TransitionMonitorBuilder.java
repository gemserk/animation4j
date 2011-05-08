package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

@SuppressWarnings("rawtypes")
public class TransitionMonitorBuilder {

	private TransitionMonitor transitionMonitor;
	
	private TransitionEventHandler transitionEventHandler;

	public TransitionMonitorBuilder with(TransitionEventHandler transitionEventHandler) {
		this.transitionEventHandler = transitionEventHandler;
		return this;
	}

	public TransitionMonitorBuilder monitor(Transition transition) {
		transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		return this;
	}
	
	public TransitionMonitorProcessor build() {
		TransitionMonitorProcessor transitionMonitorProcessor = new TransitionMonitorProcessor();
		transitionMonitorProcessor.setTransitionMonitor(transitionMonitor);
		transitionMonitorProcessor.setTransitionEventHandler(transitionEventHandler);
		return transitionMonitorProcessor;
	}

}