package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

/**
 * Provides a way to infer a linear Interpolator for the type of the value. 
 * 
 * @author acoppes
 */
public class LinearInterpolatorInferator {
	
	/**
	 * Infer a linear interpolator for the value.
	 * @param value - The value to use to infer the asociated linear interpolator.
	 * @return A linear interpolator for the type of the value.
	 */
	@SuppressWarnings("rawtypes")
	public static Interpolator inferLinearInterpolator(Object value) {

		if (value instanceof Float)
			return new FloatInterpolator(InterpolatorFunctionFactory.linear());

		return null;
	}
}