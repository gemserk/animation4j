package com.gemserk.animation4j.slick.interpolators;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.GenericInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.slick.converters.SlickConverters;


public class Vector2fInterpolatorTest {
	
	@Test
	public void testVector2fInterpolator() {
		
		Interpolator<Vector2f> vector2fInterpolator = new GenericInterpolator<Vector2f>(SlickConverters.vector2f(), new FloatArrayInterpolator(2));
		
		Vector2f interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), 0f);
		
		assertThat(interpolatedVector.x, IsEqual.equalTo(100f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(100f));
		
		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), 0.5f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(150f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(150f));

		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), 1f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(200f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(200f));
		
		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), -1f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(100f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(100f));

		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), 1.1f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(200f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(200f));

	}

}
