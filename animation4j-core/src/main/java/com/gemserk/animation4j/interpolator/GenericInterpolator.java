package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * Provides an implementation of the Interpolator interface using a TypeConverter to convert from the object to a float[] and vice versa, and a Interpolator<float[]> to interpolate float[] values.
 * 
 * @author acoppes
 */
public class GenericInterpolator<T> implements Interpolator<T> {

	private final TypeConverter<T> converter;
	private final Interpolator<float[]> interpolator;
	private float[] a, b;
	private T object;

	/**
	 * Creates a new GenericInterpolator using a type converter to map between T and float[] and vice versa and an Interpolator<float[]> to interpolate the values.
	 * 
	 * @param converter
	 *            The TypeConverter to be used to convert between T and float[].
	 * @param interpolator
	 *            The interpolator to be used to interpolate the float[] values.
	 */
	public GenericInterpolator(TypeConverter<T> converter, Interpolator<float[]> interpolator) {
		this.converter = converter;
		this.interpolator = interpolator;
	}

	/**
	 * Creates a new GenericInterpolator using a type converter to map between T and float[] and vice versa, with a default FloatArrayInterpolator with converter.variables() variables.
	 * 
	 * @param converter
	 *            The TypeConverter to be used to convert between T and float[].
	 */
	public GenericInterpolator(TypeConverter<T> converter) {
		this(converter, new FloatArrayInterpolator(converter.variables()));
	}

	/**
	 * Creates a new GenericInterpolator using a type converter to map between T and float[] and vice versa.
	 * 
	 * @param mutableObject
	 *            The mutable object to be modified when interpolating.
	 * @param converter
	 *            The TypeConverter to be used between T and float[].
	 */
	public GenericInterpolator(T mutableObject, TypeConverter<T> converter) {
		this(converter, new FloatArrayInterpolator(converter.variables()));
		this.object = mutableObject;
	}

	/**
	 * Creates a new GenericInterpolator using a type converter to map between T and float[] and vice versa, with a default FloatArrayInterpolator with converter.variables() variables.
	 * 
	 * @param converter
	 *            The TypeConverter to be used to convert between T and float[].
	 * @param functions
	 *            The functions to be used for each float[] variable when interpolating.
	 */
	public GenericInterpolator(TypeConverter<T> converter, InterpolationFunction... functions) {
		this(converter, new FloatArrayInterpolator(converter.variables(), functions));
	}

	public T interpolate(T t1, T t2, float t) {
		a = converter.copyFromObject(t1, a);
		b = converter.copyFromObject(t2, b);
		object = converter.copyToObject(object, interpolator.interpolate(a, b, t));
		return object;
	}

}