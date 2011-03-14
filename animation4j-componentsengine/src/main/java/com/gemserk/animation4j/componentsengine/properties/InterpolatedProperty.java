package com.gemserk.animation4j.componentsengine.properties;

import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;
import com.gemserk.animation4j.transitions.UpdateableTransition;
import com.gemserk.componentsengine.properties.Property;

/**
 * Provides an implementation of Property<T> which interpolates between the current value and the value you set when you call set(...) method.
 * @param <T> - The type of the property value. 
 * @author acoppes
 */
public class InterpolatedProperty<T> implements Property<T> {

	private final UpdateableTransition<T> updateableTransition;

	private final float speed;

	private final TimeProvider timeProvider;
	
	private long lastTime = 0;

	// TODO: Use total time instead of speed? or some easier way to calculate speed. Maybe specifying speed in milliseconds instead of seconds.
	
	/**
	 * @param transition - The transition to be used to set and get values for the property.
	 * @param speed - The speed is specified in seconds and it is used to advance from one value to another value of the interpolated value.
	 * @param timeProvider - The TimeProvider from where to get the time when updated needed for the transition.
	 */
	public InterpolatedProperty(UpdateableTransition<T> transition, float speed, TimeProvider timeProvider) {
		this.timeProvider = timeProvider;
		this.speed = speed;
		this.updateableTransition = transition;
		lastTime = timeProvider.getTime();
	}
	
	/**
	 * @param transition - The transition to be used to set and get values for the property.
	 * @param speed - The speed is specified in seconds and it is used to advance from one value to another value of the interpolated value.
	 */
	public InterpolatedProperty(UpdateableTransition<T> transition, float speed) {
		this(transition, speed, new SystemTimeProvider());
	}

	@Override
	public T get() {
		long currentTime = timeProvider.getTime();
		float time = ((float) (currentTime - lastTime)) * speed;
		updateableTransition.update((int) (time * 1000f));
		lastTime = currentTime;
		return updateableTransition.get();
	}

	@Override
	public void set(T value) {
		lastTime = timeProvider.getTime();
		updateableTransition.set(value, 1000);
	}
}