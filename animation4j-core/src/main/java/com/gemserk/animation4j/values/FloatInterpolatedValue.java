package com.gemserk.animation4j.values;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.timeline.InterpolatorProvider;

/**
 * An implementation of InterpolatedValue using Float as the specific type.
 * @author acoppes
 */
public class FloatInterpolatedValue extends InterpolatedValue<Float> {

	public FloatInterpolatedValue(Interpolator<Float> interpolator, Float a, Float b) {
		super(interpolator, a, b);
	}

	public FloatInterpolatedValue(Float a, Float b) {
		this(new FloatInterpolator(InterpolatorFunctionFactory.linear()), a, b);
	}

	public FloatInterpolatedValue(Float a) {
		this(a, a);
	}

	@Override
	protected float getLength() {
		return Math.abs(getB() - getA());
	}
}