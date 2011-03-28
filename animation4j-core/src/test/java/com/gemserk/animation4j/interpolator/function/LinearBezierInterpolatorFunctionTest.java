package com.gemserk.animation4j.interpolator.function;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class LinearBezierInterpolatorFunctionTest {
	
	@Test
	public void testLinearInterpolatorFunction() {
		InterpolationFunction interpolationFunction = new LinearBezierInterpolatorFunction(1f, 2f);
		assertThat(interpolationFunction.interpolate(0.5f), IsEqual.equalTo(1.5f));
	}
	
	@Test
	public void shouldReturnFirstPointWhenTLessThanZero() {
		InterpolationFunction interpolationFunction = new LinearBezierInterpolatorFunction(1f, 2f);
		assertThat(interpolationFunction.interpolate(-1), IsEqual.equalTo(1f));
	}
	
	@Test
	public void shouldReturnSecondPointWhenTGreaterThanOne() {
		InterpolationFunction interpolationFunction = new LinearBezierInterpolatorFunction(1f, 2f);
		assertThat(interpolationFunction.interpolate(2f), IsEqual.equalTo(2f));
	}

}
