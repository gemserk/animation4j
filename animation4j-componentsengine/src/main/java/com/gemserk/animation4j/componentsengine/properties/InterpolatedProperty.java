package com.gemserk.animation4j.componentsengine.properties;

import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.componentsengine.properties.Property;

/**
 * Provides an implementation of Property<T> which interpolates between the current value and the value you set when you call set(...) method.
 * @param <T> - The type of the property value. 
 * @author acoppes
 */
public class InterpolatedProperty<T> implements Property<T> {

	private final Transition<T> transition;

	/**
	 * @param transition - The transition to be used to set and get values for the property. To be used probably with an AutoUpdateableTransition to simplify usage.
	 */
	public InterpolatedProperty(Transition<T> transition) {
		this.transition = transition;
	}
	
	@Override
	public T get() {
		return transition.get();
	}

	@Override
	public void set(T value) {
		transition.set(value);
	}
}