package com.gemserk.animation4j.interpolator.function;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class LinearBezierInterpolatorFunctionTest {
	
	@Test
	public void testLinearInterpolatorFunction() {
		InterpolationFunction interpolationFunction = new LinearBezierInterpolationFunction();
		assertThat(interpolationFunction.interpolate(0f), IsEqual.equalTo(0f));
		assertThat(interpolationFunction.interpolate(0.5f), IsEqual.equalTo(0.5f));
		assertThat(interpolationFunction.interpolate(1f), IsEqual.equalTo(1f));
	}
	
}
