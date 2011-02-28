package com.gemserk.slick.animation.timeline;


import org.newdawn.slick.geom.Vector2f;

import com.gemserk.commons.animation.timeline.Interpolator;
import com.gemserk.commons.animation.timeline.LinearInterpolatorFactory;

public class Vector2fInterpolator implements Interpolator<Vector2f> {

	Interpolator<Float> floatInterpolator = LinearInterpolatorFactory.linearInterpolatorFloat();

	private Vector2f interpolatedVector = new Vector2f();

	@Override
	public Vector2f interpolate(Vector2f a, Vector2f b, float weight) {
		interpolatedVector.set(floatInterpolator.interpolate(a.x, b.x, weight), // 
				floatInterpolator.interpolate(a.y, b.y, weight));
		return interpolatedVector;
	}

}