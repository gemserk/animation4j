package com.gemserk.animation4j.event;

import java.util.ArrayList;
import java.util.List;

import com.gemserk.animation4j.Animation;

/**
 * Monitors an Animation and call to an AnimationHandler when the animation starts and when the animation ends.
 * @author acoppes
 */
public class AnimationMonitorImpl implements AnimationMonitor {

	boolean wasFinished = false;

	boolean wasStarted = false;
	
	int lastIteration = 1;

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
		boolean callOnIterationChanged = animation.getIteration() != lastIteration && !animation.isFinished();

		wasStarted = animation.isStarted();
		wasFinished = animation.isFinished();
		lastIteration = animation.getIteration();

		if (!callOnFinish && !callOnStart && !callOnIterationChanged)
			return;

		for (AnimationEventHandler animationEventHandler : animationEventHandlers) {
			if (callOnFinish)
				animationEventHandler.onAnimationFinished(new AnimationEvent(animation));
			if (callOnStart)
				animationEventHandler.onAnimationStarted(new AnimationEvent(animation));
			if (callOnIterationChanged)
				animationEventHandler.onIterationChanged(new AnimationEvent(animation));
		}

	}

}