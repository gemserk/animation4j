package com.gemserk.animation4j.slick.interpolators;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class Vector2fInterpolator implements Interpolator<Vector2f> {

	private Interpolator<Float> interpolator;
	
	public Vector2fInterpolator() {
		this(InterpolatorFunctionFactory.linear());
	}
	
	public Vector2fInterpolator(InterpolatorFunction interpolatorFunction) {
		interpolator = new FloatInterpolator(interpolatorFunction);
	}
	
	@Override
	public Vector2f interpolate(Vector2f a, Vector2f b, Vector2f out, float weight) {
		if (out == null)
			out = new Vector2f();
		out.set(interpolator.interpolate(a.x, b.x, null, weight), 
				interpolator.interpolate(a.y, b.y, null, weight));
		return out;
	}

}