package com.gemserk.commons.animation.timeline;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TimelineAnimationTest {
	
	// TEST: timeline animation with delay must not be started until first keyframe
	
	@Test
	public void shouldNotBeStartedBeforeDelay() {
		
		TimelineAnimation animation = new TimelineAnimation(new TimelineBuilder() {
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
		}.build());
		
		animation.play();
		
		assertFalse(animation.isStarted());
		animation.update(100);
		assertTrue(animation.isStarted());
		animation.update(100);
		assertTrue(animation.isFinished());
	}
}
