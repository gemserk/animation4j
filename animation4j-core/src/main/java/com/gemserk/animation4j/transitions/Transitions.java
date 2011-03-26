package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

/**
 * Provides an easy way to build transitions.
 * 
 * @author acoppes
 */
public class Transitions {

	public static float defaultTransitionSpeed = 1f / 1000f;

	public static TimeProvider timeProvider = new SystemTimeProvider();

	/**
	 * Builds a transition specifying the starting value, using default speed of 1 unit per ms.
	 * 
	 * @param startValue
	 *            Starting value of the transition.
	 * @param typeConverter
	 *            The TypeConverter to use inside the transition to convert from float[] to T and vice versa.
	 */
	public static <T> Transition<T> transition(T startValue, TypeConverter<T> typeConverter) {
		return transition(startValue, defaultTransitionSpeed, typeConverter);
	}

	/**
	 * Builds a transition specifying the starting value, using default speed of 1 unit per ms.
	 * 
	 * @param startValue
	 *            Starting value of the transition.
	 * @param typeConverter
	 *            The TypeConverter to use inside the transition to convert from float[] to T and vice versa.
	 * @param functions
	 *            The interpolation functions to be used to interpolate each variable of T.
	 */
	public static <T> Transition<T> transition(T startValue, TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		return transition(startValue, defaultTransitionSpeed, typeConverter, functions);
	}

	/**
	 * Builds a transition specifying the starting value.
	 * 
	 * @param startValue
	 *            Starting value of the transition.
	 * @param typeConverter
	 *            The TypeConverter to use inside the transition to convert from float[] to T and vice versa.
	 * @param speed
	 *            The change speed of the interpolation.
	 */
	public static <T> Transition<T> transition(T startValue, float speed, TypeConverter<T> typeConverter) {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables());
		InternalTransition<T> internalTransition = new InternalTransition<T>(startValue, typeConverter, interpolator);
		return new TransitionImpl<T>(internalTransition, speed, timeProvider);
	}

	/**
	 * Builds a transition using specified parameters.
	 * 
	 * @param startValue
	 *            Starting value of the transition.
	 * @param typeConverter
	 *            The TypeConverter to use inside the transition to convert from float[] to T and vice versa.
	 * @param speed
	 *            The change speed of the interpolation..
	 * @param functions
	 *            The interpolation functions to be used to interpolate each variable of T.
	 */
	public static <T> Transition<T> transition(T startValue, float speed, TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(typeConverter.variables(), functions);
		InternalTransition<T> internalTransition = new InternalTransition<T>(startValue, typeConverter, interpolator);
		return new TransitionImpl<T>(internalTransition, speed, timeProvider);
	}

	/**
	 * Builds a transition given a start value and inferring the corresponding TypeConverter from the Converters factory.
	 * 
	 * @param startValue
	 *            Starting value of the transition.
	 */
	public static <T> Transition<T> transition(T startValue) {
		TypeConverter converter = Converters.converter(startValue.getClass());
		return transition(startValue, converter);
	}

	/**
	 * Builds a transition given a start value and inferring the corresponding TypeConverter from the Converters factory.
	 * 
	 * @param startValue
	 *            Starting value of the transition.
	 * @param functions
	 *            The interpolation functions to be used to interpolate each variable of T.
	 */
	public static <T> Transition<T> transition(T startValue, InterpolatorFunction... functions) {
		TypeConverter converter = Converters.converter(startValue.getClass());
		return transition(startValue, converter, functions);
	}

}