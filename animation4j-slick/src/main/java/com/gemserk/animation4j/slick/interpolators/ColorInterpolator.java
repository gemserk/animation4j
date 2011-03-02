package com.gemserk.animation4j.slick.interpolators;

import org.newdawn.slick.Color;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class ColorInterpolator implements Interpolator<Color> {
	
	private Interpolator<Float> floatInterpolator;
	
	public ColorInterpolator() {
		this(InterpolatorFunctionFactory.linear());
	}
	
	public ColorInterpolator(InterpolatorFunction interpolatorFunction) {
		floatInterpolator = new FloatInterpolator(interpolatorFunction);
	}

	@Override
	public Color interpolate(Color a, Color b, float weight) {
		return new Color(floatInterpolator.interpolate(a.r, b.r, weight), // 
				floatInterpolator.interpolate(a.g, b.g, weight), // 
				floatInterpolator.interpolate(a.b, b.b, weight), // 
				floatInterpolator.interpolate(a.a, b.a, weight));
	}
}