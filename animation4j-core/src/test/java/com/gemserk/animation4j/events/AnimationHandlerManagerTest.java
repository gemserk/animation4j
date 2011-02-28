package com.gemserk.animation4j.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.MockAnimation;
import com.gemserk.animation4j.events.AnimationHandlerManager;
import com.gemserk.animation4j.events.AnimationMonitorImpl;

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
