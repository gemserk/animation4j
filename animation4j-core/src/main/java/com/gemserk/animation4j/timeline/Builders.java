package com.gemserk.animation4j.timeline;

import java.util.ArrayList;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

public class Builders {

	public static class TimelineValueBuilder<T> {

		private TypeConverter<T> typeConverter;
		private float duration;
		private ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		private T mutableObject;

		private TimelineValueBuilder() {
			reset();
		}

		private void reset() {
			keyFrames = new ArrayList<KeyFrame>();
			typeConverter = null;
			duration = 0f;
		}

		public float getDuration() {
			return duration;
		}
		
		public Builders.TimelineValueBuilder<T> mutableObject(T mutableObject) {
			this.mutableObject = mutableObject;
			return this;
		}

		@Deprecated
		public Builders.TimelineValueBuilder<T> name(String name) {
			return this;
		}

		public Builders.TimelineValueBuilder<T> typeConverter(TypeConverter<T> typeConverter) {
			this.typeConverter = typeConverter;
			return this;
		}

		// /**
		// * Defines a new key frame in the time line value.
		// *
		// * @param time
		// * The time when the key frame starts (in milliseconds).
		// * @param value
		// * The value the variable should have in the key frame.
		// */
		// public <T> Builders.TimelineValueBuilder keyFrame(float time, T value) {
		// if (typeConverter == null)
		// typeConverter = (TypeConverter) Converters.converter(value.getClass());
		//
		// // TODO: do not allow time less than last time to avoid defining key frames between other key frames.
		//
		// float timeInSeconds = time * 0.001f;
		//
		// KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null));
		// this.keyFrames.add(keyFrame);
		//
		// duration = Math.max(duration, timeInSeconds);
		// return this;
		// }

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
		public Builders.TimelineValueBuilder<T> keyFrame(float time, T value, InterpolationFunction... functions) {
			if (typeConverter == null)
				typeConverter = (TypeConverter) Converters.converter(value.getClass());

			KeyFrame keyFrame = new KeyFrame(time, typeConverter.copyFromObject(value, null), functions);
			this.keyFrames.add(keyFrame);

			duration = Math.max(duration, time);

			return this;
		}

		public Builders.TimelineValueBuilder<T> keyFrame(int time, T value, InterpolationFunction... functions) {
			return keyFrame((float) time * 0.001f, value, functions);
		}

		public TimelineValue<T> build() {
			TimelineValue<T> timelineValue = new TimelineValue<T>(mutableObject, typeConverter);
			for (int i = 0; i < keyFrames.size(); i++)
				timelineValue.addKeyFrame(keyFrames.get(i));
			reset();
			return timelineValue;
		}

	}

	public static class TimelineBuilder {

//		private HashMap<String, TimelineValue> values;
		private ArrayList<TimelineValue> values;
		private float duration;

		private TimelineBuilder() {
			reset();
		}

		public float getDuration() {
			return duration;
		}

		private void reset() {
//			values = new HashMap<String, TimelineValue>();
			values = new ArrayList<TimelineValue>();
			duration = 0f;
		}

		/**
		 * Declares a new TimelineValue using a TimelineValueBuilder.
		 */
		public Builders.TimelineBuilder value(Builders.TimelineValueBuilder timelineValueBuilder) {
			duration = Math.max(duration, timelineValueBuilder.getDuration());
//			values.put(timelineValueBuilder.getName(), timelineValueBuilder.build());
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

		public TimelineAnimationBuilder setTimelineBuilder(TimelineBuilder timelineBuilder) {
			this.timelineBuilder = timelineBuilder;
			return this;
		}

		/**
		 * Sets the delay of the TimelineAnimation.
		 * 
		 * @param time
		 *            The time in Seconds of the delay.
		 * @return
		 */
		public TimelineAnimationBuilder delay(float time) {
			this.delay = time;
			return this;
		}

		/**
		 * Sets the delay of the TimelineAnimation.
		 * 
		 * @param time
		 *            The Time in ms of the delay.
		 */
		public TimelineAnimationBuilder delay(int time) {
			this.delay = (float) time * 0.001f;
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

			TimelineAnimation timelineAnimation = new TimelineAnimation(timelineBuilder.build(), duration, started);
			// timelineAnimation.setDuration(duration);
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

	@Deprecated
	public static Builders.TimelineValueBuilder timelineValue(String name) {
		return timelineValueBuilder.name(name);
	}
	
	public static <T> Builders.TimelineValueBuilder<T> timelineValue(T mutableObject) {
		return timelineValueBuilder.mutableObject(mutableObject);
	}

}