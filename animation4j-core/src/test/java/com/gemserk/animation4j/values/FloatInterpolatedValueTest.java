package com.gemserk.animation4j.values;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class FloatInterpolatedValueTest {
	
	@Test
	public void shouldReturnInterpolatedValue() {
		
		FloatInterpolatedValue floatInterpolatedValue = new FloatInterpolatedValue(5.0f, 10.0f);
		floatInterpolatedValue.setAlpha(0f);
		assertThat(floatInterpolatedValue.getInterpolatedValue(), IsEqual.equalTo(5.0f));
		floatInterpolatedValue.setAlpha(0.5f);
		assertThat(floatInterpolatedValue.getInterpolatedValue(), IsEqual.equalTo(7.5f));
		floatInterpolatedValue.setAlpha(1f);
		assertThat(floatInterpolatedValue.getInterpolatedValue(), IsEqual.equalTo(10f));
		
	}
	
	@Test
	public void shouldReturnFirstValueIfAlphaLessThanZero() {
		FloatInterpolatedValue floatInterpolatedValue = new FloatInterpolatedValue(5.0f, 10.0f);
		floatInterpolatedValue.setAlpha(-10f);
		assertThat(floatInterpolatedValue.getInterpolatedValue(), IsEqual.equalTo(5.0f));
	}

	@Test
	public void shouldReturnSecondValueIfAlphaGreaterThanOne() {
		FloatInterpolatedValue floatInterpolatedValue = new FloatInterpolatedValue(5.0f, 10.0f);
		floatInterpolatedValue.setAlpha(1.1f);
		assertThat(floatInterpolatedValue.getInterpolatedValue(), IsEqual.equalTo(10f));
	}

}
