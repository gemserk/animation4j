package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class TimelineTest {

	@Test
	@SuppressWarnings({"rawtypes", "serial"})
	public void testGetValueForAGivenTime() {
		
		Timeline timeline = new Timeline(100, 0, new HashMap<String, TimelineValue>(){{
			put("myvalue", new TimelineValueBuilder<Float>().keyFrame(0, 100f).keyFrame(100, 200f).build());
		}});
		
		assertEquals(100f, (Float) timeline.getValue(- timeline.getDelay(), "myvalue"), 0.01f);
		assertEquals(200f, (Float) timeline.getValue(100 - timeline.getDelay(), "myvalue"), 0.01f);
	}

	@Test
	@SuppressWarnings({"rawtypes", "serial"})
	public void testGetValueForAGivenTimeWithDelay() {
		
		Timeline timeline = new Timeline(100, 100, new HashMap<String, TimelineValue>(){{
			put("myvalue", new TimelineValueBuilder<Float>().keyFrame(0, 100f).keyFrame(100, 200f).build());
		}});
		
		assertEquals(100f, (Float) timeline.getValue(0 - timeline.getDelay(), "myvalue"), 0.01f);
		assertEquals(100f, (Float) timeline.getValue(100 - timeline.getDelay(), "myvalue"), 0.01f);
		assertEquals(150f, (Float) timeline.getValue(150 - timeline.getDelay(), "myvalue"), 0.01f);
		assertEquals(200f, (Float) timeline.getValue(200 - timeline.getDelay(), "myvalue"), 0.01f);
	}

}
