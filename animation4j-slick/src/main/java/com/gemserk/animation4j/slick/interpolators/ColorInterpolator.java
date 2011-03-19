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
		interpolator = FloatInterpolator.floatInterpolator(interpolatorFunction);
	}

	@Override
	public Color interpolate(Color a, Color b, float weight) {
		Color out = new Color(0);
		out.r = interpolator.interpolate(a.r, b.r, weight);
		out.g = interpolator.interpolate(a.g, b.g, weight);
		out.b = interpolator.interpolate(a.b, b.b, weight);
		out.a = interpolator.interpolate(a.a, b.a, weight);
		return out;
	}
}