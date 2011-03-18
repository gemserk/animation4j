package com.gemserk.animation4j.interpolator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;


public class ArrayInterpolatorTest {

	@Test
	public void test() {
		
		ArrayInterpolator<Color> arrayInterpolator = new ArrayInterpolator<Color>(InterpolatorFunctionFactory.linear(), 4) {

			@Override
			public void copyFromObject(Color color, float[] x) {
				color.getRGBComponents(x);
			}

			@Override
			public Color copyToObject(Color color, float[] x) {
				return new Color(x[0], x[1], x[2], x[3]);
			}
			
		};
		
		Color result = arrayInterpolator.interpolate(new Color(0f, 0f, 0f, 0f), new Color(1f, 1f, 1f, 1f), 0.5f);
		
		assertNotNull(result);
		float[] rgb = result.getRGBComponents(null);
		assertThat(rgb[0], IsEqual.equalTo(0.5f));
		assertThat(rgb[1], IsEqual.equalTo(0.5f));
		assertThat(rgb[2], IsEqual.equalTo(0.5f));
		assertThat(rgb[3], IsEqual.equalTo(0.5f));
		
	}
	
}
