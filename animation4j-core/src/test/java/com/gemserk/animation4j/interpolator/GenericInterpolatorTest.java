package com.gemserk.animation4j.interpolator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.converters.TypeConverter;


public class GenericInterpolatorTest {

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

		GenericInterpolator<Color> arrayInterpolator = new GenericInterpolator<Color>(colorConverter, new FloatArrayInterpolator(4));

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

		FloatArrayInterpolator interpolator = new FloatArrayInterpolator(new float[4]);

		float[] a = new float[] { 0f, 0f, 0f, 0f };
		float[] b = new float[] { 1f, 1f, 1f, 1f };

		float[] out = interpolator.interpolate(a, b, 0.5f);

		assertThat(out[0], IsEqual.equalTo(0.5f));
		assertThat(out[1], IsEqual.equalTo(0.5f));
		assertThat(out[2], IsEqual.equalTo(0.5f));
		assertThat(out[3], IsEqual.equalTo(0.5f));

	}
	
	static class Vector2f {
		
		float x,y;
		
		public Vector2f(float x, float y) {
			set(x,y);
			System.out.println("vector2f created!!");
		}
		
		public void set(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
	}
	
	@Test
	public void test3() {

		// converter uses a temporary vector to return
		// WARNING: if converter is used multiple times, it will modify the vector already returned.
		
		TypeConverter<Vector2f> converter = new TypeConverter<Vector2f>() {
			
			Vector2f tmp = new Vector2f(0, 0);

			@Override
			public float[] copyFromObject(Vector2f v, float[] x) {
				x[0] = v.x;
				x[1] = v.y;
				return x;
				
			}

			@Override
			public Vector2f copyToObject(Vector2f v, float[] x) {
				if (v == null)
					v = tmp;
				v.x = x[0];
				v.y = x[1];
				return v;
			}
			
		};

		GenericInterpolator<Vector2f> arrayInterpolator = new GenericInterpolator<Vector2f>(converter, new FloatArrayInterpolator(2));

		Vector2f t1 = new Vector2f(100f, 100f);
		Vector2f t2 = new Vector2f(200f, 200f);
		
		Vector2f result = arrayInterpolator.interpolate(t1, t2, 0.5f);
		result = arrayInterpolator.interpolate(t1, t2, 0.5f);
		result = arrayInterpolator.interpolate(t1, t2, 0.5f);
		result = arrayInterpolator.interpolate(t1, t2, 0.5f);
		result = arrayInterpolator.interpolate(t1, t2, 0.5f);

		assertNotNull(result);
		assertThat(result.x, IsEqual.equalTo(150f));
		assertThat(result.y, IsEqual.equalTo(150f));
		
	}

}
