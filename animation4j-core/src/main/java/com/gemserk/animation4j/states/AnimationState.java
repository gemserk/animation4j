package com.gemserk.animation4j.states;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.animation4j.Animation;

public class AnimationState {

	Map<String, Animation> animations = new HashMap<String, Animation>();
	
	String currentId = null;
	
	public void add(String id, Animation animation) {
		animations.put(id, animation);
		if (currentId == null)
			currentId = id;
	}

	public Animation getCurrentState() {
		return animations.get(currentId);
	}

	public void goToState(String id) {
		this.currentId = id;
	}

}
