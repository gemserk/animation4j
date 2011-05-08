package com.gemserk.animation4j.transitions.sync;

import java.util.ArrayList;

import com.gemserk.animation4j.transitions.event.TransitionMonitorProcessor;

public class TransitionHandlersManager {

	private ArrayList<TransitionMonitorProcessor> transitionMonitorProcessors = new ArrayList<TransitionMonitorProcessor>();
	
	private ArrayList<TransitionMonitorProcessor> removeTransitionMonitorProcessors = new ArrayList<TransitionMonitorProcessor>();

	public void handle(TransitionMonitorProcessor transitionMonitorProcessor) {
		transitionMonitorProcessors.add(transitionMonitorProcessor);
	}

	public void update() {

		for (int i = 0; i < transitionMonitorProcessors.size(); i++) {
			TransitionMonitorProcessor transitionMonitorProcessor = transitionMonitorProcessors.get(i);
			transitionMonitorProcessor.update();
			
			if (transitionMonitorProcessor.isFinished())
				removeTransitionMonitorProcessors.add(transitionMonitorProcessor);
		}
		
		transitionMonitorProcessors.removeAll(removeTransitionMonitorProcessors);

	}

}
