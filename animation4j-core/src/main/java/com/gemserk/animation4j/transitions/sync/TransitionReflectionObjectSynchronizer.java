package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.timeline.sync.ReflectionObjectSynchronizer;
import com.gemserk.animation4j.transitions.Transition;

public class TransitionReflectionObjectSynchronizer implements TransitionObjectSynchronizer {

	private final Transition<?> transition;

	private ReflectionObjectSynchronizer reflectionObjectSynchronizer;

	private final String fieldName;

	private final Object object;
	
	public TransitionReflectionObjectSynchronizer(Transition<?> transition, Object object, String fieldName) {
		this.transition = transition;
		this.object = object;
		this.fieldName = fieldName;
		reflectionObjectSynchronizer = new ReflectionObjectSynchronizer();
	}

	@Override
	public void synchronize() {
		reflectionObjectSynchronizer.setValue(object, fieldName, transition.get());
	}

	@Override
	public boolean isFinished() {
		return !transition.isTransitioning();
	}

}