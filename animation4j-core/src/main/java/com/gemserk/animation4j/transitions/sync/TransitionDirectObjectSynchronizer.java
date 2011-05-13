package com.gemserk.animation4j.transitions.sync;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.transitions.Transition;

/**
 * Synchronizes an object value with the transiton value.
 * 
 * @author acoppes
 * 
 */
class TransitionDirectObjectSynchronizer implements TransitionObjectSynchronizer {

	private Object object;

	private Transition transition;

	private TypeConverter typeConverter;
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public void setTransition(Transition transition) {
		this.transition = transition;
	}
	
	public void setTypeConverter(TypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	public TransitionDirectObjectSynchronizer() {

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