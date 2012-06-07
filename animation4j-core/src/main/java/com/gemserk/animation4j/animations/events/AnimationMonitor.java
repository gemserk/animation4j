package com.gemserk.animation4j.animations.events;

import com.gemserk.animation4j.animations.Animation;

/**
 * Monitors an Animation and call to a group of AnimationEventHandler whenever the animation state changes.
 * 
 * @author acoppes
 */
public class AnimationMonitor {

	private boolean wasFinished = false;
	private boolean wasStarted = false;
	private int lastIteration = 1;
	private Animation animation;

	private AnimationEventHandler animationEventHandler;

	public void setAnimationEventHandler(AnimationEventHandler animationEventHandler) {
		this.animationEventHandler = animationEventHandler;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
		this.wasFinished = false;
		this.wasStarted = false;
		this.lastIteration = 1;
	}

	public Animation getAnimation() {
		return animation;
	}

	public AnimationMonitor(Animation animation) {
		this.animation = animation;
	}

	public AnimationMonitor(Animation animation, AnimationEventHandler animationEventHandler) {
		this.animation = animation;
		this.animationEventHandler = animationEventHandler;
	}

	public void checkAnimationChanges() {

		boolean callOnStart = animation.isStarted() && !wasStarted && !animation.isFinished();
		boolean callOnFinish = animation.isFinished() && !wasFinished;
		boolean callOnIterationChanged = animation.getIteration() != lastIteration && !animation.isFinished() && animation.getIteration() > 1;

		wasStarted = animation.isStarted();
		wasFinished = animation.isFinished();
		lastIteration = animation.getIteration();

		if (!callOnFinish && !callOnStart && !callOnIterationChanged)
			return;

		if (animationEventHandler == null)
			return;

		if (callOnFinish)
			animationEventHandler.onAnimationFinished(new AnimationEvent(animation));
		if (callOnStart)
			animationEventHandler.onAnimationStarted(new AnimationEvent(animation));
		if (callOnIterationChanged)
			animationEventHandler.onIterationChanged(new AnimationEvent(animation));

	}

	public boolean hasAnimationHandlers() {
		return true;
	}

}