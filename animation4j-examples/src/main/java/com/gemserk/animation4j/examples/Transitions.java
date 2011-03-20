package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;
import com.gemserk.animation4j.transitions.AutoUpdateableTransition;
import com.gemserk.animation4j.transitions.Transition;

public class Transitions {

	private static float ms = 1f / 1000f;
	
	public static TimeProvider timeProvider = new SystemTimeProvider();

	public static <T> Transition<T> transition(T startValue, Interpolator<T> interpolator) {
		return transition(startValue, interpolator, ms);
	}

	public static <T> Transition<T> transition(T startValue, T endValue, Interpolator<T> interpolator) {
		return transition(startValue, endValue, interpolator, ms);
	}

	public static <T> Transition<T> transition(T startValue, Interpolator<T> interpolator, float speed) {
		return new AutoUpdateableTransition<T>(startValue, interpolator, speed, timeProvider);
	}

	public static <T> Transition<T> transition(T startValue, T endValue, Interpolator<T> interpolator, float speed) {
		return new AutoUpdateableTransition<T>(startValue, endValue, interpolator, speed, timeProvider);
	}

}