package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

@SuppressWarnings("rawtypes")
public class MockTransitionEventHandler extends TransitionEventHandler {

	public boolean onTransitionFinishedCalled = false;

	public boolean onTransitionStartedCalled = false;

	public Transition transition = null;

	@Override
	public void onTransitionFinished(Transition transition) {
		this.transition = transition;
		onTransitionFinishedCalled = true;
	}

	@Override
	public void onTransitionStarted(Transition transition) {
		this.transition = transition;
		onTransitionStartedCalled = true;
	}

}