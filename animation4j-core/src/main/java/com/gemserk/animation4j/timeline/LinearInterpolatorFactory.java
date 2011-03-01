package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolators.FloatInterpolator;
import com.gemserk.animation4j.interpolators.Interpolator;
import com.gemserk.animation4j.interpolators.InterpolatorFunctionFactory;

/**
 * Provides a way to build linear interpolators of most common types.
 * 
 * @author acoppes
 */
public class LinearInterpolatorFactory {

	public static Interpolator<Float> linearInterpolatorFloat() {
		return new FloatInterpolator(InterpolatorFunctionFactory.linear());
	}

	@SuppressWarnings("rawtypes")
	public static Interpolator inferLinearInterpolator(Object value) {

		if (value instanceof Float)
			return linearInterpolatorFloat();

		return null;
	}
}