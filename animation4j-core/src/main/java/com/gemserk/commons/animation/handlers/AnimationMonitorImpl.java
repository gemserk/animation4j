package com.gemserk.commons.animation.handlers;

import java.util.ArrayList;
import java.util.List;

import com.gemserk.commons.animation.Animation;

/**
 * Monitors an Animation and call to an AnimationHandler when the animation starts and when the animation ends.
 * @author acoppes
 */
public class AnimationMonitorImpl implements AnimationMonitor {

	boolean wasFinished = false;

	boolean wasStarted = false;

	Animation animation;

	List<AnimationEventHandler> animationEventHandlers = new ArrayList<AnimationEventHandler>();

	public AnimationMonitorImpl(Animation animation) {
		this.animation = animation;
	}
	
	public AnimationMonitorImpl(Animation animation, AnimationEventHandler animationEventHandler) {
		this(animation);
		addAnimationHandler(animationEventHandler);
	}

	public void addAnimationHandler(AnimationEventHandler animationEventHandler) {
		animationEventHandlers.add(animationEventHandler);
	}

	@Override
	public void removeAnimationHandler(AnimationEventHandler animationEventHandler) {
		animationEventHandlers.remove(animationEventHandler);
	}

	public void checkAnimationChanges() {

		boolean callOnStart = animation.isStarted() && !wasStarted && !animation.isFinished();
		boolean callOnFinish = animation.isFinished() && !wasFinished;

		wasStarted = animation.isStarted();
		wasFinished = animation.isFinished();

		if (!callOnFinish && !callOnStart)
			return;

		for (AnimationEventHandler animationEventHandler : animationEventHandlers) {
			if (callOnFinish)
				animationEventHandler.onAnimationFinished(animation);
			if (callOnStart)
				animationEventHandler.onAnimationStarted(animation);
		}

	}

}