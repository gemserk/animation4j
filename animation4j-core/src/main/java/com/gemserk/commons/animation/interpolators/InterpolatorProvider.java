package com.gemserk.commons.animation.interpolators;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;


public class InterpolatorProvider {

	// TODO: convert to a dynamic provider, register interpolators, etc
	
	@SuppressWarnings("unchecked")
	public static Interpolator getInterpolator(int time, Object startValue, Object endValue) {
		
		if (startValue instanceof Float && endValue instanceof Float) {
			return new FloatInterpolator(time, (Float) startValue, (Float) endValue);
		}
		
		if (startValue instanceof Vector2f && endValue instanceof Vector2f) {
			return new Vector2fInterpolator(time, (Vector2f) startValue, (Vector2f) endValue);
		}

		if (startValue instanceof Color && endValue instanceof Color) {
			return new ColorInterpolator(time, (Color) startValue, (Color) endValue);
		}
		
		throw new RuntimeException("interpolator for class " + startValue.getClass().getName() + " not found.");
	}

}