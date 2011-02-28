package com.gemserk.commons.animation.handlers;

public interface AnimationMonitor {

	void addAnimationHandler(AnimationEventHandler animationEventHandler);
	
	void removeAnimationHandler(AnimationEventHandler animationEventHandler);

	void checkAnimationChanges();
	
}