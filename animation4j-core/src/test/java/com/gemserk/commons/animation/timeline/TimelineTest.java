package com.gemserk.commons.animation.timeline;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimelineTest {

	@Test
	public void testGetValueForAGivenTime() {
		Timeline timeline = new TimelineBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						interpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
						keyFrame(0, 100f);
						keyFrame(100, 200f);
					}
				});
			}
		}.build();

		assertEquals(100f, (Float) timeline.getValue(0, "myvalue"), 0.01f);
		assertEquals(200f, (Float) timeline.getValue(100, "myvalue"), 0.01f);
	}

	@Test
	public void testGetValueForAGivenTimeWithDelay() {
		Timeline timeline = new TimelineBuilder() {
			{
				delay(100);
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						interpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
						keyFrame(0, 100f);
						keyFrame(100, 200f);
					}
				});
			}
		}.build();

		assertEquals(100f, (Float) timeline.getValue(0, "myvalue"), 0.01f);
		assertEquals(100f, (Float) timeline.getValue(100, "myvalue"), 0.01f);
		assertEquals(150f, (Float) timeline.getValue(150, "myvalue"), 0.01f);
		assertEquals(200f, (Float) timeline.getValue(200, "myvalue"), 0.01f);
	}

}
