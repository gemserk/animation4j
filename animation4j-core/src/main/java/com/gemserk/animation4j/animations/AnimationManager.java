package com.gemserk.animation4j.animations;

import java.util.ArrayList;

import com.gemserk.animation4j.animations.events.AnimationEventHandler;
import com.gemserk.animation4j.animations.events.AnimationHandlerManager;

public class AnimationManager {

	private AnimationHandlerManager animationHandlerManager;
	private ArrayList<Animation> animations;
	
	private ArrayList<Animation> animationsToRemove;

	public AnimationManager() {
		animationHandlerManager = new AnimationHandlerManager();
		animations = new ArrayList<Animation>();
		animationsToRemove = new ArrayList<Animation>();
	}

	public void add(Animation animation) {
		animations.add(animation);
	}

	public void remove(Animation animation) {
		animationsToRemove.add(animation);
	}

	public void handleAnimationChanges(Animation animation, AnimationEventHandler animationEventHandler) {
		animationHandlerManager.with(animationEventHandler).handleChangesOf(animation);
	}

	public void update(float delta) {
		for (int i = 0; i < animations.size(); i++)
			animations.get(i).update(delta);
		animationHandlerManager.checkAnimationChanges();
		
		for (int i = 0; i < animationsToRemove.size(); i++) {
			Animation animation = animationsToRemove.get(i);
			animationHandlerManager.removeMonitorsFor(animation);
		}
		
		animations.remove(animationsToRemove);
		animationsToRemove.clear();
	}

}
