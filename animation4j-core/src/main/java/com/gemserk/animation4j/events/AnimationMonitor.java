package com.gemserk.animation4j.events;

public interface AnimationMonitor {

	void addAnimationHandler(AnimationEventHandler animationEventHandler);
	
	void removeAnimationHandler(AnimationEventHandler animationEventHandler);

	void checkAnimationChanges();
	
}