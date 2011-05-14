package com.gemserk.animation4j.transitions.sync;

import java.util.ArrayList;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;
import com.gemserk.animation4j.transitions.event.TransitionMonitor;
import com.gemserk.animation4j.transitions.event.TransitionMonitorProcessor;
import com.gemserk.animation4j.utils.Pool;
import com.gemserk.animation4j.utils.Pool.PoolObjectFactory;

public class TransitionHandlersManager {

	private static final PoolObjectFactory<TransitionMonitorProcessor> monitorProcessorFactory = new PoolObjectFactory<TransitionMonitorProcessor>() {
		@Override
		public TransitionMonitorProcessor createObject() {
			TransitionMonitorProcessor transitionMonitorProcessor = new TransitionMonitorProcessor();
			transitionMonitorProcessor.setTransitionMonitor(new TransitionMonitor());
			return transitionMonitorProcessor;
		}
	};

	private ArrayList<TransitionMonitorProcessor> transitionMonitorProcessors = new ArrayList<TransitionMonitorProcessor>();

	private ArrayList<TransitionMonitorProcessor> removeTransitionMonitorProcessors = new ArrayList<TransitionMonitorProcessor>();

	private Pool<TransitionMonitorProcessor> monitorProcessorPool = new Pool<TransitionMonitorProcessor>(monitorProcessorFactory, 100);

	/**
	 * Monitors the Transition and calls the TransitionEventHandler whenever the Transition changes its states.
	 * 
	 * @param transition
	 *            The Transition to be monitored.
	 * @param transitionEventHandler
	 *            The TransitionEventHandler to be called when changes were detected.
	 */
	public void handle(Transition transition, TransitionEventHandler transitionEventHandler) {
		TransitionMonitorProcessor transitionMonitorProcessor = monitorProcessorPool.newObject();
		transitionMonitorProcessor.process(transition, transitionEventHandler);
		transitionMonitorProcessors.add(transitionMonitorProcessor);
	}

	/**
	 * Updates all internal monitors to detect changes in transitions and call all registered event handlers.
	 */
	public void update() {
		removeTransitionMonitorProcessors.clear();
		for (int i = 0; i < transitionMonitorProcessors.size(); i++) {
			TransitionMonitorProcessor transitionMonitorProcessor = transitionMonitorProcessors.get(i);
			transitionMonitorProcessor.update();

			if (transitionMonitorProcessor.isFinished()) {
				monitorProcessorPool.free(transitionMonitorProcessor);
				removeTransitionMonitorProcessors.add(transitionMonitorProcessor);
			}
		}
		transitionMonitorProcessors.removeAll(removeTransitionMonitorProcessors);
	}

}
