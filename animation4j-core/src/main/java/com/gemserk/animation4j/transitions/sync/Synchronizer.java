package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions.TransitionBuilder;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;
import com.gemserk.animation4j.transitions.event.TransitionMonitorBuilder;

public class Synchronizer {

	private SynchronizedTransitionManager synchronizedTransitionManager = new SynchronizedTransitionManager();

	private TransitionHandlersManager transitionHandlersManager = new TransitionHandlersManager();

	private TransitionMonitorBuilder transitionMonitorBuilder = new TransitionMonitorBuilder();
	
	/**
	 * Used internally to synchronize correctly when calling synchronize(delta) method, a restriction however is it will only work for all Transitions registered using a TransitionBuilder.
	 */
	private UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();

	private long lastTime;

	public Synchronizer() {
		lastTime = System.currentTimeMillis();
	}

	/**
	 * Performs the synchronization of all the objects with the corresponding registered transitions, it uses a delta based on the last system time.
	 */
	public void synchronize() {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - lastTime;
		synchronize(delta);
		lastTime = currentTime;
	}

	/**
	 * Performs a synchronization of all internal objects with the corresponding registered transitions using the specified delta. 
	 * It will only work (for now) for those transitions registered using a TransitionBuilder.
	 * 
	 * @param delta
	 *            The delta time to use to synchronize.
	 */
	public void synchronize(long delta) {
		timeProvider.update(delta);
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
	private void transition(Object object, String field, Transition transition) {
		synchronizedTransitionManager.reflectionObjectSynchronizer(object, field, transition);
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
	public void transition(Object object, String field, TransitionBuilder transitionBuilder) {
		this.transition(object, field, transitionBuilder.timeProvider(timeProvider).build());
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transition
	 *            The transition to use to modify the object.
	 */
	private void transition(Object object, Transition transition) {
		synchronizedTransitionManager.objectSynchronizer(object, transition);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transitionBuilder
	 *            the TransitionBuilder used to build internally the transition.
	 */
	public void transition(Object object, TransitionBuilder transitionBuilder) {
		if (!transitionBuilder.isStartValueSet())
			transitionBuilder.start(object);
		transition(object, transitionBuilder.timeProvider(timeProvider).build());
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
	private void transition(Object object, Transition transition, TransitionEventHandler transitionEventHandler) {
		transition(object, transition);
		transitionHandlersManager.handle(transitionMonitorBuilder.with(transitionEventHandler).monitor(transition).build());
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
	public void transition(Object object, TransitionBuilder transitionBuilder, TransitionEventHandler transitionEventHandler) {
		if (!transitionBuilder.isStartValueSet())
			transitionBuilder.start(object);
		transition(object, transitionBuilder.timeProvider(timeProvider).build(), transitionEventHandler);
	}

}