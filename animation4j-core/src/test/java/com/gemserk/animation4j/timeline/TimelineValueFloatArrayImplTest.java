package com.gemserk.animation4j.timeline;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class TimelineValueFloatArrayImplTest {

	@Test
	public void shouldReturnLastKeyFrameValueIfTimeGreaterThanTimeline() {
		float[] x = new float[] {0f, 0f};
		
		TimelineValueFloatArrayImpl timelineValue = new TimelineValueFloatArrayImpl(x);
		timelineValue.addKeyFrame(new KeyFrame(0f, new float[] { 10f, 10f }));
		timelineValue.addKeyFrame(new KeyFrame(1f, new float[] { 20f, 30f }));
		
		timelineValue.setTime(2f);
		
		assertThat(x[0], IsEqual.equalTo(20f));
		assertThat(x[1], IsEqual.equalTo(30f));
	}

}
