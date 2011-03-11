package com.gemserk.animation4j.event;

import com.gemserk.animation4j.Animation;

public class AnimationEvent {
	
	private final Animation animation;
	
	public Animation getAnimation() {
		return animation;
	}

	public AnimationEvent(Animation animation, AnimationHandlerManager animationHandlerManager) {
		this.animation = animation;
	}
}