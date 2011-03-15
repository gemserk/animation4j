package com.gemserk.animation4j.states;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.animation4j.Animation;

public class AnimationState {
	
	static class Transition {
		
		private final Animation sourceState;
		
		private final Animation targetState;
		
		public Animation getSourceState() {
			return sourceState;
		}
		
		public Animation getTargetState() {
			return targetState;
		}

		public Transition(Animation sourceState, Animation targetState) {
			this.sourceState = sourceState;
			this.targetState = targetState;
			
		}
		
	}

	Map<String, Animation> states = new HashMap<String, Animation>();
	
	Map<String, Transition> transitions = new HashMap<String, AnimationState.Transition>();
	
	Animation currentState;
	
	public void addState(String id, Animation state) {
		states.put(id, state);
		if (currentState == null)
			currentState = state;
	}

	public Animation getCurrentState() {
		return currentState;
	}

	public void handleTransitionCondition(String transitionId) {
		Transition transition = transitions.get(transitionId);
		if (transition != null)
			currentState = transition.getTargetState();
	}

	public void addTransition(String transitionId, String sourceStateId, String targetStateId) {
		transitions.put(transitionId, new Transition(states.get(sourceStateId), states.get(targetStateId)));
	}

}
