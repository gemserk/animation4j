package com.gemserk.slick.animation.timeline;

import org.newdawn.slick.Color;

import com.gemserk.commons.animation.timeline.InterpolatedValue;
import com.gemserk.commons.animation.timeline.Interpolator;
import com.gemserk.commons.animation.timeline.LinearInterpolatorFactory;

public class ColorInterpolatedValue extends InterpolatedValue<Color> {

	public ColorInterpolatedValue(Interpolator<Color> interpolator, Color a, Color b) {
		super(interpolator, a, b);
	}

	public ColorInterpolatedValue(Color a, Color b) {
		this(LinearInterpolatorFactory.linearInterpolatorColor(), a, b);
	}

	public ColorInterpolatedValue(Color a) {
		this(a, new Color(a));
	}

	@Override
	public void setA(Color a) {
		this.a.r = a.r;
		this.a.g = a.g;
		this.a.b = a.b;
		this.a.a = a.a;
	}

	@Override
	public void setB(Color b) {
		this.b.r = b.r;
		this.b.g = b.g;
		this.b.b = b.b;
		this.b.a = b.a;
	}

	@Override
	protected float getLength() {
		return Math.abs(a.r - b.r) + Math.abs(a.g - b.g) + Math.abs(a.b - b.b) + Math.abs(a.a - b.a);
	}
}