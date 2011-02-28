package com.gemserk.commons.animation.interpolators;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.commons.animation.interpolators.FloatInterpolator;
import com.gemserk.commons.animation.interpolators.Interpolator;

public class Vector2fInterpolator implements Interpolator<Vector2f> {

	FloatInterpolator xInterpolator;

	FloatInterpolator yInterpolator;
	
	public Vector2fInterpolator(int time, Vector2f from, Vector2f to) {
		xInterpolator = new FloatInterpolator(time, from.x, to.x);
		yInterpolator = new FloatInterpolator(time, from.y, to.y);
	}

	public void update(int delta) {
		xInterpolator.update(delta);
		yInterpolator.update(delta);
	}

	public Vector2f getInterpolatedValue() {
		return new Vector2f(xInterpolator.getInterpolatedValue(), yInterpolator.getInterpolatedValue());
	}
	
	@Override
	public void reset() {
		xInterpolator.reset();
		yInterpolator.reset();
	}

	@Override
	public boolean isFinished() {
		return xInterpolator.isFinished() && yInterpolator.isFinished();
	}

}