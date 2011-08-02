package com.gemserk.animation4j.timeline;

import java.util.ArrayList;
import java.util.HashMap;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

public class Builders {

	public static class TimelineValueBuilder {

		private TypeConverter typeConverter;
		private String name;
		private float duration;
		private ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

		private TimelineValueBuilder() {
			reset();
		}

		private void reset() {
			keyFrames = new ArrayList<KeyFrame>();
			typeConverter = null;
		}

		public float getDuration() {
			return duration;
		}

		public Builders.TimelineValueBuilder name(String name) {
			this.name = name;
			return this;
		}

		public Builders.TimelineValueBuilder typeConverter(TypeConverter typeConverter) {
			this.typeConverter = typeConverter;
			return this;
		}

		private String getName() {
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
		public <T> Builders.TimelineValueBuilder keyFrame(float time, T value) {
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
		public <T> Builders.TimelineValueBuilder keyFrame(float time, T value, InterpolationFunction... functions) {
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

		private TimelineBuilder() {
			reset();
		}
		
		public float getDuration() {
			return duration;
		}

		private void reset() {
			values = new HashMap<String, TimelineValue>();
			duration = 0f;
		}

		/**
		 * Declares a new TimelineValue using a TimelineValueBuilder.
		 */
		public Builders.TimelineBuilder value(Builders.TimelineValueBuilder timelineValueBuilder) {
			duration = Math.max(duration, timelineValueBuilder.getDuration());
			values.put(timelineValueBuilder.getName(), timelineValueBuilder.build());
			return this;
		}

		/**
		 * Returns a new Timeline using the declaration values.
		 */
		public Timeline build() {
			Timeline timeline = new Timeline(values);
			reset();
			return timeline;
		}

	}

	public static class TimelineAnimationBuilder {

		private TimelineBuilder timelineBuilder;
		private float delay;
		private float speed;
		private boolean started;

		public Builders.TimelineAnimationBuilder setTimelineBuilder(TimelineBuilder timelineBuilder) {
			this.timelineBuilder = timelineBuilder;
			return this;
		}

		public Builders.TimelineAnimationBuilder delay(float time) {
			this.delay = time;
			return this;
		}

		public Builders.TimelineAnimationBuilder started(boolean started) {
			this.started = started;
			return this;
		}

		public Builders.TimelineAnimationBuilder speed(float speed) {
			this.speed = speed;
			return this;
		}

		private TimelineAnimationBuilder() {
			reset();
		}

		private void reset() {
			delay = 0f;
			speed = 1f;
			started = false;
		}

		public TimelineAnimation build() {
			float duration = timelineBuilder.getDuration();
			
			TimelineAnimation timelineAnimation = new TimelineAnimation(timelineBuilder.build(), started);
			timelineAnimation.setDuration(duration);
			timelineAnimation.setDelay(delay);
			timelineAnimation.setSpeed(speed);
			
			reset();
			return timelineAnimation;
		}

	}

	private static Builders.TimelineBuilder timelineBuilder = new TimelineBuilder();
	private static Builders.TimelineAnimationBuilder timelineAnimationBuilder = new TimelineAnimationBuilder();
	private static Builders.TimelineValueBuilder timelineValueBuilder = new TimelineValueBuilder();

	public static Builders.TimelineBuilder timeline() {
		return timelineBuilder;
	}

	public static Builders.TimelineAnimationBuilder animation(Builders.TimelineBuilder timelineBuilder) {
		return timelineAnimationBuilder.setTimelineBuilder(timelineBuilder);
	}

	public static Builders.TimelineValueBuilder timelineValue(String name) {
		return timelineValueBuilder.name(name);
	}

}