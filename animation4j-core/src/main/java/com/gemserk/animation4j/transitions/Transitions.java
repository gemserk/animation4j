package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

public class Transitions {

	private static float ms = 1f / 1000f;

	public static TimeProvider timeProvider = new SystemTimeProvider();

	public static <T> Transition<T> transition(T startValue, Interpolator<T> interpolator, TypeConverter<T> typeConverter) {
		return transition(startValue, interpolator, ms, typeConverter);
	}

	public static <T> Transition<T> transition(T startValue, T endValue, Interpolator<T> interpolator, TypeConverter<T> typeConverter) {
		return transition(startValue, endValue, interpolator, ms, typeConverter);
	}

	public static <T> Transition<T> transition(T startValue, Interpolator<T> interpolator, float speed, TypeConverter<T> typeConverter) {
		return new AutoUpdateableTransition<T>(startValue, speed, timeProvider, typeConverter);
	}

	public static <T> Transition<T> transition(T startValue, T endValue, Interpolator<T> interpolator, float speed, TypeConverter<T> typeConverter) {
		return new AutoUpdateableTransition<T>(startValue, endValue, speed, timeProvider, typeConverter);
	}

}