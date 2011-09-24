package com.gemserk.animation4j.interpolator;

/**
 * Provides a way to interpolate values between two values based on a weight and an interpolation function.
 * 
 * @param <T>
 *            The type of the variable to interpolate.
 * @author acoppes
 */
public interface Interpolator<T> {

	/**
	 * Returns an interpolated object between a and b, based on variable t.
	 * 
	 * @param a
	 *            The left value.
	 * @param b
	 *            The right value.
	 * @param t
	 *            The interpolation value.
	 * @return An interpolated object between a and b.
	 */
	T interpolate(T a, T b, float t);
	
	// maybe a method:
	
	// T interpolate(T a, T b, float t, InterpolationFunction ... functions);
	
	// right now each implementation has its own functions, with this new method you could have default interpolation functions
	// or custom interpolation functions for one interpolation
	
	// another useful method could be something for mutable objects:
	
	// void interpolate(T t, T a, T b, float t)
	
	// or just modify GenericInterpolator to accept the variable object and if it is mutable it will modify it, else it would not...
	
}