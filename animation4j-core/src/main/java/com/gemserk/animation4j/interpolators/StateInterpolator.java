package com.gemserk.animation4j.interpolators;

/**
 * An Interpolator implementation with internal state.
 * @param <T> The type of the interpolated variable.
 * @author acoppes
 */
public interface StateInterpolator<T> {
	
	void update(int delta);
	
	void reset();

	T getInterpolatedValue();

	boolean isFinished();

}