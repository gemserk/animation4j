package com.gemserk.animation4j.event;

public interface AnimationMonitor {

	void addAnimationHandler(AnimationEventHandler animationEventHandler);
	
	void removeAnimationHandler(AnimationEventHandler animationEventHandler);

	void checkAnimationChanges();
	
}