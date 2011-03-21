package com.gemserk.animation4j.event;

import com.gemserk.animation4j.Animation;

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