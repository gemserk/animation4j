package com.gemserk.commons.animation.timeline;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InterpolatedValueTest {

	public static class FloatInterpolatedValue extends InterpolatedValue<Float> {
		public FloatInterpolatedValue(Interpolator<Float> interpolator, Float a, Float b) {
			super(interpolator, a, b);
		}

		@Override
		protected float getLength() {
			return Math.abs(getB() - getA());
		}
	}

	static Interpolator<Float> linearFloatInterpolator = LinearInterpolatorFactory.linearInterpolatorFloat();

	@Test
	public void shouldStartNotFinished() {
		InterpolatedValue<Float> interpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 10f);
		assertEquals(false, interpolatedValue.isFinnished());
		interpolatedValue.setAlpha(1f);
		assertEquals(true, interpolatedValue.isFinnished());
	}

	@Test
	public void shouldStartOnFirstValue() {
		InterpolatedValue<Float> interpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 20f);
		assertEquals(10f, interpolatedValue.getInterpolatedValue(), 0.01f);
	}

	@Test
	public void shouldReturnInterpolatedValue() {
		InterpolatedValue<Float> interpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 20f);
		interpolatedValue.setAlpha(0.5f);
		assertEquals(15f, interpolatedValue.getInterpolatedValue(), 0.01f);
	}

	@Test
	public void shouldNotSurpassEndValue() {
		InterpolatedValue<Float> interpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 20f);
		interpolatedValue.setAlpha(2f);
		assertEquals(20f, interpolatedValue.getInterpolatedValue(), 0.01f);
	}

	@Test
	public void shouldUseInterpolatedValueNormalized() {
		InterpolatedValue<Float> normalizedInterpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 20f);
		assertEquals(10f, normalizedInterpolatedValue.getInterpolatedValue(), 0.01f);
		normalizedInterpolatedValue.setNormalizedAlpha(5f);
		assertEquals(15f, normalizedInterpolatedValue.getInterpolatedValue(), 0.01f);
	}

	@Test
	public void shouldNotFailWhenBEqualsA() {
		InterpolatedValue<Float> normalizedInterpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 10f);
		assertEquals(10f, normalizedInterpolatedValue.getInterpolatedValue(), 0.01f);
		normalizedInterpolatedValue.setNormalizedAlpha(5f);
		assertEquals(10f, normalizedInterpolatedValue.getInterpolatedValue(), 0.01f);

	}
}
