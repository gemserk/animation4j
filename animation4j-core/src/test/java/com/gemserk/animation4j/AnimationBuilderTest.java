package com.gemserk.animation4j;

import static org.junit.Assert.assertThat;

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
import com.gemserk.animation4j.timeline.TimelineValue;

public class AnimationBuilderTest {

	public static class TimelineBuilder {

		private HashMap<String, TimelineValue> values;

		private class ValueBuilder {

			private final TimelineBuilder timelineBuilder;

			private String name;

			private TimelineValue timelineValue;

			public ValueBuilder(TimelineBuilder timelineBuilder, String name) {
				this.timelineBuilder = timelineBuilder;
				this.name = name;
			}

			public ValueBuilder keyframe(float time, Object value, InterpolationFunction... functions) {
				TypeConverter typeConverter = Converters.converter(value.getClass());

				if (timelineValue == null)
					timelineValue = new TimelineValue(name, typeConverter);

				timelineValue.addKeyFrame(new KeyFrame(time, typeConverter.copyFromObject(value, null), functions));

				return this;
			}

			// keyframe(time, float...values)

			public Timeline build() {
				values.put(this.name, this.timelineValue);
				return timelineBuilder.build();
			}

			public ValueBuilder value(String name) {
				values.put(this.name, this.timelineValue);
				this.name = name;
				this.timelineValue = null;
				return this;
			}

		}

		private TimelineBuilder() {
			values = new HashMap<String, TimelineValue>();
		}

		public ValueBuilder value(String name) {
			return new ValueBuilder(this, name);
		}

		public Timeline build() {
			return new Timeline(new HashMap<String, TimelineValue>(values));
		}

		private static TimelineBuilder timelineBuilder = new TimelineBuilder();

		public static TimelineBuilder timeline() {
			timelineBuilder.values.clear();
			return timelineBuilder;
		}

	}

	@Before
	public void before() {
		Converters.register(Vector2f.class, new Vector2fConverter());
	}

	@After
	public void after() {
		Converters.unregister(Vector2f.class);
	}

	@Test
	public void test() {

		// animationBuilder.animate(v) //
		// .to(v).time(500).functions(InterpolationFunctions.ease()) //
		// .to(v).time(100);

		Timeline timeline = TimelineBuilder.timeline() //
				.value("position") //
				.keyframe(100, new Vector2f(20, 30)) //
				.keyframe(200, new Vector2f(200, 300)) //
				.value("size") //
				.keyframe(0, new Vector2f(10f, 10f)) //
				.keyframe(100, new Vector2f(50f, 50f)) //
				.build();

		Vector2f v = timeline.getValue2(0, "position");
		assertThat(v, IsEqual.equalTo(new Vector2f(20f, 30f)));

		v = timeline.getValue2(100, "position");
		assertThat(v, IsEqual.equalTo(new Vector2f(20f, 30f)));

		v = timeline.getValue2(200, "position");
		assertThat(v, IsEqual.equalTo(new Vector2f(200f, 300f)));

		v = timeline.getValue2(0, "size");
		assertThat(v, IsEqual.equalTo(new Vector2f(10f, 10f)));

		v = timeline.getValue2(100, "size");
		assertThat(v, IsEqual.equalTo(new Vector2f(50f, 50f)));

		timeline = TimelineBuilder.timeline() //
				.value("size") //
				.keyframe(100, new Vector2f(20, 30)) //
				.keyframe(200, new Vector2f(200, 300)) //
				.value("position") //
				.keyframe(0, new Vector2f(10f, 10f)) //
				.keyframe(100, new Vector2f(50f, 50f)) //
				.build();
		
		v = timeline.getValue2(0, "position");
		assertThat(v, IsEqual.equalTo(new Vector2f(10f, 10f)));

		v = timeline.getValue2(100, "position");
		assertThat(v, IsEqual.equalTo(new Vector2f(50f, 50f)));

	}

}
