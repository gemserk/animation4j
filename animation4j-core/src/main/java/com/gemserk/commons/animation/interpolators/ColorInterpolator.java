package com.gemserk.commons.animation.interpolators;

import org.newdawn.slick.Color;

import com.gemserk.commons.animation.interpolators.FloatInterpolator;
import com.gemserk.commons.animation.interpolators.Interpolator;

public class ColorInterpolator implements Interpolator<Color> {

	FloatInterpolator rInterpolator;

	FloatInterpolator gInterpolator;

	FloatInterpolator bInterpolator;

	FloatInterpolator aInterpolator;

	public ColorInterpolator(int time, Color from, Color to) {
		rInterpolator = new FloatInterpolator(time, from.r, to.r);
		gInterpolator = new FloatInterpolator(time, from.g, to.g);
		bInterpolator = new FloatInterpolator(time, from.b, to.b);
		aInterpolator = new FloatInterpolator(time, from.a, to.a);
	}

	public void update(int delta) {
		rInterpolator.update(delta);
		gInterpolator.update(delta);
		bInterpolator.update(delta);
		aInterpolator.update(delta);
	}

	public Color getInterpolatedValue() {
		return new Color(rInterpolator.getInterpolatedValue(), gInterpolator.getInterpolatedValue(), bInterpolator.getInterpolatedValue(), aInterpolator.getInterpolatedValue());
	}

	@Override
	public void reset() {
		rInterpolator.reset();
		gInterpolator.reset();
		bInterpolator.reset();
		aInterpolator.reset();
	}

	@Override
	public boolean isFinished() {
		return rInterpolator.isFinished() && gInterpolator.isFinished() && bInterpolator.isFinished() && aInterpolator.isFinished();
	}

}