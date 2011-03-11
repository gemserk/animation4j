package com.gemserk.animation4j.event;

import java.util.ArrayList;

import com.gemserk.animation4j.Animation;

public class AnimationHandlerManager {
	
	public static class Action {

		private final AnimationHandlerManager animationHandlerManager;
		
		private final AnimationEventHandler animationEventHandler;

		public Action(AnimationHandlerManager animationHandlerManager, AnimationEventHandler animationEventHandler) {
			this.animationHandlerManager = animationHandlerManager;
			this.animationEventHandler = animationEventHandler;
		}
		
		public Action handleChangesOf(Animation animation) {
			animationHandlerManager.handleAnimationChanges(new AnimationMonitorImpl(animation, animationEventHandler));
			return this;
		}
		
	}

	ArrayList<AnimationMonitorImpl> animationMonitors = new ArrayList<AnimationMonitorImpl>();

	public void checkAnimationChanges() {
		for (int i = 0; i < animationMonitors.size(); i++)
			animationMonitors.get(i).checkAnimationChanges(this);
		animationMonitors.removeAll(animationMonitorsToRemove);
		animationMonitorsToRemove.clear();
	}

	ArrayList<AnimationMonitorImpl> animationMonitorsToRemove = new ArrayList<AnimationMonitorImpl>();

	public void handleAnimationChanges(final AnimationMonitorImpl animationMonitor) {
		animationMonitors.add(animationMonitor);
		// Not sure about this yet... 
		animationMonitor.addAnimationHandler(new AnimationEventHandler() {
			@Override
			public void onAnimationFinished(AnimationEvent e) {
				animationMonitorsToRemove.add(animationMonitor);
			}
		});
	}
	
	public void remove(AnimationEventHandler animationEventHandler) {
		throw new RuntimeException("not implemented yet");
	}

	public Action with(AnimationEventHandler animationEventHandler) {
		return new Action(this, animationEventHandler);
	}
}