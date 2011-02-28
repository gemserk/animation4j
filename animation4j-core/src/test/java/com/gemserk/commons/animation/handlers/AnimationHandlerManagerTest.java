package com.gemserk.commons.animation.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gemserk.commons.animation.Animation;
import com.gemserk.commons.animation.MockAnimation;

public class AnimationHandlerManagerTest {

	@Test
	public void shouldRemoveMonitoredAnimationIfFinished() {

		Animation animation = new MockAnimation() {
			{
				setStarted(true);
				setFinished(true);
			}
		};

		AnimationMonitorImpl animationMonitor = new AnimationMonitorImpl(animation);

		AnimationHandlerManager animationHandlerManager = new AnimationHandlerManager();
		animationHandlerManager.handleAnimationChanges(animationMonitor);
		
		assertTrue(animationHandlerManager.animationMonitors.contains(animationMonitor));
		
		animationHandlerManager.checkAnimationChanges();
		
		assertFalse(animationHandlerManager.animationMonitors.contains(animationMonitor));
	}
	
	@Test
	public void shouldNotRemoveMonitoredAnimationIfNotFinished() {

		Animation animation = new MockAnimation() {
			{
				setStarted(false);
				setFinished(false);
			}
		};

		AnimationMonitorImpl animationMonitor = new AnimationMonitorImpl(animation);

		AnimationHandlerManager animationHandlerManager = new AnimationHandlerManager();
		animationHandlerManager.handleAnimationChanges(animationMonitor);
		
		assertTrue(animationHandlerManager.animationMonitors.contains(animationMonitor));
		
		animationHandlerManager.checkAnimationChanges();
		
		assertTrue(animationHandlerManager.animationMonitors.contains(animationMonitor));
	}

}
