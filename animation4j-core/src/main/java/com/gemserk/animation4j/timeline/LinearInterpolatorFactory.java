package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

/**
 * Provides a way to build linear interpolators of most common types.
 * 
 * @author acoppes
 */
public class LinearInterpolatorFactory {

	@SuppressWarnings("rawtypes")
	public static Interpolator inferLinearInterpolator(Object value) {

		if (value instanceof Float)
			return new FloatInterpolator(InterpolatorFunctionFactory.linear());

		return null;
	}
}