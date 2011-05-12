package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.timeline.sync.ReflectionObjectSynchronizer;
import com.gemserk.animation4j.transitions.Transition;

/**
 * Provides an implementation of TransitionObjectSynchronizer which synchronizes the transition current value with the object internal field value.
 * 
 * @author acoppes
 */
public class TransitionReflectionObjectSynchronizer implements TransitionObjectSynchronizer {

	private final Transition<?> transition;

	private ReflectionObjectSynchronizer reflectionObjectSynchronizer;

	private final String fieldName;

	private final Object object;

	/**
	 * @param transition
	 *            The transition to be synchronized with the object field.
	 * @param object
	 *            The object owner of the field.
	 * @param fieldName
	 *            The field name of the field to be synchronized with the transition value.
	 */
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