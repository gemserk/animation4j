package com.gemserk.animation4j.slick.interpolators;

import org.newdawn.slick.Color;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class ColorInterpolator implements Interpolator<Color> {
	
	private Interpolator<Float> interpolator;
	
	public ColorInterpolator() {
		this(InterpolatorFunctionFactory.linear());
	}
	
	public ColorInterpolator(InterpolatorFunction interpolatorFunction) {
		interpolator = new FloatInterpolator(interpolatorFunction);
	}

	@Override
	public Color interpolate(Color a, Color b, float weight) {
		return new Color(interpolator.interpolate(a.r, b.r, weight), // 
				interpolator.interpolate(a.g, b.g, weight), // 
				interpolator.interpolate(a.b, b.b, weight), // 
				interpolator.interpolate(a.a, b.a, weight));
	}
}