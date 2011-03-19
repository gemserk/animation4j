package com.gemserk.animation4j.slick.interpolators;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;


public class Vector2fInterpolatorTest {
	
	@Test
	public void testVector2fInterpolator() {
		
		Vector2fInterpolator vector2fInterpolator = new Vector2fInterpolator();
		
		Vector2f interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), null, 0f);
		
		assertThat(interpolatedVector.x, IsEqual.equalTo(100f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(100f));
		
		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), null, 0.5f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(150f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(150f));

		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), null, 1f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(200f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(200f));
		
		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), null, -1f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(100f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(100f));

		interpolatedVector = vector2fInterpolator.interpolate(new Vector2f(100,100), new Vector2f(200,200), null, 1.1f);

		assertThat(interpolatedVector.x, IsEqual.equalTo(200f));
		assertThat(interpolatedVector.y, IsEqual.equalTo(200f));

	}

}
