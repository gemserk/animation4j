package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;


public class TimelineValueTest {
	
	TypeConverter<Float> floatConverter = Converters.floatValue();
	
	@Test
	public void testTimeLineValues() {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(1);
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		
		timelineValue.addKeyFrame(0, 10f, interpolator);
		timelineValue.addKeyFrame(100, 20f, interpolator);
		timelineValue.addKeyFrame(200, 30f, interpolator);
		
		assertEquals(10f, timelineValue.getValue(-50), 0.001f);
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(15f, timelineValue.getValue(50), 0.001f);
		assertEquals(20f, timelineValue.getValue(100), 0.001f);
		assertEquals(30f, timelineValue.getValue(200), 0.001f);
		assertEquals(30f, timelineValue.getValue(300), 0.001f);
	}
	
	@Test
	public void testTimelineValuesWithOffset() {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(1);
		
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		timelineValue.addKeyFrame(100, 10f, interpolator);
		timelineValue.addKeyFrame(200, 20f, interpolator);
		
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(10f, timelineValue.getValue(50), 0.001f);
		assertEquals(10f, timelineValue.getValue(100), 0.001f);
	}
	
	// test should not use last key frame interpolator ?
	
	@Test
	public void shouldReturnInterpolatedValueForLastFrameKey() {
		Interpolator<float[]> interpolator = new FloatArrayInterpolator(1, InterpolationFunctions.quadratic(0, 1, 0));
		
		TimelineValue<Float> timelineValue = new TimelineValue<Float>(floatConverter);
		timelineValue.addKeyFrame(0, 10f, interpolator);
		timelineValue.addKeyFrame(100, 20f, null);
		
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(15f, timelineValue.getValue(50), 0.001f);
		assertEquals(10f, timelineValue.getValue(101), 0.001f);
	}

}
