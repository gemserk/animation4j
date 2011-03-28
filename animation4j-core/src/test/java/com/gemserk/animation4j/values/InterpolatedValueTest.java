package com.gemserk.animation4j.values;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;

public class InterpolatedValueTest {

	static Interpolator<Float> linearFloatInterpolator = Interpolators.floatInterpolator(InterpolationFunctions.linear());

	@Test
	public void shouldStartNotFinished() {
		InterpolatedValue<Float> interpolatedValue = new FloatInterpolatedValue(linearFloatInterpolator, 10f, 10f);
		assertEquals(false, interpolatedValue.isFinished());
		interpolatedValue.setAlpha(1f);
		assertEquals(true, interpolatedValue.isFinished());
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
