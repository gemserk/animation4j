package com.gemserk.animation4j.animations.events;

import java.util.ArrayList;

import com.gemserk.animation4j.animations.Animation;

/**
 * Manages all animation event handlers, checking whenever an animation state changes so related animation event handlers be called.
 * 
 * @author acoppes
 * 
 */
public class AnimationHandlerManager {

	/**
	 * Represents the action of register a new event handler in the manager.
	 * 
	 * @author acoppes
	 */
	public static class Action {

		private final AnimationHandlerManager animationHandlerManager;

		private final AnimationEventHandler animationEventHandler;

		Action(AnimationHandlerManager animationHandlerManager, AnimationEventHandler animationEventHandler) {
			this.animationHandlerManager = animationHandlerManager;
			this.animationEventHandler = animationEventHandler;
		}

		public Action handleChangesOf(Animation animation) {
			animationHandlerManager.handleAnimationChanges(new AnimationMonitor(animation, animationEventHandler));
			return this;
		}

	}

	ArrayList<AnimationMonitor> animationMonitors = new ArrayList<AnimationMonitor>();

	/**
	 * Checks all animation monitors to verify animation state changes and call corresponding animation event handlers.
	 */
	public void checkAnimationChanges() {
		for (int i = 0; i < animationMonitors.size(); i++)
			animationMonitors.get(i).checkAnimationChanges();
		animationMonitors.removeAll(animationMonitorsToRemove);
		animationMonitorsToRemove.clear();
	}

	ArrayList<AnimationMonitor> animationMonitorsToRemove = new ArrayList<AnimationMonitor>();

	public void handleAnimationChanges(final AnimationMonitor animationMonitor) {
		animationMonitors.add(animationMonitor);
	}

	public void remove(AnimationEventHandler animationEventHandler) {
		for (int i = 0; i < animationMonitors.size(); i++) {
			AnimationMonitor animationMonitor = animationMonitors.get(i);
			animationMonitor.removeAnimationHandler(animationEventHandler);
			if (!animationMonitor.hasAnimationHandlers())
				animationMonitorsToRemove.add(animationMonitor);
		}
	}

	public Action with(AnimationEventHandler animationEventHandler) {
		return new Action(this, animationEventHandler);
	}

	public void removeMonitorsFor(Animation animation) {
		for (int i = 0; i < animationMonitors.size(); i++) {
			if (animationMonitors.get(i).getAnimation() == animation)
				animationMonitorsToRemove.add(animationMonitors.get(i));
		}
		animationMonitors.removeAll(animationMonitorsToRemove);
		animationMonitorsToRemove.clear();
	}
}