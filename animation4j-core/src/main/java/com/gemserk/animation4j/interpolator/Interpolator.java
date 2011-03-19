package com.gemserk.animation4j.interpolator;

/**
 * Provides a way to interpolate values between two values based on a weight and an interpolation function. 
 * @param <T> the type of the variable to interpolate.
 * @author acoppes
 */
public interface Interpolator<T> {

	T interpolate(T a, T b, T out, float weight);

}