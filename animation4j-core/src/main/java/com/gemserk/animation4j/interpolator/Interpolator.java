package com.gemserk.animation4j.interpolator;

/**
 * Provides a way to interpolate values between two values based on a weight and an interpolation function.
 * 
 * @param <T>
 *            the type of the variable to interpolate.
 * @author acoppes
 */
public interface Interpolator<T> {

	/**
	 * Returns an interpolated object between a and b, based on variable t.
	 * 
	 * @param a
	 *            - The left value.
	 * @param b
	 *            - The right value.
	 * @param t
	 *            - The interpolation value.
	 * @return An interpolated object between a and b.
	 */
	T interpolate(T a, T b, float t);

}