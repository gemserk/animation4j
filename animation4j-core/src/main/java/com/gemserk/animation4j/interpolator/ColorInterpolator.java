package com.gemserk.animation4j.interpolator;

import java.awt.Color;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class ColorInterpolator implements Interpolator<Color> {

	private final InterpolatorFunction interpolatorFunction;
	
	public ColorInterpolator() {
		this(InterpolatorFunctionFactory.linear());
	}

	public ColorInterpolator(InterpolatorFunction interpolatorFunction) {
		this.interpolatorFunction = interpolatorFunction;
	}

	@Override
	public Color interpolate(Color a, Color b, float t) {
		float x = interpolatorFunction.interpolate(t);
		float newR = a.getRed() * (1 - x) + b.getRed() * x;
		float newG = a.getGreen() * (1 - x) + b.getGreen() * x;
		float newB = a.getBlue() * (1 - x) + b.getBlue() * x;
		float newA = a.getAlpha() * (1 - x) + b.getAlpha() * x;
		return new Color((int) newR, (int) newG, (int) newB, (int) newA);
	}

}