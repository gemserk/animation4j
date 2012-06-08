package com.gemserk.animation4j.interpolator.function;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class StepFunctionTest {
	
	@Test
	public void testLinearInterpolatorFunction() {
		InterpolationFunction stepFunction = InterpolationFunctions.step;
		
		 assertThat(stepFunction.interpolate(0), IsEqual.equalTo(0f));
		 assertThat(stepFunction.interpolate(0.4999f), IsEqual.equalTo(0f));
		 assertThat(stepFunction.interpolate(1f), IsEqual.equalTo(1f));
		 assertThat(stepFunction.interpolate(0.50001f), IsEqual.equalTo(1f));
		
	}
	
}
