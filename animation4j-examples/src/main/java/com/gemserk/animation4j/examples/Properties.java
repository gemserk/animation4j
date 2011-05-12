package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.componentsengine.properties.InterpolatedProperty;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;

public class Properties {
	
	public static <T> Property<T> simple(T value) {
		return new SimpleProperty<T>(value);
	}

	public static <T> Property<T> interpolatedProperty(Transition transition) {
		return new InterpolatedProperty<T>(transition);
	}

}
