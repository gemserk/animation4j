package com.gemserk.animation4j.animations.events;

import com.gemserk.animation4j.animations.Animation;

/**
 * Used to call the AnimationEventHandler methods.
 * 
 * @author acoppes
 * 
 */
public class AnimationEvent {

	private final Animation animation;

	public Animation getAnimation() {
		return animation;
	}

	public AnimationEvent(Animation animation) {
		this.animation = animation;
	}
}