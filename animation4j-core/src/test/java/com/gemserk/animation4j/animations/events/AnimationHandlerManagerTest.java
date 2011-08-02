package com.gemserk.animation4j.animations.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gemserk.animation4j.animations.Animation;
import com.gemserk.animation4j.animations.MockAnimation;

public class AnimationHandlerManagerTest {

	@Deprecated
	public void shouldRemoveMonitoredAnimationIfFinished() {

		Animation animation = new MockAnimation() {
			{
				setStarted(true);
				setFinished(true);
			}
		};

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);

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

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);

		AnimationHandlerManager animationHandlerManager = new AnimationHandlerManager();
		animationHandlerManager.handleAnimationChanges(animationMonitor);
		
		assertTrue(animationHandlerManager.animationMonitors.contains(animationMonitor));
		
		animationHandlerManager.checkAnimationChanges();
		
		assertTrue(animationHandlerManager.animationMonitors.contains(animationMonitor));
	}
	
	@Test
	public void shouldRemoveAllMonitorsForAnimation() {

		Animation animation = new MockAnimation() {
			{
				setStarted(false);
				setFinished(false);
			}
		};

		AnimationMonitor animationMonitor = new AnimationMonitor(animation);

		AnimationHandlerManager animationHandlerManager = new AnimationHandlerManager();
		animationHandlerManager.handleAnimationChanges(animationMonitor);
		
		assertTrue(animationHandlerManager.animationMonitors.contains(animationMonitor));
		
		animationHandlerManager.removeMonitorsFor(animation);
		
		assertFalse(animationHandlerManager.animationMonitors.contains(animationMonitor));
	}

}
