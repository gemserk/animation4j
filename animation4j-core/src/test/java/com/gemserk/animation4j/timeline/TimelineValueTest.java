package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.animation4j.interpolator.Interpolator;


public class TimelineValueTest {
	
	@Test
	public void testTimeLineValues() {
		Interpolator<Float> interpolator = LinearInterpolatorFactory.linearInterpolatorFloat();
		TimelineValue<Float> timelineValue = new TimelineValue<Float>();
		
		timelineValue.addKeyFrame(0, 10f, interpolator);
		timelineValue.addKeyFrame(100, 20f, interpolator);
		timelineValue.addKeyFrame(200, 30f, interpolator);
		
//		timelineValue.setInterpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
		
		assertEquals(10f, timelineValue.getValue(-50), 0.001f);
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(15f, timelineValue.getValue(50), 0.001f);
		assertEquals(20f, timelineValue.getValue(100), 0.001f);
		assertEquals(30f, timelineValue.getValue(200), 0.001f);
		assertEquals(30f, timelineValue.getValue(300), 0.001f);
	}
	
	@Test
	public void testTimelineValuesWithOffset() {
		Interpolator<Float> interpolator = LinearInterpolatorFactory.linearInterpolatorFloat();
		
		TimelineValue<Float> timelineValue = new TimelineValue<Float>();
		timelineValue.addKeyFrame(100, 10f, interpolator);
		timelineValue.addKeyFrame(200, 20f, interpolator);
		
//		timelineValue.setInterpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
		
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(10f, timelineValue.getValue(50), 0.001f);
		assertEquals(10f, timelineValue.getValue(100), 0.001f);
	}

}
