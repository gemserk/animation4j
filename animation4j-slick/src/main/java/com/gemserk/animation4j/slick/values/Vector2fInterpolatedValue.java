package com.gemserk.animation4j.slick.values;

import org.newdawn.slick.geom.Vector2f;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.slick.interpolators.Vector2fInterpolator;
import com.gemserk.animation4j.values.InterpolatedValue;

public class Vector2fInterpolatedValue extends InterpolatedValue<Vector2f> {

	private final Vector2f difference = new Vector2f();

	public Vector2fInterpolatedValue(Interpolator<Vector2f> interpolator, Vector2f a, Vector2f b) {
		super(interpolator, a, b);
	}
	
	public Vector2fInterpolatedValue(Vector2f a, Vector2f b) {
		this(new Vector2fInterpolator(), a , b);
	}
	
	public Vector2fInterpolatedValue(Vector2f a) {
		this(a, new Vector2f(a));
	}

	@Override
	public void setA(Vector2f a) {
		this.a.set(a);
	}

	@Override
	public void setB(Vector2f b) {
		this.b.set(b);
	}

	@Override
	protected float getLength() {

		difference.set(getB());
		difference.sub(getA());

		return difference.length();
	}
}