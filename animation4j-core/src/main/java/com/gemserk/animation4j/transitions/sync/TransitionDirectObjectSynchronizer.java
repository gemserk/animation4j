package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.transitions.Transition;

/**
 * Synchronizes an object value with the transiton value.
 * 
 * @author acoppes
 * 
 */
class TransitionDirectObjectSynchronizer implements TransitionObjectSynchronizer {

	private final Object object;

	private final Transition transition;

	private final TypeConverter typeConverter;

	public TransitionDirectObjectSynchronizer(Object object, Transition transition) {
		this.object = object;
		this.transition = transition;
		typeConverter = Converters.converter(object.getClass());
	}

	@Override
	public void synchronize() {
		// TypeConverter typeConverter = Converters.converter(object.getClass());
		Object currentValue = transition.get();
		float[] x = typeConverter.copyFromObject(currentValue, null);
		typeConverter.copyToObject(object, x);
	}

	@Override
	public boolean isFinished() {
		return !transition.isTransitioning();
	}
}