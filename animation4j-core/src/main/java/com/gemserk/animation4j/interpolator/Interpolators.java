package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * Provides factory methods to simplify Interpolator<T> creation.
 * 
 * @author acoppes
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
	public static Interpolator<Float> floatInterpolator(InterpolationFunction function) {
		return interpolator(Converters.floatValue(), function);
	}

	/**
	 * Returns an Interpolator<T> with internal cache, using the specified type converter.
	 * 
	 * @param typeConverter
	 *            The TypeConverter to convert your type to float[] and vice versa.
	 */
	public static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter) {
		return new GenericInterpolator<T>(typeConverter);
	}

	/**
	 * Returns an Interpolator<T> with internal cache, using the specified type converter.
	 * 
	 * @param typeConverter
	 *            The TypeConverter to convert your type to float[] and vice versa.
	 * @param functions
	 *            The functions to be used when interpolating the values.
	 */
	public static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter, InterpolationFunction... functions) {
		return new GenericInterpolator<T>(typeConverter, functions);
	}

}