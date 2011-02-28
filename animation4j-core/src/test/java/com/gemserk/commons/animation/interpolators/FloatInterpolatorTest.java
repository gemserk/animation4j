package com.gemserk.commons.animation.interpolators;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class FloatInterpolatorTest {

	@Test
	public void test() {
		FloatInterpolator floatInterpolator = new FloatInterpolator(1000, 0.0f, 1.0f);
		assertThat(floatInterpolator.isFinished(), IsEqual.equalTo(false));
		assertThat(floatInterpolator.getInterpolatedValue(), IsEqual.equalTo(0.0f));
		floatInterpolator.update(100);
		assertThat(floatInterpolator.isFinished(), IsEqual.equalTo(false));
		assertThat(floatInterpolator.getInterpolatedValue(), IsEqual.equalTo(0.1f));
		floatInterpolator.update(900);
		assertThat(floatInterpolator.isFinished(), IsEqual.equalTo(true));
		assertThat(floatInterpolator.getInterpolatedValue(), IsEqual.equalTo(1.0f));
	}
	
}
