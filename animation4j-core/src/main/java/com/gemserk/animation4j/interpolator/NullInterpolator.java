package com.gemserk.animation4j.interpolator;

/**
 * Null implementation of Interpolator<T>, will return always first value.
 * @author acoppes
 */
public class NullInterpolator<T> implements Interpolator<T> {

	@Override
	public T interpolate(T a, T b, T out, float weight) {
		return a;
	}

}
