package com.gemserk.animation4j;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;

public class AnimationBuilderTest2 {

	@Before
	public void before() {
		Converters.register(Vector2f.class, new Vector2fConverter());
		Converters.register(Float.class, Converters.floatValue());
	}

	@After
	public void after() {
		Converters.unregister(Vector2f.class);
		Converters.unregister(Float.class);
	}

	@Test
	public void testAnimationBuilder() {
		TimelineAnimation timelineAnimation = Builders.animation( //
				Builders.timeline() //
						.value(Builders.timelineValue("alpha") //
								.typeConverter(Converters.floatValue()) //
								.keyFrame(0, 500f) //
								.keyFrame(500, 12500f) //
								.keyFrame(1000, 500f) //
						) //
						.value(Builders.timelineValue("gamma") //
								.keyFrame(0, 3f) //
								.keyFrame(500, 10f) //
								.keyFrame(1000, 50f) //
						)) //
				.speed(2f) //
				.delay(200) //
				.started(true) //
				.build();
		assertThat(timelineAnimation.getDelay(), IsEqual.equalTo(200f));
		System.out.println(timelineAnimation.getTimeline());
	}

}
