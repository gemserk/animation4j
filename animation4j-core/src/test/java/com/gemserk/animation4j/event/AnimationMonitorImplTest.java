package com.gemserk.animation4j.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.MockAnimation;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;

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

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

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

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);

		animation.setStarted(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);
		assertEquals(true, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);

		animation.setFinished(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);
		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(true, animationEventHandler.onFinishCalled);

		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);
		assertEquals(false, animationEventHandler.onFinishCalled);
		assertEquals(false, animationEventHandler.onStartCalled);

		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);
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

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onFinishCalled);

		animation.setFinished(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

		assertEquals(true, animationEventHandler.onFinishCalled);

		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

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

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(true, animationEventHandler.onStartCalled);
		animationEventHandler.onStartCalled = false;

		animationMonitor.checkAnimationChanges(null);

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

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(true, animationEventHandler.onStartCalled);
		animationEventHandler.onStartCalled = false;

		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onStartCalled);

		animation.setStarted(false);
		animationMonitor.checkAnimationChanges(null);
		animation.setStarted(true);
		animationEventHandler.onStartCalled = false;
		animationMonitor.checkAnimationChanges(null);
		assertEquals(true, animationEventHandler.onStartCalled);
	}

	@Test
	public void testAnimationHandlerManagerWithTimelineAnimation() {

		Animation animation = new TimelineAnimationBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						keyFrame(0, 100f);
						keyFrame(100, 200f);
					}
				});
			}
		}.build();

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(new AnimationEventHandler() {
			@Override
			public void onAnimationStarted(AnimationEvent e) {
				System.out.println("animation playing!!");
			}

			@Override
			public void onAnimationFinished(AnimationEvent e) {
				System.out.println("animation stopped!!");
			}
		});
		animationMonitor.checkAnimationChanges(null);
		animation.resume();
		animationMonitor.checkAnimationChanges(null);

		animation.update(500);
		animationMonitor.checkAnimationChanges(null);

		animation.update(500);
		animationMonitor.checkAnimationChanges(null);

		animation.update(500);
		animationMonitor.checkAnimationChanges(null);

		animation.update(500);
		animationMonitor.checkAnimationChanges(null);
	}

	@Test
	public void onIterationChangedShouldBeCalledWhenIterationChanged() {

		MockAnimation animation = new MockAnimation() {
			{
				setStarted(true);
				setIteration(1);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onIterationChangedCalled);
		animation.setIteration(2);
		animationMonitor.checkAnimationChanges(null);
		assertEquals(true, animationEventHandler.onIterationChangedCalled);

		animationEventHandler.onIterationChangedCalled = false;

		animationMonitor.checkAnimationChanges(null);
		assertEquals(false, animationEventHandler.onIterationChangedCalled);

	}

	@Test
	public void shouldNotCallOnIterationChangedAfterAnimationFinished() {

		MockAnimation animation = new MockAnimation() {
			{
				setStarted(true);
				setFinished(true);
				setIteration(1);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onIterationChangedCalled);
		animation.setIteration(2);
		animationMonitor.checkAnimationChanges(null);
		assertEquals(false, animationEventHandler.onIterationChangedCalled);

	}
	
	@Test
	public void shouldCallStartAgainIfAnimationRestarted() {

		MockAnimation animation = new MockAnimation() {
			{
				setStarted(false);
				setFinished(false);
				setIteration(1);
			}
		};

		MockAnimationEventHandler animationEventHandler = new MockAnimationEventHandler();

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);
		animationMonitor.addAnimationHandler(animationEventHandler);
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);
		
		animation.setStarted(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

		assertEquals(true, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);

		animation.setFinished(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(true, animationEventHandler.onFinishCalled);
		
		animation.restart();
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);
		
		animation.setStarted(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

		assertEquals(true, animationEventHandler.onStartCalled);
		assertEquals(false, animationEventHandler.onFinishCalled);

		animation.setFinished(true);
		animationEventHandler.reset();
		animationMonitor.checkAnimationChanges(null);

		assertEquals(false, animationEventHandler.onStartCalled);
		assertEquals(true, animationEventHandler.onFinishCalled);


	}
}
