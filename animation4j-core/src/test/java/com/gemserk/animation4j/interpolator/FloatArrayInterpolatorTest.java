package com.gemserk.animation4j.interpolator;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;

public class FloatArrayInterpolatorTest {

	@Test
	public void staticInterpolationTest() {
		float a[] = new float[] { 0f, 0f };
		float b[] = new float[] { 1f, 1f };
		float c[] = new float[2];

		FloatArrayInterpolator.interpolate(a, b, c, 0f, InterpolationFunctions.linear(), InterpolationFunctions.linear());

		assertThat(c[0], IsEqual.equalTo(0f));
		assertThat(c[1], IsEqual.equalTo(0f));

		FloatArrayInterpolator.interpolate(a, b, c, 1f);

		assertThat(c[0], IsEqual.equalTo(1f));
		assertThat(c[1], IsEqual.equalTo(1f));
	}

	@Test
	public void staticInterpolationTest2() {
		float a[] = new float[] { 0f, 0f };
		float b[] = new float[] { 1f, 1f };
		float c[] = new float[2];

		FloatArrayInterpolator.interpolate(a, b, c, 0.5f, InterpolationFunctions.linear(), InterpolationFunctions.quadraticEaseIn());

		assertThat(c[0], IsEqual.equalTo(0.5f));
		assertThat(c[1], IsEqual.equalTo(0.75f));
	}

	@Test
	public void staticInterpolationTest3() {
		float a[] = new float[] { 0f };
		float b[] = new float[] { 1f };
		float c[] = new float[1];
		FloatArrayInterpolator.interpolate(a, b, c, 0.5f);
		assertThat(c[0], IsEqual.equalTo(0.5f));
	}

}
