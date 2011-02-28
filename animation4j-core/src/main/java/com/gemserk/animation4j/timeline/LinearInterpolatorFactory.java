package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolators.Interpolator;


/**
 * Provides a way to build linear interpolators of most common types.
 * @author acoppes
 */
public class LinearInterpolatorFactory {

	private static float interpolate(float a, float b, float weight) {
		return a * (1 - weight) + b * weight;
	}

	public static Interpolator<Float> linearInterpolatorFloat() {
		return new Interpolator<Float>() {
			
			@Override
			public Float interpolate(Float a, Float b, float weight) {
				return LinearInterpolatorFactory.interpolate(a, b, weight);
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	public static Interpolator inferLinearInterpolator(Object value) {
		
		if (value instanceof Float) 
			return linearInterpolatorFloat();
		
		return null;
	}
}