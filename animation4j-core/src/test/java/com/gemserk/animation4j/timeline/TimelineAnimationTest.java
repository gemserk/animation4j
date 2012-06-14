package com.gemserk.animation4j.timeline;

import static org.junit.Assert.*;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.FloatValue;
import com.gemserk.animation4j.FloatValueConverter;
import com.gemserk.animation4j.animations.Animation;

public class TimelineAnimationTest {

	public static class IsNear {

		public static Matcher<Float> isNear(float x, float range) {
			return new Near(x, range);
		}

	}

	public static class Near extends BaseMatcher<Float> {

		private final float x;

		private final float range;

		public Near(float x, float range) {
			this.x = x;
			this.range = range;
		}

		@Override
		public boolean matches(Object o) {
			if (!(o instanceof Float))
				return false;
			Float x1 = (Float) o;
			return Math.abs(x1 - x) < range;
		}

		@Override
		public void describeTo(Description description) {

		}
	}

	FloatValueConverter floatValueConverter = new FloatValueConverter();

	@Test
	public void shouldNotBeStartedBeforeDelay() {
		FloatValue floatValue = new FloatValue(0f);

		Animation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(100f)) //
						.keyFrame(0.1f, new FloatValue(200f)) //
				) //
				).started(true) //
				.delay(0.1f) //
				.build();

		animation.resume();

		assertFalse(animation.isStarted());
		animation.update(101);
		assertTrue(animation.isStarted());
		animation.update(100);
		assertTrue(animation.isFinished());
	}

	@Test
	public void shouldIterateIterationCountTimes() {
		FloatValue floatValue = new FloatValue(0f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(100f)) //
						.keyFrame(0.1f, new FloatValue(200f)) //
				) //
				).started(true) //
				.build();

		animation.start(2);

		assertThat(animation.getIteration(), IsEqual.equalTo(1));
		assertThat(floatValue.value, IsEqual.equalTo(100f));

		animation.update(0.09999f);

		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));

		animation.update(0.1f);

		assertThat(animation.getIteration(), IsEqual.equalTo(2));
		assertThat(animation.isFinished(), IsEqual.equalTo(false));

		assertThat(floatValue.value, IsNear.isNear(100f, 0.1f));

		animation.update(100f * 0.001f);
		assertThat(animation.isFinished(), IsEqual.equalTo(true));
		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));
	}

	@Test
	public void shouldIterateAnIndefiniteNumberOfTimes() {

		FloatValue floatValue = new FloatValue(0f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(100f)) //
						.keyFrame(0.1f, new FloatValue(200f)) //
				) //
				).started(true) //
				.build();

		animation.start(0);

		assertThat(floatValue.value, IsEqual.equalTo(100f));
		animation.update(99.99f * 0.001f);
		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));
		animation.update(0.01f * 0.001f);
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		assertThat(floatValue.value, IsNear.isNear(100f, 0.1f));
		animation.update(99.99f * 0.001f);
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));

		animation.update(9999999f * 0.001f);
		assertThat(animation.isFinished(), IsEqual.equalTo(false));

	}

	@Test
	public void shouldBeStartedBetweenIterationsWithDelays() {

		FloatValue floatValue = new FloatValue(0f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(100f)) //
						.keyFrame(0.1f, new FloatValue(200f)) //
				) //
				).started(true) //
				.delay(0.1f) //
				.build();

		animation.start(2);
		assertThat(animation.isStarted(), IsEqual.equalTo(false));
		animation.update(101);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
		animation.update(101);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldPlayInReversIfAlternateDirection() {

		FloatValue floatValue = new FloatValue(0f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(200f)) //
						.keyFrame(0.1f, new FloatValue(900f)) //
						.keyFrame(0.15f, new FloatValue(1900f)) //
						.keyFrame(0.2f, new FloatValue(2000f)) //
				)) //
				.build();

		animation.setAlternateDirection(true);
		animation.start(2);

		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));

		animation.update(0.1f);
		assertThat(floatValue.value, IsNear.isNear(900f, 0.1f));

		animation.update(0.1f);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
		assertThat(floatValue.value, IsNear.isNear(2000f, 0.1f));

		animation.update(0.05f);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
		assertThat(floatValue.value, IsNear.isNear(1900f, 0.1f));
		assertThat(animation.isFinished(), IsEqual.equalTo(false));

		animation.update(0.151f);
		assertThat(animation.isFinished(), IsEqual.equalTo(true));
		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));

	}

	// test: delay should be used only to start the animation, not in each iteration.

	@Test
	public void shouldProcessDelayOnlyWhenAnimationIsNotStarted() {

		FloatValue floatValue = new FloatValue(0f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(100f)) //
						.keyFrame(0.1f, new FloatValue(200f)) //
				)) //
				.delay(0.1f) //
				.build();

		animation.start(2);
		assertThat(floatValue.value, IsNear.isNear(100f, 0.1f));
		animation.update(100 * 0.001f);
		assertThat(floatValue.value, IsNear.isNear(100f, 0.1f));
		animation.update(99.999f * 0.001f);
		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));

		animation.update(0.001f);
		assertEquals(100f, floatValue.value, 0.01f);
		//assertThat(floatValue.value, IsNear.isNear(100f, 0.1f));

		// the second time it should only move without using the delay.

		animation.update(99.999f * 0.001f);
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		assertThat(floatValue.value, IsNear.isNear(200f, 0.1f));
	}

	// test: same thing with an animation alternating directions

	// test restart is not setting iteration to 0? (fixed without test)

	// test: restart should restart delay?

	@Test
	public void bugWhenRestartAnimationPlayingInReverseItWasStillInReverse() {
		FloatValue floatValue = new FloatValue(0f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(100f)) //
						.keyFrame(0.1f, new FloatValue(200f)) //
				)) //
				.build();

		animation.start(2, true);
		assertThat(animation.getIteration(), IsEqual.equalTo(1));
		assertThat(animation.getPlayingDirection(), IsEqual.equalTo(Animation.PlayingDirection.Normal));
		animation.update(101 * 0.001f);
		assertThat(animation.getIteration(), IsEqual.equalTo(2));
		assertThat(animation.getPlayingDirection(), IsEqual.equalTo(Animation.PlayingDirection.Reverse));
		animation.restart();
		assertThat(animation.getIteration(), IsEqual.equalTo(1));
		assertThat(animation.getPlayingDirection(), IsEqual.equalTo(Animation.PlayingDirection.Normal));
		animation.update(20 * 0.001f);
		assertThat(animation.getIteration(), IsEqual.equalTo(1));
		assertThat(animation.getPlayingDirection(), IsEqual.equalTo(Animation.PlayingDirection.Normal));

	}

	@Test
	public void shouldBeFinishedAfterTotalTimeSpent() {
		float totalTime = 20f;
		
		FloatValue floatValue = new FloatValue(20f);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(0f)) //
						.keyFrame(0.2f, new FloatValue(1f)) //
						.keyFrame(0.8f, new FloatValue(1f)) //
						.keyFrame(1f, new FloatValue(0f))) //
				) //
				.speed(1f / totalTime) //
				.build();
		animation.start(1);

		animation.update(totalTime * 0.5f);

		assertFalse(animation.isFinished());

		animation.update(totalTime * 0.5f);

		assertTrue(animation.isFinished());
	}

	@Test
	public void bugTimelineAnimationShouldHaveTimelineDuration() {
		FloatValue floatValue = new FloatValue(20f);
		
		Timeline timeline = Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new FloatValue(0f)) //
						.keyFrame(0.2f, new FloatValue(1f))).build();

		TimelineAnimation animation = new TimelineAnimation(timeline, 0.2f);

		assertThat(animation.getDuration(), IsEqual.equalTo(0.2f));
	}
	
	@Test
	public void bugTimelineAnimationShouldModifyToFirstValueWhenDelay() {
		FloatValue floatValue = new FloatValue(20f);
		
		Timeline timeline = Builders.timeline() //
				.value(Builders.timelineValue(floatValue, floatValueConverter) //
						.keyFrame(0f, new float[] { -10f }) //
						.keyFrame(1f, new float[] { 10f })) //
						.build();
		
		TimelineAnimation animation = new TimelineAnimation(timeline, 1f);
		animation.setDelay(1f);
		animation.start();
		
		animation.update(0.9f);

		assertThat(floatValue.value, IsEqual.equalTo(-10f));
	}

}
