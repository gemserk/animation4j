package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;


public class TimelineValueTest {
	
	TypeConverter<Float> floatConverter = Converters.floatValue();
	
	@Test
	public void testTimeLineValues() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		
		timelineValue.addKeyFrame(new KeyFrame(0f, new float[]{10f}));
		timelineValue.addKeyFrame(new KeyFrame(100, new float[]{20f}));
		timelineValue.addKeyFrame(new KeyFrame(200, new float[]{30f}));
		
		assertThat(timelineValue.getValue(-50), IsEqual.equalTo(10f));
		assertThat(timelineValue.getValue(0), IsEqual.equalTo(10f));
		assertThat(timelineValue.getValue(50), IsEqual.equalTo(15f));
		assertThat(timelineValue.getValue(100), IsEqual.equalTo(20f));
		assertThat(timelineValue.getValue(200), IsEqual.equalTo(30f));
		assertThat(timelineValue.getValue(300), IsEqual.equalTo(30f));
	}
	
	@Test
	public void testTimelineValuesWithOffset() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		
		timelineValue.addKeyFrame(new KeyFrame(100f, new float[]{10f}));
		timelineValue.addKeyFrame(new KeyFrame(200f, new float[]{20f}));

		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(10f, timelineValue.getValue(50), 0.001f);
		assertEquals(10f, timelineValue.getValue(100), 0.001f);
	}
	
	// test should not use last key frame interpolator ?
	
	@Test
	public void shouldReturnInterpolatedValueForLastFrameKey() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		
		timelineValue.addKeyFrame(new KeyFrame(0f, new float[]{10f}));
		timelineValue.addKeyFrame(new KeyFrame(100, new float[]{20f}));
		
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(15f, timelineValue.getValue(50), 0.001f);
		assertEquals(20f, timelineValue.getValue(101), 0.001f);
	}
	
	@Test
	public void shouldReturnNullWhenNoKeyFrames() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		assertThat(timelineValue.getValue(0f), IsNull.nullValue());
	}
	
	@Test
	public void shouldReturnUniqueValueWhenOnlyOneValue() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		timelineValue.addKeyFrame(new KeyFrame(0f, new float[]{10f}));
		assertThat(timelineValue.getValue(-1f), IsEqual.equalTo(10f));
		assertThat(timelineValue.getValue(0f), IsEqual.equalTo(10f));
		assertThat(timelineValue.getValue(1f), IsEqual.equalTo(10f));
	}
	
	@Test
	public void testGetFrame() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		
		KeyFrame firstKeyFrame = new KeyFrame(0f, new float[]{10f});
		KeyFrame secondKeyFrame = new KeyFrame(100, new float[]{20f});
		KeyFrame thirdKeyFrame = new KeyFrame(200, new float[]{30f});
		
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
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		
		KeyFrame firstKeyFrame = new KeyFrame(0f, new float[]{10f});
		KeyFrame secondKeyFrame = new KeyFrame(100, new float[]{20f});
		KeyFrame thirdKeyFrame = new KeyFrame(200, new float[]{30f});
		
		timelineValue.addKeyFrame(firstKeyFrame);
		timelineValue.addKeyFrame(secondKeyFrame);
		timelineValue.addKeyFrame(thirdKeyFrame);
		
		assertThat(timelineValue.getPreviousKeyFrame(firstKeyFrame), IsNull.nullValue());
		assertThat(timelineValue.getPreviousKeyFrame(secondKeyFrame), IsSame.sameInstance(firstKeyFrame));
		assertThat(timelineValue.getPreviousKeyFrame(thirdKeyFrame), IsSame.sameInstance(secondKeyFrame));
	}

}
