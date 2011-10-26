package com.gemserk.animation4j.transitions.sync;

import java.util.ArrayList;

import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions.TransitionBuilder;
import com.gemserk.animation4j.transitions.event.TransitionEventHandler;

public class Synchronizer {

	class TransitionUpdater {

		ArrayList<Transition> transitions = new ArrayList<Transition>();
		ArrayList<Transition> toRemoveTransitions = new ArrayList<Transition>();

		public void update(float delta) {
			for (int i = 0; i < transitions.size(); i++) {
				Transition transition = transitions.get(i);
				transition.update(delta);
				if (transition.isFinished())
					toRemoveTransitions.add(transition);
			}

			if (toRemoveTransitions.isEmpty())
				return;

			for (int i = 0; i < toRemoveTransitions.size(); i++)
				transitions.remove(toRemoveTransitions.get(i));

			toRemoveTransitions.clear();
		}

		public void add(Transition transition) {
			transitions.add(transition);
		}

	}

	private SynchronizedTransitionManager synchronizedTransitionManager = new SynchronizedTransitionManager();
	private TransitionHandlersManager transitionHandlersManager = new TransitionHandlersManager();

	/**
	 * Used internally to synchronize correctly when calling synchronize(delta) method, a restriction however is it will only work for all Transitions registered using a TransitionBuilder.
	 */
	private UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
	private SystemTimeProvider systemTimeProvider = new SystemTimeProvider();

	private TransitionUpdater transitionUpdater;

	private float lastTime;

	public Synchronizer() {
		lastTime = systemTimeProvider.getTime();
		transitionUpdater = new TransitionUpdater();
	}

	/**
	 * Performs the synchronization of all the objects with the corresponding registered transitions, it uses a delta based on the last system time.
	 */
	public void synchronize() {
		float currentTime = systemTimeProvider.getTime();
		float delta = currentTime - lastTime;
		synchronize(delta);
		lastTime = currentTime;
	}

	/**
	 * Performs a synchronization of all internal objects with the corresponding registered transitions using the specified delta. It will only work (for now) for those transitions registered using a TransitionBuilder.
	 * 
	 * @param delta
	 *            The delta time in Seconds to use to synchronize.
	 */
	public void synchronize(float delta) {
		timeProvider.update(delta);
		transitionUpdater.update(delta);
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
		transitionUpdater.add(transition);
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
		this.transition(object, field, transitionBuilder.build());
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
	public void transition(Object object, String field, TransitionBuilder transitionBuilder, TransitionEventHandler transitionEventHandler) {
		Transition transition = transitionBuilder.build();
		this.transition(object, field, transition);
		transitionHandlersManager.handle(transition, transitionEventHandler);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object
	 *            The <b>mutable</b> object to be modified in through the transition.
	 * @param transition
	 *            The transition to use to modify the object.
	 */
	public void transition(Object object, Transition transition) {
		transitionUpdater.add(transition);
		synchronizedTransitionManager.objectSynchronizer(object, transition);
	}

	/**
	 * Adds the transition to be updated by the synchronizer.
	 * 
	 * @param transition
	 *            The transition to be registered.
	 */
	public void transition(Transition transition) {
		transitionUpdater.add(transition);
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
		transitionHandlersManager.handle(transition, transitionEventHandler);
	}
	
	public void monitor(Transition transition, TransitionEventHandler transitionEventHandler) {
		transitionHandlersManager.handle(transition, transitionEventHandler);
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
		transition(object, transitionBuilder.build(), transitionEventHandler);
	}

}