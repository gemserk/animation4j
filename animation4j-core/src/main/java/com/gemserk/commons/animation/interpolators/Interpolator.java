package com.gemserk.commons.animation.interpolators;

public interface Interpolator<T> {
	
//	/**
//	 * Sets internal values from interpolator.
//	 * Added because I want to have these values in the interface.
//	 * @param time total time of the interpolation
//	 * @param from the value to start the interpolation
//	 * @param to the value to end the interpolation
//	 * @return
//	 */
//	Interpolator<T> configure(int time, T from, T to);

	void update(int delta);
	
	void reset();

	T getInterpolatedValue();

	boolean isFinished();

}