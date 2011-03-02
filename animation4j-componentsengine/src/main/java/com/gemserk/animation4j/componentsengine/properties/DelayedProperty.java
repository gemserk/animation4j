package com.gemserk.animation4j.componentsengine.properties;

import com.gemserk.animation4j.componentsengine.TimeProvider;
import com.gemserk.componentsengine.properties.Property;

/**
 * Delays the value set a delay time specified on construction time. Could be an InterpolatedProprety with a custom interpolator too.
 */
public class DelayedProperty<T> implements Property<T> {

	private final int delay;

	private final TimeProvider timeProvider;

	private T delayedValue;

	private long timeWhenSet = 0;

	private T value;

	public DelayedProperty(T value, int delay, TimeProvider timeProvider) {
		this.value = value;
		this.delay = delay;
		this.timeProvider = timeProvider;
	}

	boolean updated = false;

	@Override
	public T get() {
		if (timeProvider.getTime() - timeWhenSet >= delay && !updated) {
			value = delayedValue;
			updated = true;
		}
		return value;
	}

	@Override
	public void set(T value) {
		delayedValue = value;
		timeWhenSet = timeProvider.getTime();
		updated = false;
	}

}