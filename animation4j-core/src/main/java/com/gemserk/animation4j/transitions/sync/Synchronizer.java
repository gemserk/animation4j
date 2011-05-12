package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions.TransitionBuilder;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;
import com.gemserk.animation4j.transitions.event.TransitionMonitorBuilder;

public class Synchronizer {

	private SynchronizedTransitionManager synchronizedTransitionManager = new SynchronizedTransitionManager();

	private TransitionHandlersManager transitionHandlersManager = new TransitionHandlersManager();

	private TransitionMonitorBuilder transitionMonitorBuilder = new TransitionMonitorBuilder();
	
	/**
	 * Performs the synchronization of all the objects with the corresponding transitions registered by calling transition() method.
	 */
	public void synchronize() {
		synchronizedTransitionManager.synchronize();
		transitionHandlersManager.update();
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
	public void transition(Object object, String field, Transition transition) {
		synchronizedTransitionManager.handle(new TransitionReflectionObjectSynchronizer(transition, object, field));
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transition
	 *            The transition to use to modify the object.
	 */
	public void transition(final Object object, final Transition transition) {
		synchronizedTransitionManager.handle(new TransitionDirectObjectSynchronizer(object, transition));
	}

	public void transition(Object object, TransitionBuilder transitionBuilder) {
		transition(object, transitionBuilder.build());
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transition
	 *            The transition to use to modify the object.
	 * @param transitionEventHandler
	 *            The event handler to handle Transition status change events.
	 */
	public void transition(Object object, Transition transition, TransitionEventHandler transitionEventHandler) {
		transition(object, transition);
		transitionHandlersManager.handle(transitionMonitorBuilder.with(transitionEventHandler).monitor(transition).build());
	}

	public void transition(Object object, TransitionBuilder transitionBuilder, TransitionEventHandler transitionEventHandler) {
		transition(object, transitionBuilder.build(), transitionEventHandler);
	}

}