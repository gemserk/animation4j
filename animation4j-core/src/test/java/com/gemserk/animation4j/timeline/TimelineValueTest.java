package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.animation4j.timeline.LinearInterpolatorFactory;
import com.gemserk.animation4j.timeline.TimelineValue;


public class TimelineValueTest {
	
	@Test
	public void testTimeLineValues() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>();
		timelineValue.addKeyFrame(0, 10f);
		timelineValue.addKeyFrame(100, 20f);
		timelineValue.addKeyFrame(200, 30f);
		
		timelineValue.setInterpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
		
		assertEquals(10f, timelineValue.getValue(-50), 0.001f);
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(15f, timelineValue.getValue(50), 0.001f);
		assertEquals(20f, timelineValue.getValue(100), 0.001f);
		assertEquals(30f, timelineValue.getValue(200), 0.001f);
		assertEquals(30f, timelineValue.getValue(300), 0.001f);
	}
	
	@Test
	public void testTimelineValuesWithOffset() {
		TimelineValue<Float> timelineValue = new TimelineValue<Float>();
		timelineValue.addKeyFrame(100, 10f);
		timelineValue.addKeyFrame(200, 20f);
		
		timelineValue.setInterpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
		
		assertEquals(10f, timelineValue.getValue(0), 0.001f);
		assertEquals(10f, timelineValue.getValue(50), 0.001f);
		assertEquals(10f, timelineValue.getValue(100), 0.001f);
	}

}
