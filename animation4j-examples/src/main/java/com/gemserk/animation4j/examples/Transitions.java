package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.transitions.AutoUpdateableTransition;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.UpdateableTransition;

public class Transitions {

	public static <T> Transition<T> updateable(T startValue, Interpolator<T> interpolator) {
		return new UpdateableTransition<T>(startValue, interpolator);
	}

	public static <T> Transition<T> transition(T startValue, Interpolator<T> interpolator, float speed) {
		return new AutoUpdateableTransition<T>(startValue, interpolator, speed);
	}

}