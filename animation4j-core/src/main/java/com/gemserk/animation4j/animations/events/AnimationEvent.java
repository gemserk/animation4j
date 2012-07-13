package com.gemserk.animation4j.animations.events;

import com.gemserk.animation4j.animations.Animation;

/**
 * Used to call the AnimationEventHandler methods.
 * 
 * @author acoppes
 * 
 */
public class AnimationEvent {

	private Animation animation;
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Animation getAnimation() {
		return animation;
	}
	
	public AnimationEvent() {
		
	}

	public AnimationEvent(Animation animation) {
		this.animation = animation;
	}
	
}