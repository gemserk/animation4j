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
	public Color interpolate(Color a, Color b, Color out, float weight) {
		if (out == null)
			out = new Color(0);
		out.r = interpolator.interpolate(a.r, b.r, null, weight);
		out.g = interpolator.interpolate(a.g, b.g, null, weight);
		out.b = interpolator.interpolate(a.b, b.b, null, weight);
		out.a = interpolator.interpolate(a.a, b.a, null, weight);
		return out;
	}
}