package com.gemserk.animation4j.interpolator;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.interpolator.FloatStateInterpolator;


public class FloatInterpolatorTest {

	@Test
	public void test() {
		FloatStateInterpolator floatStateInterpolator = new FloatStateInterpolator(1000, 0.0f, 1.0f);
		assertThat(floatStateInterpolator.isFinished(), IsEqual.equalTo(false));
		assertThat(floatStateInterpolator.getInterpolatedValue(), IsEqual.equalTo(0.0f));
		floatStateInterpolator.update(100);
		assertThat(floatStateInterpolator.isFinished(), IsEqual.equalTo(false));
		assertThat(floatStateInterpolator.getInterpolatedValue(), IsEqual.equalTo(0.1f));
		floatStateInterpolator.update(900);
		assertThat(floatStateInterpolator.isFinished(), IsEqual.equalTo(true));
		assertThat(floatStateInterpolator.getInterpolatedValue(), IsEqual.equalTo(1.0f));
	}
	
}
