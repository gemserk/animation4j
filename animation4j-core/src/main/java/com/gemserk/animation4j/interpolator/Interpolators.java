package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

/**
 * Provides factory methods to simplify Interpolator<T> creation.
 * 
 * @author acoppes
 * 
 */
public class Interpolators {

	/**
	 * Returns an Interpolator<Float>.
	 */
	public static Interpolator<Float> floatInterpolator() {
		return interpolator(Converters.floatValue());
	}

	/**
	 * Returns an Interpolator<Float> using the specified interpolation function.
	 */
	public static Interpolator<Float> floatInterpolator(InterpolatorFunction function) {
		return interpolator(Converters.floatValue(), function);
	}

	public static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter) {
		return new GenericInterpolator<T>(typeConverter);
	}

	public static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		return new GenericInterpolator<T>(typeConverter, functions);
	}

}