package com.gemserk.animation4j.animations.events;

import java.util.ArrayList;

import com.gemserk.animation4j.animations.Animation;
import com.gemserk.animation4j.utils.Pool;
import com.gemserk.animation4j.utils.Pool.PoolObjectFactory;

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
	public class Action {

		private final AnimationHandlerManager animationHandlerManager;
		private final AnimationEventHandler animationEventHandler;

		Action(AnimationHandlerManager animationHandlerManager, AnimationEventHandler animationEventHandler) {
			this.animationHandlerManager = animationHandlerManager;
			this.animationEventHandler = animationEventHandler;
		}

		public Action handleChangesOf(Animation animation) {
			AnimationMonitor animationMonitor = animationMonitorsPool.newObject();
			animationMonitor.setAnimationEventHandler(animationEventHandler);
			animationMonitor.setAnimation(animation);
			animationHandlerManager.handleAnimationChanges(animationMonitor);
			return this;
		}

	}

	Pool<AnimationMonitor> animationMonitorsPool = new Pool<AnimationMonitor>(new PoolObjectFactory<AnimationMonitor>() {
		@Override
		public AnimationMonitor createObject() {
			return new AnimationMonitor();
		}
	}, 64);

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
	
	public void handleAnimationChanges(Animation animation, AnimationEventHandler animationEventHandler) {
		AnimationMonitor animationMonitor = animationMonitorsPool.newObject();
		animationMonitor.setAnimationEventHandler(animationEventHandler);
		animationMonitor.setAnimation(animation);
		handleAnimationChanges(animationMonitor);
	}

	public Action with(AnimationEventHandler animationEventHandler) {
		return new Action(this, animationEventHandler);
	}

	public void removeMonitorsFor(Animation animation) {
		for (int i = 0; i < animationMonitors.size(); i++) {
			AnimationMonitor animationMonitor = animationMonitors.get(i);
			if (animationMonitor.getAnimation() == animation)
				animationMonitorsToRemove.add(animationMonitor);
		}
		
		for (int i = 0; i < animationMonitorsToRemove.size(); i++) 
			animationMonitorsPool.free(animationMonitorsToRemove.get(i));
		
		animationMonitors.removeAll(animationMonitorsToRemove);
		animationMonitorsToRemove.clear();
	}
}