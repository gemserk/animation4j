package com.gemserk.commons.animation.timeline;

/**
 * Provides a way to interpolate values between two values based on a weight and an interpolation function.
 * @param <T> the type of the variable to interpolate.
 * @author acoppes
 */
public interface Interpolator<T> {

	T interpolate(T a, T b, float weight);

}