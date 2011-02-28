package com.gemserk.commons.animation.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.commons.animation.Animation;
import com.gemserk.commons.animation.MockAnimation;
import com.gemserk.commons.animation.timeline.LinearInterpolatorFactory;
import com.gemserk.commons.animation.timeline.TimelineAnimation;
import com.gemserk.commons.animation.timeline.TimelineBuilder;
import com.gemserk.commons.animation.timeline.TimelineValueBuilder;

public class AnimationMonitorImplTest {

	@Test
	public void onEndShouldNotBeCalledIfAnimationNotFinished() {
		Animation animation = new MockAnimation() {
			{
				setFinished(false);
				setStarted(false);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitorImpl(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges();

		assertEquals(false, animationEventHandler.onFinishCalled);
	}
	
	@Test
	public void onEndShouldBeCalledOnceIfAnimationFinishedAndNeverAgainIfAnimationNotStartedAgain() {
		MockAnimation animation = new MockAnimation() {
			{
				setStarted(false);
				setFinished(false);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitorImpl(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges();

		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);
		
		animation.setStarted(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges();
		assertEquals(true, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);

		animation.setFinished(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges();
		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(true, animationEventHandler.onFinishCalled);

		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges();
		assertEquals(false, animationEventHandler.onFinishCalled);
		assertEquals(false, animationEventHandler.onStartCalled);
		
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges();
		assertEquals(false, animationEventHandler.onFinishCalled);
		assertEquals(false, animationEventHandler.onStartCalled);
	}

	@Test
	public void onFinishShouldBeCalledOnlyOnceIfAnimationFinished() {
		
		MockAnimation animation = new MockAnimation() {
			{
				setStarted(true);
				setFinished(false);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitorImpl(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges();
		
		assertEquals(false, animationEventHandler.onFinishCalled);
		
		animation.setFinished(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges();

		assertEquals(true, animationEventHandler.onFinishCalled);
		
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges();

		assertEquals(false, animationEventHandler.onFinishCalled);
	}

	@Test
	public void onStartShouldBeCalledIfAnimationStartedOnlyOnce() {
		MockAnimation animation = new MockAnimation() {
			{
				setStarted(true);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();
		
		AnimationMonitor animationMonitor = new AnimationMonitorImpl(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges();

		assertEquals(true, animationEventHandler.onStartCalled);
		animationEventHandler.onStartCalled = false;

		animationMonitor.checkAnimationChanges();

		assertEquals(false, animationEventHandler.onStartCalled);
	}

	@Test
	public void onStartShouldBeCalledTwiceIfAnimationReseted() {
		MockAnimation animation = new MockAnimation() {
			{
				setStarted(true);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitorImpl(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges();

		assertEquals(true, animationEventHandler.onStartCalled);
		animationEventHandler.onStartCalled = false;

		animationMonitor.checkAnimationChanges();

		assertEquals(false, animationEventHandler.onStartCalled);

		animation.setStarted(false);
		animationMonitor.checkAnimationChanges();
		animation.setStarted(true);
		animationEventHandler.onStartCalled = false;
		animationMonitor.checkAnimationChanges();
		assertEquals(true, animationEventHandler.onStartCalled);
	}

	@Test
	public void testAnimationHandlerManagerWithTimelineAnimation() {

		Animation animation = new TimelineAnimation(new TimelineBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						interpolator(LinearInterpolatorFactory.linearInterpolatorFloat());
						keyFrame(0, 100f);
						keyFrame(100, 200f);
					}
				});
			}
		}.build());

		AnimationMonitor animationMonitor = new AnimationMonitorImpl(animation);
		animationMonitor.addAnimationHandler(new AnimationEventHandler() {
			@Override
			public void onAnimationStarted(Animation animation) {
				System.out.println("animation playing!!");
			}

			@Override
			public void onAnimationFinished(Animation animation) {
				System.out.println("animation stopped!!");
			}
		});
		animationMonitor.checkAnimationChanges();
		animation.play();
		animationMonitor.checkAnimationChanges();

		animation.update(500);
		animationMonitor.checkAnimationChanges();
		
		animation.update(500);
		animationMonitor.checkAnimationChanges();
		
		animation.update(500);
		animationMonitor.checkAnimationChanges();
		
		animation.update(500);
		animationMonitor.checkAnimationChanges();
	}
	
}
