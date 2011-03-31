package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.timeline.sync.ReflectionObjectSynchronizer;
import com.gemserk.animation4j.transitions.Transition;

public class TransitionReflectionObjectSynchronizer implements TransitionObjectSynchronizer {

	private final Transition<?> transition;

	private final Object object;

	private ReflectionObjectSynchronizer reflectionObjectSynchronizer;

	private final String fieldName;
	
	public Transition<?> getTransition() {
		return transition;
	}

	public TransitionReflectionObjectSynchronizer(Transition<?> transition, Object object, String fieldName) {
		this.transition = transition;
		this.object = object;
		this.fieldName = fieldName;
		reflectionObjectSynchronizer = new ReflectionObjectSynchronizer(object);
	}

	@Override
	public void synchronize() {
		reflectionObjectSynchronizer.setValue(fieldName, transition.get());
	}

}