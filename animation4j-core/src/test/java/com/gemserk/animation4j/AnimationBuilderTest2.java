package com.gemserk.animation4j;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.timeline.KeyFrame;
import com.gemserk.animation4j.timeline.Timeline;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineValue;

public class AnimationBuilderTest2 {

	public static class TimelineValueBuilder {

		private TypeConverter typeConverter;
		private String name;
		private float duration;
		private ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

		public TimelineValueBuilder() {
			reset();
		}

		private void reset() {
			keyFrames = new ArrayList<KeyFrame>();
			typeConverter = null;
		}

		public float getDuration() {
			return duration;
		}

		public TimelineValueBuilder name(String name) {
			this.name = name;
			return this;
		}

		public TimelineValueBuilder typeConverter(TypeConverter typeConverter) {
			this.typeConverter = typeConverter;
			return this;
		}

		public String getName() {
			return name;
		}

		/**
		 * Defines a new key frame in the time line value.
		 * 
		 * @param time
		 *            The time when the key frame starts (in milliseconds).
		 * @param value
		 *            The value the variable should have in the key frame.
		 */
		public <T> TimelineValueBuilder keyFrame(float time, T value) {
			if (typeConverter == null)
				typeConverter = (TypeConverter) Converters.converter(value.getClass());

			// TODO: do not allow time less than last time to avoid defining key frames between other key frames.

			KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null));
			this.keyFrames.add(keyFrame);

			duration = Math.max(duration, time);
			return this;
		}

		/**
		 * Defines a new key frame in the time line value.
		 * 
		 * @param time
		 *            The time when the key frame starts (in milliseconds).
		 * @param value
		 *            The value the variable should have in the key frame.
		 * @param functions
		 *            The interpolation functions to be used when interpolating this keyframe.
		 */
		public <T> TimelineValueBuilder keyFrame(float time, T value, InterpolationFunction... functions) {
			if (typeConverter == null)
				typeConverter = (TypeConverter) Converters.converter(value.getClass());

			KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null), functions);
			this.keyFrames.add(keyFrame);

			duration = Math.max(duration, time);

			return this;
		}

		public TimelineValue build() {
			TimelineValue timelineValue = new TimelineValue(name, typeConverter);
			for (int i = 0; i < keyFrames.size(); i++)
				timelineValue.addKeyFrame(keyFrames.get(i));
			reset();
			return timelineValue;
		}

	}

	public static class TimelineBuilder {

		private HashMap<String, TimelineValue> values;
		private float duration;

		public TimelineBuilder() {
			reset();
		}

		private void reset() {
			values = new HashMap<String, TimelineValue>();
			duration = 0f;
		}

		public TimelineBuilder value(TimelineValueBuilder timelineValueBuilder) {
			duration = Math.max(duration, timelineValueBuilder.getDuration());
			values.put(timelineValueBuilder.getName(), timelineValueBuilder.build());
			return this;
		}

		public Timeline build() {
			Timeline timeline = new Timeline(values);
			reset();
			return timeline;
		}

	}

	public static class TimelineAnimationBuilder {

		private Timeline timeline;
		private float delay;
		private float speed;
		private boolean started;

		public TimelineAnimationBuilder setTimeline(Timeline timeline) {
			this.timeline = timeline;
			return this;
		}

		public TimelineAnimationBuilder delay(float time) {
			this.delay = time;
			return this;
		}

		public TimelineAnimationBuilder started(boolean started) {
			this.started = started;
			return this;
		}

		public TimelineAnimationBuilder speed(float speed) {
			this.speed = speed;
			return this;
		}

		public TimelineAnimationBuilder() {
			reset();
		}

		private void reset() {
			delay = 0f;
			speed = 1f;
			started = false;
		}

		public TimelineAnimation build() {
			TimelineAnimation timelineAnimation = new TimelineAnimation(timeline, started);
			timelineAnimation.setDelay(delay);
			timelineAnimation.setSpeed(speed);
			reset();
			return timelineAnimation;
		}

	}

	public static class Builders {

		private static TimelineBuilder timelineBuilder = new TimelineBuilder();
		private static TimelineAnimationBuilder timelineAnimationBuilder = new TimelineAnimationBuilder();
		private static TimelineValueBuilder timelineValueBuilder = new TimelineValueBuilder();

		public static TimelineBuilder timeline() {
			return timelineBuilder;
		}

		public static TimelineAnimationBuilder animation(TimelineBuilder timelineBuilder) {
			return timelineAnimationBuilder.setTimeline(timelineBuilder.build());
		}

		public static TimelineValueBuilder timelineValue(String name) {
			return timelineValueBuilder.name(name);
		}
		
	}

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
