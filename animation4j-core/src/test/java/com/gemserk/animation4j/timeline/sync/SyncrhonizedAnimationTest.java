package com.gemserk.animation4j.timeline.sync;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.timeline.Timeline;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineIterator;

@RunWith(JMock.class)
public class SyncrhonizedAnimationTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void test() {

		final TimelineAnimation animation = mockery.mock(TimelineAnimation.class);
		final TimelineSynchronizer timelineSynchronizer = mockery.mock(TimelineSynchronizer.class);
		final Timeline timeline = mockery.mock(Timeline.class);
		final TimelineIterator timelineIterator = mockery.mock(TimelineIterator.class);

		mockery.checking(new Expectations() {
			{
				oneOf(animation).getTimeline();
				will(returnValue(timeline));

				oneOf(timeline).getIterator();
				will(returnValue(timelineIterator));

				oneOf(animation).update(10f);
				oneOf(animation).getCurrentTime();
				will(returnValue(200f));
				
				oneOf(timelineIterator).restart();

				oneOf(timelineSynchronizer).syncrhonize(timelineIterator, 200f);
			}
		});

		SynchronizedAnimation synchronizedAnimation = new SynchronizedAnimation(animation, timelineSynchronizer);

		synchronizedAnimation.update(10f);

	}
	
	@Test
	public void shouldAskForIteratorOnConstructor() {

		final TimelineAnimation animation = mockery.mock(TimelineAnimation.class);
		final TimelineSynchronizer timelineSynchronizer = mockery.mock(TimelineSynchronizer.class);
		final Timeline timeline = mockery.mock(Timeline.class);
		final TimelineIterator timelineIterator = mockery.mock(TimelineIterator.class);

		mockery.checking(new Expectations() {
			{
				oneOf(animation).getTimeline();
				will(returnValue(timeline));
				oneOf(timeline).getIterator();
				will(returnValue(timelineIterator));
			}
		});

		new SynchronizedAnimation(animation, timelineSynchronizer);

	}

}
