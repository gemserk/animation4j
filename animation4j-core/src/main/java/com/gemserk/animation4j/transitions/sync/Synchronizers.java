package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions.TransitionBuilder;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;

public class Synchronizers {

	private static final Synchronizer synchronizer = new Synchronizer();

	public static Synchronizer getInstance() {
		return synchronizer;
	}

	/**
	 * Performs the synchronization of all the objects with the corresponding transitions registered by calling transition() method.
	 */
	public static void synchronize() {
		getInstance().synchronize();
	}

	/**
	 * Performs a synchronization of all internal objects with the corresponding registered transitions using the specified delta. It will only work (for now) for those transitions registered using a TransitionBuilder.
	 * 
	 * @param delta
	 *            The delta time in Seconds to use to synchronize.
	 */
	public static void synchronize(float delta) {
		getInstance().synchronize(delta);
	}

	/**
	 * Starts a transition and a synchronizer to modify the specified object's field through the transition.
	 * 
	 * @param object
	 *            The container of the field to be modified.
	 * @param field
	 *            The name of the field which contains the object to be modified.
	 * @param transitionBuilder
	 *            the TransitionBuilder used to build internally the transition.
	 */
	public static void transition(Object object, String field, TransitionBuilder transitionBuilder) {
		getInstance().transition(object, field, transitionBuilder);
	}

	/**
	 * Starts a transition and a synchronizer to modify the specified object's field through the transition.
	 * 
	 * @param object
	 *            The container of the field to be modified.
	 * @param field
	 *            The name of the field which contains the object to be modified.
	 * @param transition
	 *            The transition to be synchronized with the object's field.
	 */
	public static void transition(Object object, String field, Transition transition) {
		getInstance().transition(object, field, transition);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transitionBuilder
	 *            the TransitionBuilder used to build internally the transition.
	 */
	public static void transition(Object object, TransitionBuilder transitionBuilder) {
		getInstance().transition(object, transitionBuilder);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transition
	 *            The transition to be synchronized with the specified object.
	 */
	public static void transition(Object object, Transition transition) {
		getInstance().transition(object, transition);
	}
	
	/**
	 * Adds the transition to be updated by the synchronizer.
	 * 
	 * @param transition
	 *            The transition to be registered.
	 */
	public static void transition(Transition transition) {
		getInstance().transition(transition);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transitionBuilder
	 *            The transition builder to create the transition.
	 * @param transitionEventHandler
	 *            The event handler to handle Transition status change events.
	 */
	public static void transition(Object object, TransitionBuilder transitionBuilder, TransitionEventHandler transitionEventHandler) {
		getInstance().transition(object, transitionBuilder, transitionEventHandler);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transitionBuilder
	 *            The transition builder to create the transition.
	 * @param transition
	 *            The transition to be synchronized with the specified object.
	 */
	public static void transition(Object object, Transition transition, TransitionEventHandler transitionEventHandler) {
		getInstance().transition(object, transition, transitionEventHandler);
	}
}