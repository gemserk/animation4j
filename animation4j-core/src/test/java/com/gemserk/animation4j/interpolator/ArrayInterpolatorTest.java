package com.gemserk.animation4j.interpolator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.interpolator.ArrayInterpolator.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class ArrayInterpolatorTest {

	@Test
	public void test() {

		// if Color wasn't immutable then we could maintain a local color :
		// Color color;
		// the problem is, the interpolator could not be reused for multiple objects.
		
		TypeConverter<Color> colorConverter = new TypeConverter<Color>() {
			@Override
			public float[] copyFromObject(Color color, float[] x) {
				color.getRGBComponents(x);
				return x;
			}

			@Override
			public Color copyToObject(Color color, float[] x) {
				return new Color(x[0], x[1], x[2], x[3]);
			}
		};

		ArrayInterpolator<Color> arrayInterpolator = new ArrayInterpolator<Color>(InterpolatorFunctionFactory.linear(), colorConverter, 4);

		Color startColor = new Color(0f, 0f, 0f, 0f);
		Color endColor = new Color(1f, 1f, 1f, 1f);

		Color result = arrayInterpolator.interpolate(startColor, endColor, 0.5f);

		assertNotNull(result);
		float[] rgb = result.getRGBComponents(null);
		assertThat(rgb[0], IsEqual.equalTo(0.5f));
		assertThat(rgb[1], IsEqual.equalTo(0.5f));
		assertThat(rgb[2], IsEqual.equalTo(0.5f));
		assertThat(rgb[3], IsEqual.equalTo(0.5f));

	}

	@Test
	public void test2() {

		FloatArrayInterpolator interpolator = new FloatArrayInterpolator(new float[4], InterpolatorFunctionFactory.linear());

		float[] a = new float[] { 0f, 0f, 0f, 0f };
		float[] b = new float[] { 1f, 1f, 1f, 1f };

		float[] out = interpolator.interpolate(a, b, 0.5f);

		assertThat(out[0], IsEqual.equalTo(0.5f));
		assertThat(out[1], IsEqual.equalTo(0.5f));
		assertThat(out[2], IsEqual.equalTo(0.5f));
		assertThat(out[3], IsEqual.equalTo(0.5f));

	}

}
