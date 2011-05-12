package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.reflection.ReflectionUtils;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;

public class Synchronizers {

	private static Synchronizer synchronizer = new Synchronizer();

	/**
	 * Starts a transition and a synchronizer to modify the specified object's field through the transition.
	 * 
	 * @param object
	 *            The container of the field to be modified.
	 * @param field
	 *            The name of the field which contains the object to be modified.
	 * @param endValue
	 *            The end value of the transition.
	 * @param duration
	 *            The duration of the transition.
	 */
	public static void transition(Object object, String field, Object endValue, int duration) {
		// this method seem strange here, why making all the reflection stuff ?
		Object startValue = ReflectionUtils.getFieldValue(object, field);
		transition(object, field, Transitions.transitionBuilder(startValue).end(endValue).time(duration).build());
	}

	/**
	 * Starts a transition and a synchronizer to modify the specified object's field through the transition.
	 * 
	 * @param object
	 *            The container of the field to be modified.
	 * @param field
	 *            The name of the field which contains the object to be modified.
	 * @param transition
	 *            The transition to synchronize with the object field.
	 */
	public static void transition(Object object, String field, Transition transition) {
		synchronizer.transition(object, field, transition);
	}

	/**
	 * Performs the synchronization of all the objects with the corresponding transitions registered by calling transition() method.
	 */
	public static void synchronize() {
		synchronizer.synchronize();
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param endValue
	 *            The end value of the transition.
	 * @param duration
	 *            The duration of the transition.
	 */
	public static void transition(final Object object, Object endValue, int duration) {
		synchronizer.transition(object, Transitions.transitionBuilder(object).end(endValue).time(duration));
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transition
	 *            The transition to use to modify the object.
	 */
	public static void transition(final Object object, final Transition transition) {
		synchronizer.transition(object, transition);
	}

	public static void transition(Object object, Transition transition, TransitionEventHandler transitionEventHandler) {
		synchronizer.transition(object, transition, transitionEventHandler);
	}

}