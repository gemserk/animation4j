package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

public class Transitions {

	private static float ms = 1f / 1000f;

	public static TimeProvider timeProvider = new SystemTimeProvider();

	public static <T> Transition<T> transition(T startValue, TypeConverter<T> typeConverter) {
		return transition(startValue, ms, typeConverter);
	}
	
	public static <T> Transition<T> transition(T startValue, TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		return transition(startValue, ms, typeConverter, functions);
	}

	public static <T> Transition<T> transition(T startValue, float speed, TypeConverter<T> typeConverter) {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables());
		TransitionImpl<T> transition = new TransitionImpl<T>(startValue, typeConverter, interpolator);
		return new AutoUpdateableTransition<T>(transition, speed, timeProvider);
	}

	public static <T> Transition<T> transition(T startValue, float speed, TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables(), functions);
		TransitionImpl<T> transition = new TransitionImpl<T>(startValue, typeConverter, interpolator);
		return new AutoUpdateableTransition<T>(transition, speed, timeProvider);
	}

}