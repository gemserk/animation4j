package com.gemserk.animation4j.transitions.sync;

import java.lang.reflect.Method;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.reflection.ReflectionUtils;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

public class Synchronizers {

	static SynchronizedTransitionManager synchronizedTransitionManager = new SynchronizedTransitionManager();

	public static void transition(Object object, String field, Object startValue, Object endValue, int time) {

		Transition<Object> transition = Transitions.transition(startValue);
		transition.set(endValue, time);

		synchronizedTransitionManager.handle(new TransitionReflectionObjectSynchronizer(transition, object, field));

	}

	/**
	 * Starts a transition and a synchronizer to modify the specified object's field through the transition.
	 * @param object The container of the field to be modified.
	 * @param field The name of the field which contains the object to be modified.
	 * @param endValue The end value of the transition.
	 * @param duration The duration of the transition.
	 */
	public static void transition(Object object, String field, Object endValue, int duration) {

		try {
			String getterName = ReflectionUtils.getGetterName(field);
			Method getterMethod = ReflectionUtils.findMethod(object.getClass(), getterName);

			if (getterMethod == null)
				throw new RuntimeException();

			Object startValue = getterMethod.invoke(object, (Object[]) null);
			transition(object, field, startValue, endValue, duration);
		} catch (Exception e) {
			throw new RuntimeException("get" + ReflectionUtils.capitalize(field) + "() method not found in " + object.getClass(), e);
		}

	}

	public static void synchronize() {
		synchronizedTransitionManager.synchronize();
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object The <b>mutable</b> object to be modified in through the transition. 
	 * @param endValue The end value of the transition.
	 * @param duration The duration of the transition.
	 */
	public static void transition(final Object object, Object endValue, int duration) {
		final Transition<Object> transition = Transitions.transition(object);
		transition.set(endValue, duration);
		transition(object, transition);
	}

	/**
	 * Starts a transition and a synchronizer of the transition current value with the specified object. The object must be <b>mutable</b> in order to be modified.
	 * 
	 * @param object The <b>mutable</b> object to be modified in through the transition. 
	 * @param transition The transition to use to modify the object.
	 */
	public static void transition(final Object object, final Transition transition) {

		synchronizedTransitionManager.handle(new TransitionObjectSynchronizer() {

			@Override
			public void synchronize() {
				TypeConverter typeConverter = Converters.converter(object.getClass());
				Object currentValue = transition.get();
				float[] x = typeConverter.copyFromObject(currentValue, null);
				typeConverter.copyToObject(object, x);
			}

			@Override
			public boolean isFinished() {
				return !transition.isTransitioning();
			}

		});

	}

}