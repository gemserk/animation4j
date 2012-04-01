package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.animation4j.FloatValue;
import com.gemserk.animation4j.FloatValueConverter;

public class TimelineValueTest {

	FloatValueConverter floatValueConverter = new FloatValueConverter();

	@Test
	public void testTimeLineValues() {
		FloatValue floatValue = new FloatValue(0f);

		TimelineValueImpl<FloatValue> timelineValue = new TimelineValueImpl<FloatValue>(floatValue, floatValueConverter);

		timelineValue.addKeyFrame(new KeyFrame(0f, new float[] { 10f }));
		timelineValue.addKeyFrame(new KeyFrame(100, new float[] { 20f }));
		timelineValue.addKeyFrame(new KeyFrame(200, new float[] { 30f }));

		timelineValue.setTime(-50);
		assertThat(floatValue.value, IsEqual.equalTo(10f));

		timelineValue.setTime(0);
		assertThat(floatValue.value, IsEqual.equalTo(10f));

		timelineValue.setTime(50);
		assertThat(floatValue.value, IsEqual.equalTo(15f));

		timelineValue.setTime(100);
		assertThat(floatValue.value, IsEqual.equalTo(20f));

		timelineValue.setTime(200);
		assertThat(floatValue.value, IsEqual.equalTo(30f));
	}

	@Test
	public void shouldReturnInterpolatedValueForFirstFrameKey() {
		FloatValue floatValue = new FloatValue(0f);

		TimelineValueImpl<FloatValue> timelineValue = new TimelineValueImpl<FloatValue>(floatValue, floatValueConverter);

		timelineValue.addKeyFrame(new KeyFrame(0f, new float[] { 10f }));
		timelineValue.addKeyFrame(new KeyFrame(100, new float[] { 20f }));
		timelineValue.addKeyFrame(new KeyFrame(200, new float[] { 30f }));

		timelineValue.setTime(-1f);
		assertThat(floatValue.value, IsEqual.equalTo(10f));
	}

	@Test
	public void shouldReturnInterpolatedValueForLastFrameKey() {
		FloatValue floatValue = new FloatValue(0f);

		TimelineValueImpl<FloatValue> timelineValue = new TimelineValueImpl<FloatValue>(floatValue, floatValueConverter);

		timelineValue.addKeyFrame(new KeyFrame(0f, new float[] { 10f }));
		timelineValue.addKeyFrame(new KeyFrame(100, new float[] { 20f }));
		timelineValue.addKeyFrame(new KeyFrame(200, new float[] { 30f }));

		timelineValue.setTime(201f);
		assertThat(floatValue.value, IsEqual.equalTo(30f));
	}

	@Test(expected = RuntimeException.class)
	public void shouldReturnNullWhenNoKeyFrames() {
		TimelineValueImpl<FloatValue> timelineValue = new TimelineValueImpl<FloatValue>(new FloatValue(0f), floatValueConverter);
		timelineValue.setTime(0f);
	}

	@Test
	public void shouldReturnUniqueValueWhenOnlyOneValue() {
		FloatValue floatValue = new FloatValue(0f);

		TimelineValueImpl<FloatValue> timelineValue = new TimelineValueImpl<FloatValue>(floatValue, floatValueConverter);

		timelineValue.addKeyFrame(new KeyFrame(0f, new float[] { 10f }));

		timelineValue.setTime(-1f);
		assertThat(floatValue.value, IsEqual.equalTo(10f));

		timelineValue.setTime(0f);
		assertThat(floatValue.value, IsEqual.equalTo(10f));

		timelineValue.setTime(1f);
		assertThat(floatValue.value, IsEqual.equalTo(10f));
	}

	@Test
	public void testGetFrame() {
		TimelineValueFloatArrayImpl timelineValue = new TimelineValueFloatArrayImpl(new float[1]);

		KeyFrame firstKeyFrame = new KeyFrame(0f, new float[] { 10f });
		KeyFrame secondKeyFrame = new KeyFrame(100, new float[] { 20f });
		KeyFrame thirdKeyFrame = new KeyFrame(200, new float[] { 30f });

		timelineValue.addKeyFrame(firstKeyFrame);
		timelineValue.addKeyFrame(secondKeyFrame);
		timelineValue.addKeyFrame(thirdKeyFrame);

		assertThat(timelineValue.getKeyFrame(-100f), IsSame.sameInstance(firstKeyFrame));
		assertThat(timelineValue.getKeyFrame(0f), IsSame.sameInstance(secondKeyFrame));
		assertThat(timelineValue.getKeyFrame(100f), IsSame.sameInstance(thirdKeyFrame));
		assertThat(timelineValue.getKeyFrame(201f), IsSame.sameInstance(thirdKeyFrame));
	}

	@Test
	public void testGetPreviousFrame() {
		TimelineValueFloatArrayImpl timelineValue = new TimelineValueFloatArrayImpl(new float[1]);

		KeyFrame firstKeyFrame = new KeyFrame(0f, new float[] { 10f });
		KeyFrame secondKeyFrame = new KeyFrame(100, new float[] { 20f });
		KeyFrame thirdKeyFrame = new KeyFrame(200, new float[] { 30f });

		timelineValue.addKeyFrame(firstKeyFrame);
		timelineValue.addKeyFrame(secondKeyFrame);
		timelineValue.addKeyFrame(thirdKeyFrame);

		assertThat(timelineValue.getPreviousKeyFrame(firstKeyFrame), IsNull.nullValue());
		assertThat(timelineValue.getPreviousKeyFrame(secondKeyFrame), IsSame.sameInstance(firstKeyFrame));
		assertThat(timelineValue.getPreviousKeyFrame(thirdKeyFrame), IsSame.sameInstance(secondKeyFrame));
	}
	
	@Test
	public void testChangeBindedObjectOnTheRun() {
		FloatValue value1 = new FloatValue(10f);
		FloatValue value2 = new FloatValue(10f);
		
		TimelineValueImpl<FloatValue> timelineValue = new TimelineValueImpl<FloatValue>(value1, floatValueConverter);

		KeyFrame firstKeyFrame = new KeyFrame(0f, new float[] { 10f });
		KeyFrame secondKeyFrame = new KeyFrame(1f, new float[] { 20f });
		KeyFrame thirdKeyFrame = new KeyFrame(2f, new float[] { 30f });

		timelineValue.addKeyFrame(firstKeyFrame);
		timelineValue.addKeyFrame(secondKeyFrame);
		timelineValue.addKeyFrame(thirdKeyFrame);
		
		timelineValue.setTime(0.5f);
		
		assertThat(value1.value, IsEqual.equalTo(15f));
		assertThat(value2.value, IsEqual.equalTo(10f));
		
		timelineValue.setObject(value2);
		timelineValue.setTime(0.5f);

		assertThat(value2.value, IsEqual.equalTo(15f));
	}

}
