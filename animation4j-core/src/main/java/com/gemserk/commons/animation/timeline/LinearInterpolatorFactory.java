package com.gemserk.commons.animation.timeline;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * Provides a way to build linear interpolators of most common types.
 * @author acoppes
 */
public class LinearInterpolatorFactory {

	private static class Vector2fLinearInterpolator implements Interpolator<Vector2f> {
		@Override
		public Vector2f interpolate(Vector2f a, Vector2f b, float weight) {
			return new Vector2f(LinearInterpolatorFactory.interpolate(a.x, b.x, weight), LinearInterpolatorFactory.interpolate(a.y, b.y, weight));
		}
	}

	private static float interpolate(float a, float b, float weight) {
		return a * (1 - weight) + b * weight;
	}

	public static Interpolator<Vector2f> linearInterpolatorVector2f() {
		return new Vector2fLinearInterpolator();
	}

	public static Interpolator<Color> linearInterpolatorColor() {
		return new Interpolator<Color>() {
			@Override
			public Color interpolate(Color a, Color b, float weight) {
				return new Color(LinearInterpolatorFactory.interpolate(a.r, b.r, weight), // 
						LinearInterpolatorFactory.interpolate(a.g, b.g, weight), // 
						LinearInterpolatorFactory.interpolate(a.b, b.b, weight), // 
						LinearInterpolatorFactory.interpolate(a.a, b.a, weight));
			}
		};
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
		
		if (value instanceof Vector2f)
			return linearInterpolatorVector2f();
		
		if (value instanceof Color) 
			return linearInterpolatorColor();
		
		if (value instanceof Float) 
			return linearInterpolatorFloat();
		
		return null;
	}
}