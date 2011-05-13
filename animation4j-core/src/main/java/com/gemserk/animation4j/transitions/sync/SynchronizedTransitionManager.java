package com.gemserk.animation4j.transitions.sync;

import java.util.ArrayList;

import com.gemserk.animation4j.transitions.Transition;

/**
 * Handles synchronization of a collection of object synchronizers.
 * 
 * @author acoppes
 * 
 */
public class SynchronizedTransitionManager {

	private class ObjectSynchronizerProvider {
		
		TransitionReflectionObjectSynchronizer getReflectionObjectSynchronizer(Object object, String field, Transition transition) {
			return new TransitionReflectionObjectSynchronizer(transition, object, field);
		}

		TransitionDirectObjectSynchronizer getObjectSynchronizer(Object object, Transition transition) {
			return new TransitionDirectObjectSynchronizer(object, transition);
		}
		
	}

	private ArrayList<TransitionObjectSynchronizer> synchronizers;

	private ArrayList<TransitionObjectSynchronizer> removeSynchronizers;
	
	private ObjectSynchronizerProvider objectSynchronizerProvider = new ObjectSynchronizerProvider();

	public SynchronizedTransitionManager() {
		synchronizers = new ArrayList<TransitionObjectSynchronizer>();
		removeSynchronizers = new ArrayList<TransitionObjectSynchronizer>();
	}
	
	public void objectSynchronizer(Object object, Transition transition) {
		handle(objectSynchronizerProvider.getObjectSynchronizer(object, transition));
	}

	public void reflectionObjectSynchronizer(Object object, String field, Transition transition) {
		handle(objectSynchronizerProvider.getReflectionObjectSynchronizer(object, field, transition));
	}

	public void handle(TransitionObjectSynchronizer synchronizer) {
		synchronizers.add(synchronizer);
	}

	public void synchronize() {
		for (int i = 0; i < synchronizers.size(); i++) {
			TransitionObjectSynchronizer synchronizer = synchronizers.get(i);
			synchronizer.synchronize();

			if (synchronizer.isFinished())
				removeSynchronizers.add(synchronizer);
		}
		synchronizers.removeAll(removeSynchronizers);
	}

}