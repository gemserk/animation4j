package com.gemserk.animation4j.animations;

import java.util.ArrayList;

import com.gemserk.animation4j.animations.events.AnimationEventHandler;
import com.gemserk.animation4j.animations.events.AnimationHandlerManager;

public class AnimationManager {

	private AnimationHandlerManager animationHandlerManager;
	private ArrayList<Animation> animations;
	
	private ArrayList<Animation> animationsToRemove;
	
	public int getAnimationsCount() { 
		return animations.size();
	}

	public AnimationManager() {
		animationHandlerManager = new AnimationHandlerManager();
		animations = new ArrayList<Animation>();
		animationsToRemove = new ArrayList<Animation>();
	}

	public void add(Animation animation) {
		animations.add(animation);
	}

	public void add(Animation animation, AnimationEventHandler... animationEventHandlers) {
		animations.add(animation);
		for (int i = 0; i < animationEventHandlers.length; i++) 
			handleAnimationChanges(animation, animationEventHandlers[i]);
	}

	public void remove(Animation animation) {
		animationsToRemove.add(animation);
	}

	public void handleAnimationChanges(Animation animation, AnimationEventHandler animationEventHandler) {
		animationHandlerManager.with(animationEventHandler).handleChangesOf(animation);
	}
	
	public void clear() {
		for (int i = 0; i < animations.size(); i++) 
			animationHandlerManager.removeMonitorsFor(animations.get(i));
		animations.clear();
	}

	public void update(float delta) {
		if (animations.isEmpty())
			return;
		
		for (int i = 0; i < animations.size(); i++)
			animations.get(i).update(delta);
		animationHandlerManager.checkAnimationChanges();
		
		for (int i = 0; i < animationsToRemove.size(); i++) {
			Animation animation = animationsToRemove.get(i);
			animationHandlerManager.removeMonitorsFor(animation);
		}
		
		animations.removeAll(animationsToRemove);
		animationsToRemove.clear();
	}

}
